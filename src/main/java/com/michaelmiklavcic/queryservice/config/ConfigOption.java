package com.michaelmiklavcic.queryservice.config;

public interface ConfigOption<T, V> {

  T get(V source);

}
