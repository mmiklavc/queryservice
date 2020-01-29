package com.michaelmiklavcic.queryservice.service;

import com.michaelmiklavcic.queryservice.model.ParserChain;
import java.util.List;

public interface ParserConfigService {

  List<ParserChain> findAll();

}
