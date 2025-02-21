package tech.jhipster.lite.module.domain;

import java.util.Collection;

public interface ProjectFiles {
  String readString(String path);

  byte[] readBytes(String path);

  Collection<String> findPaths(String path);
}
