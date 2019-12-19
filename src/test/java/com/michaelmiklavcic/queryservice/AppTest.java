package com.michaelmiklavcic.queryservice;


import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class AppTest {

  @Mock
  private Shape shape;

  @Test
  public void testHello() throws Exception {
    when(shape.getValue()).thenReturn("square");
    AppMain.config(shape);
    AppMain.main(null);
    assertThat("comparing 2's", 2, equalTo(2));
    verify(shape).getValue();
  }

  @Test
  public void testMock(@Mock Shape shape) {
    AppMain.config(shape);
    AppMain.main(null);
    verify(shape).getValue();
  }

}
