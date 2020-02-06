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

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;

import com.michaelmiklavcic.queryservice.common.utils.UniqueIDGenerator;
import com.michaelmiklavcic.queryservice.model.ParserChain;
import java.io.IOException;
import java.nio.file.Path;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FileBasedParserConfigServiceTest {

  private UniqueIDGenerator idGenerator;
  private FileBasedParserConfigService service;
  private Path configPath;

  @BeforeEach
  public void beforeEach() {
    int seed = 0;
    idGenerator = new UniqueIDGenerator(seed);
    service = new FileBasedParserConfigService(idGenerator);
  }

  @Test
  public void creates_parser_chain() throws IOException {
    ParserChain chain = new ParserChain().setName("chain1");
    ParserChain actual = service.create(chain, configPath);
    ParserChain expected = new ParserChain().setId("1").setName("chain1");
    assertThat(actual, equalTo(expected));
  }

  @Test
  public void reads_existing_parser_chain() throws IOException {
    ParserChain chain = new ParserChain().setName("chain1");
    service.create(chain, configPath);
    ParserChain actual = service.read("1", configPath);
    ParserChain expected = new ParserChain().setId("1").setName("chain1");
    assertThat(actual, equalTo(expected));
  }
}