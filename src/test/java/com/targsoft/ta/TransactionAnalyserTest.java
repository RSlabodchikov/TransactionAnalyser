package com.targsoft.ta;


import lombok.SneakyThrows;
import org.junit.Before;
import org.junit.Test;

import java.io.FileInputStream;
import java.nio.file.Paths;
import java.util.Properties;

public class TransactionAnalyserTest {

  private static final String PROPERTIES_FILE = "src/test/resources/test.properties";
  private String[] args;

  @Before
  @SneakyThrows
  public void loadArgs() {
    FileInputStream fis;
    Properties properties = new Properties();
    fis = new FileInputStream(PROPERTIES_FILE);
    properties.load(fis);
    args = new String[]{
        Paths.get(properties.getProperty("file")).toAbsolutePath().toString(),
        properties.getProperty("merchant"),
        properties.getProperty("dateFrom"),
        properties.getProperty("dateTo")};
  }

  @Test
  public void analyserTest() {
    TransactionAnalyser.main(args);
  }
}
