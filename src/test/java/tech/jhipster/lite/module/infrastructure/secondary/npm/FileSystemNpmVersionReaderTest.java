package tech.jhipster.lite.module.infrastructure.secondary.npm;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.anyString;
import static org.mockito.Mockito.when;

import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tech.jhipster.lite.UnitTest;
import tech.jhipster.lite.module.domain.ProjectFiles;
import tech.jhipster.lite.module.domain.npm.*;

@UnitTest
@ExtendWith(MockitoExtension.class)
class FileSystemNpmVersionReaderTest {

  private static final NpmVersionSource COMMON = JHLiteNpmVersionSource.COMMON.build();

  @Mock
  private ProjectFiles projectFiles;

  private FileSystemNpmVersionReader reader;

  @BeforeEach
  void setup() {
    reader = new FileSystemNpmVersionReader(projectFiles, List.of(JHLiteNpmVersionSource.COMMON), "not-used");
  }

  @Test
  void shouldGetVersionFromDevSource() {
    mockProjectFiles();

    NpmPackageVersion version = reader.get().get(new NpmPackageName("@types/node"), COMMON);

    assertThat(version).isEqualTo(new NpmPackageVersion("17.0.43"));
  }

  @Test
  void shouldGetVersionFromSource() {
    mockProjectFiles();

    NpmPackageVersion version = reader.get().get(new NpmPackageName("vue"), COMMON);

    assertThat(version).isEqualTo(new NpmPackageVersion("1.2.3"));
  }

  @Test
  void shouldGetVersionFromEmptySourceWithEmptyDevSource() {
    emptyProjectFiles();

    NpmPackageVersion version = reader.get().get(new NpmPackageName("vue"), COMMON);

    assertThat(version).isEqualTo(new NpmPackageVersion("1.2.3"));
  }

  private void mockProjectFiles() {
    when(projectFiles.readString(anyString())).thenReturn(
      """
      {
        "name": "jhlite-dependencies",
        "version": "0.0.0",
        "description": "JHipster Lite : used for Dependencies",
        "license": "Apache-2.0",
        "dependencies": {
          "vue": "1.2.3"
        },
        "devDependencies": {
          "@playwright/test": "1.22.2",
          "@prettier/plugin-xml": "2.2.0",
          "@types/jest": "28.1.1",
          "@types/node": "17.0.43",
          "@typescript-eslint/eslint-plugin": "5.28.0",
          "@typescript-eslint/parser": "5.28.0",
          "cypress": "10.1.0",
          "eslint": "8.17.0",
          "eslint-config-prettier": "9.1.0",
          "eslint-import-resolver-typescript": "2.7.1",
          "eslint-plugin-cypress": "2.12.1",
          "eslint-plugin-import": "2.26.0",
          "husky": "8.0.1",
          "jasmine-core": "4.2.0",
          "jest": "27.5.1",
          "lint-staged": "13.0.1",
          "prettier": "2.7.0",
          "prettier-plugin-java": "1.6.2",
          "prettier-plugin-packagejson": "2.2.18",
          "ts-jest": "27.1.4",
          "typescript": "4.7.3"
        }
      }
      """
    );
  }

  private void emptyProjectFiles() {
    when(projectFiles.readString(anyString())).thenReturn(
      """
      {
        "name": "jhlite-dependencies",
        "version": "0.0.0",
        "description": "JHipster Lite : used for Dependencies",
        "license": "Apache-2.0",
        "dependencies": {
          "vue": "1.2.3"
        },
      }
      """
    );
  }
}
