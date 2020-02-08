/**
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.michaelmiklavcic.queryservice.service;

import com.michaelmiklavcic.queryservice.common.utils.IDGenerator;
import com.michaelmiklavcic.queryservice.common.utils.JSONUtils;
import com.michaelmiklavcic.queryservice.common.utils.UniqueIDGenerator;
import com.michaelmiklavcic.queryservice.model.ParserChain;
import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FileBasedParserConfigService implements ParserConfigService {

  private IDGenerator idGenerator;

  public FileBasedParserConfigService() {
    this.idGenerator = new UniqueIDGenerator();
  }

  public FileBasedParserConfigService(IDGenerator idGenerator) {
    this.idGenerator = idGenerator;
  }

  @Override
  public List<ParserChain> findAll(Path path) {
    return Arrays.asList(
        new ParserChain().setId("1").setName("chain1"),
        new ParserChain().setId("2").setName("chain2"),
        new ParserChain().setId("3").setName("chain3")
    );
  }

  @Override
  public ParserChain create(ParserChain chain, Path path) throws IOException {
    chain.setId(idGenerator.generate());
    Path out = Paths.get(getName(chain) + ".json");
    out = path.resolve(out);
    writeChain(chain, out);
    return chain;
  }

  private String getName(ParserChain chain) {
    return chain.getId() + "_" + chain.getName();
  }

  private void writeChain(ParserChain chain, Path outPath) throws IOException {
    byte[] bytes = JSONUtils.INSTANCE.toJSONPretty(chain);
    Files.write(outPath, bytes);
  }

  @Override
  public ParserChain read(String id, Path path) throws IOException {
    Path inPath = findFile(id, path);
    return JSONUtils.INSTANCE.load(inPath.toFile(), ParserChain.class);
  }

  private Path findFile(String id, Path root) throws IOException {
    try (DirectoryStream<Path> files = Files.newDirectoryStream(root)) {
      for (Path file : files) {
        System.out.println(file.getFileName());
        if (file.getFileName().toString().startsWith(String.format("%s_", id))) {
          System.out.println(file);
          return file;
        }
      }
    }
    throw new IOException("No file found with id=" + id);
  }

  @Override
  public ParserChain update(String id, Path path) {
    return new ParserChain().setId("1").setName("chain1");
  }

  @Override
  public boolean delete(String id, Path path) {
    return true;
  }
}
