package ru.otus.spring.service;


import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Assertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Map;

@SpringBootTest
public class CsvParserImplTest {

    @Autowired CsvParserImpl parser;

    @Test
    public void parserTest() {
        Map<String,String> map = parser.parseFile();
        Assertions.assertEquals(5, map.size());
    }

}