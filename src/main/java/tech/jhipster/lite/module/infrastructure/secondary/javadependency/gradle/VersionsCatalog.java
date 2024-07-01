package tech.jhipster.lite.module.infrastructure.secondary.javadependency.gradle;

import com.electronwill.nightconfig.core.Config;
import com.electronwill.nightconfig.core.Config.Entry;
import com.electronwill.nightconfig.core.UnmodifiableConfig;
import com.electronwill.nightconfig.core.file.FileConfig;
import com.electronwill.nightconfig.core.io.ParsingException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;
import org.apache.commons.lang3.StringUtils;
import tech.jhipster.lite.module.domain.gradleplugin.GradleCommunityPlugin;
import tech.jhipster.lite.module.domain.gradleplugin.GradlePluginSlug;
import tech.jhipster.lite.module.domain.javabuild.DependencySlug;
import tech.jhipster.lite.module.domain.javabuild.VersionSlug;
import tech.jhipster.lite.module.domain.javadependency.DependencyId;
import tech.jhipster.lite.module.domain.javadependency.JavaDependency;
import tech.jhipster.lite.module.domain.javadependency.JavaDependencyVersion;
import tech.jhipster.lite.module.domain.properties.JHipsterProjectFolder;

public class VersionsCatalog {

  private static final String VERSIONS_TOML_KEY = "versions";
  private static final String LIBRARIES_TOML_KEY = "libraries";
  private static final String PLUGINS_TOML_KEY = "plugins";

  private final FileConfig tomlConfigFile;

  public VersionsCatalog(JHipsterProjectFolder projectFolder) {
    this(tomlVersionCatalogPath(projectFolder));
  }

  public VersionsCatalog(Path tomlVersionCatalogFile) {
    tomlConfigFile = FileConfig.builder(tomlVersionCatalogFile).sync().build();

    // Missing TOML file will be automatically created, but its parent folder should exist
    if (!Files.exists(tomlVersionCatalogFile.getParent())) {
      tomlVersionCatalogFile.toFile().getParentFile().mkdirs();
    }

    try {
      tomlConfigFile.load();
    } catch (ParsingException exception) {
      throw new InvalidTomlVersionCatalogException(exception);
    }
  }

  private static Path tomlVersionCatalogPath(JHipsterProjectFolder projectFolder) {
    return projectFolder.filePath("gradle").resolve("libs.versions.toml");
  }

  private void save() {
    tomlConfigFile.save();
  }

  public static String libraryAlias(JavaDependency dependency) {
    return dependency
      .slug()
      .map(DependencySlug::slug)
      .or(() -> Optional.of(dependency.id().artifactId().get()))
      .map(StringUtils::uncapitalize)
      .orElseThrow();
  }

  public void setVersion(JavaDependencyVersion javaDependencyVersion) {
    tomlConfigFile.set(List.of(VERSIONS_TOML_KEY, javaDependencyVersion.slug().slug()), javaDependencyVersion.version().get());
    save();
  }

  public void addLibrary(JavaDependency dependency) {
    Config libraryConfig = Config.inMemory();
    libraryConfig.set("group", dependency.id().groupId().get());
    libraryConfig.set("name", dependency.id().artifactId().get());
    dependency.version().ifPresent(versionSlug -> libraryConfig.set("version.ref", versionSlug.slug()));
    String libraryEntryKey = libraryAlias(dependency);
    tomlConfigFile.set(List.of(LIBRARIES_TOML_KEY, libraryEntryKey), libraryConfig);
    save();
  }

  public void removeLibrary(DependencyId dependency) {
    libraryEntriesMatchingDependency(dependency).forEach(libraryConfig -> {
      tomlConfigFile.remove(List.of(LIBRARIES_TOML_KEY, libraryConfig.getKey()));
      if (versionUnused(tomlConfigFile, libraryConfig)) {
        VersionSlug.of(versionReference(libraryConfig)).ifPresent(this::removeVersion);
      }
    });
    save();
  }

  private static boolean versionUnused(FileConfig tomlConfigFile, Entry libraryConfig) {
    return tomlConfigFile
      .entrySet()
      .stream()
      .filter(entry -> entry.getKey().equals(LIBRARIES_TOML_KEY))
      .map(Entry::getValue)
      .filter(Config.class::isInstance)
      .map(Config.class::cast)
      .map(Config::entrySet)
      .flatMap(Collection::stream)
      .noneMatch(versionShouldMatch(versionReference(libraryConfig)));
  }

  private static Predicate<Entry> versionShouldMatch(String versionReference) {
    return libraryConfig -> {
      Object versionProperty = versionReference(libraryConfig);
      return versionProperty.equals(versionReference);
    };
  }

  private static String versionReference(Entry libraryConfig) {
    return ((Config) libraryConfig.getValue()).get("version.ref");
  }

  private void removeVersion(VersionSlug versionSlug) {
    tomlConfigFile.remove(List.of(VERSIONS_TOML_KEY, versionSlug.slug()));
    save();
  }

  private List<? extends Entry> libraryEntriesMatchingDependency(DependencyId dependency) {
    return tomlConfigFile
      .entrySet()
      .stream()
      .filter(entry -> entry.getKey().equals(LIBRARIES_TOML_KEY))
      .map(Entry::getValue)
      .filter(Config.class::isInstance)
      .map(Config.class::cast)
      .map(Config::entrySet)
      .flatMap(Collection::stream)
      .filter(groupShouldMatch(dependency))
      .filter(nameShouldMatch(dependency))
      .toList();
  }

  private static Predicate<Entry> groupShouldMatch(DependencyId dependency) {
    return libraryConfig -> {
      Object groupProperty = ((Config) libraryConfig.getValue()).get("group");
      return dependency.groupId().get().equals(groupProperty);
    };
  }

  private static Predicate<Entry> nameShouldMatch(DependencyId dependency) {
    return libraryConfig -> {
      Object groupProperty = ((Config) libraryConfig.getValue()).get("name");
      return dependency.artifactId().get().equals(groupProperty);
    };
  }

  public Collection<DependencySlug> retrieveDependencySlugsFrom(DependencyId dependency) {
    return libraryEntriesMatchingDependency(dependency).stream().map(UnmodifiableConfig.Entry::getKey).map(DependencySlug::new).toList();
  }

  public Collection<JavaDependencyVersion> retrieveVersions() {
    return tomlConfigFile
      .entrySet()
      .stream()
      .filter(entry -> entry.getKey().equals(VERSIONS_TOML_KEY))
      .map(Entry::getValue)
      .filter(Config.class::isInstance)
      .map(Config.class::cast)
      .map(Config::entrySet)
      .flatMap(Collection::stream)
      .map(entry -> new JavaDependencyVersion(entry.getKey(), entry.getValue()))
      .toList();
  }

  public static String pluginAlias(GradleCommunityPlugin communityPlugin) {
    return communityPlugin.pluginSlug().map(GradlePluginSlug::get).orElse(communityPlugin.id().get());
  }

  public void addPlugin(GradleCommunityPlugin plugin) {
    Config pluginConfig = Config.inMemory();
    pluginConfig.set("id", plugin.id().get());
    plugin.versionSlug().ifPresent(versionSlug -> pluginConfig.set("version.ref", versionSlug.slug()));
    String pluginEntryKey = pluginAlias(plugin);
    tomlConfigFile.set(List.of(PLUGINS_TOML_KEY, pluginEntryKey), pluginConfig);
    save();
  }
}
