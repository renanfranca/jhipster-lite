package tech.jhipster.lite.module.infrastructure.secondary;

import static org.assertj.core.api.Assertions.*;

import java.util.Collection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.shared.error.domain.GeneratorException;

@UnitTest
class FileSystemProjectFilesTest {

  private static final ProjectFiles files = new FileSystemProjectFiles();

  @Nested
  @DisplayName("String")
  class FileSystemProjectFilesReaderStringTest {

    @Test
    void shouldNotReadUnknownFileContent() {
      assertThatThrownBy(() -> files.readString("unknown")).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldReadFileContent() {
      assertThat(files.readString("/generator/dependencies/Dockerfile")).isNotEmpty();
    }
  }

  @Nested
  @DisplayName("Bytes")
  class FileSystemProjectFilesReaderBytesTest {

    @Test
    void shouldNotReadUnknownFileContent() {
      assertThatThrownBy(() -> files.readBytes("unknown")).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldReadFileContent() {
      assertThat(files.readBytes("/generator/dependencies/Dockerfile")).isNotEmpty();
    }
  }

  @Nested
  @DisplayName("Paths")
  class FileSystemProjectFilesFindPathsTest {

    @Test
    void shouldNotFindUnknownFolder() {
      assertThatThrownBy(() -> files.findPaths("unknown")).isExactlyInstanceOf(GeneratorException.class);
    }

    @Test
    void shouldFindFilesPaths() {
      Collection<String> paths = files.findPaths("/generator/init");

      assertThat(paths).contains(
        "/generator/init/.editorconfig.mustache",
        "/generator/init/.husky/pre-commit",
        "/generator/init/.lintstagedrc.cjs",
        "/generator/init/README.md.mustache",
        "/generator/init/gitattributes",
        "/generator/init/gitignore",
        "/generator/init/package.json.mustache"
      );
    }
  }
}
