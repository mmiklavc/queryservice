package com.michaelmiklavcic.queryservice.demo;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class AppMain {

  private static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
  private static Shape shape;

  public static void config(Shape shape) {
    AppMain.shape = shape;
  }

  public static void main(String[] args) {
    LOGGER.info("Starting application.");
    LOGGER.info("Shape value {}", AppMain.shape.getValue());
    System.out.println("HELLO");
    LOGGER.info("Application finished.");
  }

}
