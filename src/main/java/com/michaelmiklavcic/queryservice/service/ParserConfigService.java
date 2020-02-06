package com.michaelmiklavcic.queryservice.service;

import com.michaelmiklavcic.queryservice.model.ParserChain;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface ParserConfigService {

  List<ParserChain> findAll(Path path);

  ParserChain create(ParserChain chain, Path path) throws IOException;

  ParserChain read(String id, Path path) throws IOException;

  ParserChain update(String id, Path path) throws IOException;

  boolean delete(String id, Path path) throws IOException;
}
