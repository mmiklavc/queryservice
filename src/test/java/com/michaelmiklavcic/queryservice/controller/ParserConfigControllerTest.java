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

package com.michaelmiklavcic.queryservice.controller;

import static com.michaelmiklavcic.queryservice.common.ApplicationConstants.API_CHAINS_DELETE_URL;
import static com.michaelmiklavcic.queryservice.common.ApplicationConstants.API_CHAINS_READ_URL;
import static com.michaelmiklavcic.queryservice.common.ApplicationConstants.API_CHAINS_URL;
import static org.hamcrest.CoreMatchers.instanceOf;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.Matchers.hasSize;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.michaelmiklavcic.queryservice.model.ParserChain;
import com.michaelmiklavcic.queryservice.service.ParserConfigService;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import org.adrianwalker.multilinestring.Multiline;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class ParserConfigControllerTest {

  @Autowired
  private MockMvc mvc;
  @MockBean
  private ParserConfigService service;
  private String chainId = "chain1";

  /**
   * hello
   */
  @Multiline
  public static String basicChainConfig;

  @Test
  public void returns_list_of_all_chains() throws Exception {
    given(service.findAll()).willReturn(
        Arrays.asList(
            new ParserChain("1", "chain1"),
            new ParserChain("2", "chain2"),
            new ParserChain("3", "chain3")
        ));
    mvc.perform(get(API_CHAINS_URL).accept(MediaType.APPLICATION_JSON))
//        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.*", instanceOf(List.class)))
        .andExpect(jsonPath("$.*", hasSize(3)))
        .andExpect(jsonPath("$.[0].id", is("1")))
        .andExpect(jsonPath("$.[0].name", is("chain1")))
        .andExpect(jsonPath("$.[1].id", is("2")))
        .andExpect(jsonPath("$.[1].name", is("chain2")))
        .andExpect(jsonPath("$.[2].id", is("3")))
        .andExpect(jsonPath("$.[2].name", is("chain3")));
  }

  @Test
  public void returns_empty_list_when_no_chains() throws Exception {
    given(service.findAll()).willReturn(Collections.emptyList());
    mvc.perform(get(API_CHAINS_URL).accept(MediaType.APPLICATION_JSON))
//        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.*", instanceOf(List.class)))
        .andExpect(jsonPath("$.*", hasSize(0)));
  }

  @Test
  public void read_chain_by_id_returns_chain_config() throws Exception {
    final ParserChain chain = new ParserChain("1", "chain1");
    given(service.read(chainId)).willReturn(chain);
    mvc.perform(
        get(API_CHAINS_READ_URL.replace("{id}", chainId)).accept(MediaType.APPLICATION_JSON))
//        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.name", is("chain1")))
        .andExpect(jsonPath("$.id", is("1")));
  }

  @Test
  public void read_chain_by_nonexistent_id_returns_not_found() throws Exception {
    given(service.read(chainId)).willReturn(null);
    mvc.perform(
        get(API_CHAINS_READ_URL.replace("{id}", chainId)).accept(MediaType.APPLICATION_JSON))
//        .andDo(print())
        .andExpect(status().isNotFound());
  }

/*
  @Test
  public void creates_chain() throws Exception {
    ParserChain chain = new ParserChain(chainConfig);
    given(service.create(chain)).willReturn(chain);
    mvc.perform(get("/parserconfig/chains").accept(MediaType.APPLICATION_JSON))
        .andDo(print())
        .andExpect(status().isOk())
        .andExpect(header().string(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE))
        .andExpect(jsonPath("$.*", instanceOf(List.class)))
        .andExpect(jsonPath("$.*", hasSize(0)));
  }
*/

  @Test
  public void deleting_existing_chain_succeeds() throws Exception {
    given(service.delete(chainId)).willReturn(true);
    mvc.perform(
        delete(API_CHAINS_DELETE_URL.replace("{id}", chainId)).accept(MediaType.APPLICATION_JSON))
//        .andDo(print())
        .andExpect(status().isNoContent());
  }

  @Test
  public void deleting_nonexistent_chain_returns_not_found() throws Exception {
    given(service.delete(chainId)).willReturn(false);
    mvc.perform(
        delete(API_CHAINS_DELETE_URL.replace("{id}", chainId)).accept(MediaType.APPLICATION_JSON))
//        .andDo(print())
        .andExpect(status().isNotFound());
  }
}