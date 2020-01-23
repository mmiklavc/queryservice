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

package com.michaelmiklavcic.queryservice.demo.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import com.michaelmiklavcic.queryservice.model.Hello;
import com.michaelmiklavcic.queryservice.demo.resource.HelloResourceAssembler;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

  private List<Hello> hellosRepo;

  public HelloController() {
    this.hellosRepo = Arrays.asList(new Hello("a", 1), new Hello("b", 2), new Hello("c", 3));
  }

  @RequestMapping("/")
  public String index() {
    return "Greetings from Spring Boot!";
  }

  @GetMapping("/helloes")
  ResponseEntity<CollectionModel<EntityModel<Hello>>> findAll() {
    List<EntityModel<Hello>> hellos = StreamSupport.stream(hellosRepo.spliterator(), false)
        .map(hello -> new EntityModel<>(hello,
            linkTo(methodOn(HelloController.class).findOne(hello.getId())).withSelfRel(),
            linkTo(methodOn(HelloController.class).findAll()).withRel("hellos")))
        .collect(Collectors.toList());
    HelloResourceAssembler assembler = new HelloResourceAssembler();
    return ResponseEntity.ok(assembler.toCollectionModel(hellosRepo));
/*
    return ResponseEntity.ok(new CollectionModel<>(hellos,
        linkTo(methodOn(HelloController.class).findAll()).withSelfRel()));
*/
  }

  @GetMapping("/helloes/{id}")
  ResponseEntity<EntityModel<Hello>> findOne(@PathVariable int id) {
    Hello hello = hellosRepo.get(id - 1);
/*
    return ResponseEntity.ok(new EntityModel<>(hello,
        linkTo(methodOn(HelloController.class).findOne(hello.getId())).withSelfRel(),
        linkTo(methodOn(HelloController.class).findAll()).withRel("hellos")));
*/
    HelloResourceAssembler assembler = new HelloResourceAssembler();
    return ResponseEntity.ok(assembler.toModel(hello));
  }

}
