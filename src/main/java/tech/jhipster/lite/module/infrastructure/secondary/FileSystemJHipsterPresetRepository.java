package tech.jhipster.lite.module.infrastructure.secondary;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Repository;
import tech.jhipster.lite.module.domain.JHipsterPresetRepository;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.preset.Preset;
import tech.jhipster.lite.module.domain.preset.PresetName;
import tech.jhipster.lite.module.domain.preset.Presets;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

@Repository
class FileSystemJHipsterPresetRepository implements JHipsterPresetRepository {

  private static final String ROOT_FOLDER = "/";

  private final ObjectMapper json;
  private final ProjectFiles projectFiles;
  private final PresetName presetName;

  public FileSystemJHipsterPresetRepository(
    ObjectMapper json,
    ProjectFiles projectFiles,
    @Value("${jhlite.preset-folder:presets}") String presetFolderName
  ) {
    this.json = json;
    this.projectFiles = projectFiles;
    this.presetName = PresetName.from(presetFolderName);
  }

  @Override
  public Presets getPresets() {
    return new Presets(readAllPresets());
  }

  private List<Preset> readAllPresets() {
    return projectFiles
      .findRecursivelyInPath(presetFolderPath())
      .stream()
      .filter(jsonExtensionFiles())
      .map(readPresetFile())
      .map(PersistedPresets::toDomain)
      .flatMap(Collection::stream)
      .toList();
  }

  private String presetFolderPath() {
    return ROOT_FOLDER + presetName.name();
  }

  private static Predicate<String> jsonExtensionFiles() {
    return path -> path.endsWith(".json");
  }

  private Function<String, PersistedPresets> readPresetFile() {
    return path -> {
      try {
        return json.readValue(projectFiles.readBytes(path), PersistedPresets.class);
      } catch (IOException e) {
        throw GeneratorException.technicalError("Can't read preset file at " + path + ": " + e.getMessage(), e);
      }
    };
  }
}
