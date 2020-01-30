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

import com.michaelmiklavcic.queryservice.model.ParserChain;
import java.util.Arrays;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class FileBasedParserConfigService implements ParserConfigService {

  @Override
  public List<ParserChain> findAll() {
    return Arrays.asList(
        new ParserChain("1", "chain1"),
        new ParserChain("2", "chain2"),
        new ParserChain("3", "chain3"));
  }

  @Override
  public ParserChain create(ParserChain chain) {
    return null;
  }

  @Override
  public ParserChain read(String id) {
    return null;
  }

  @Override
  public ParserChain update(String id) {
    return null;
  }

  @Override
  public boolean delete(String id) {
    return false;
  }
}
