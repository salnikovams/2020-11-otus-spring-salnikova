package ru.otus.spring.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.shell.jline.InteractiveShellApplicationRunner;
import ru.otus.spring.service.CsvParserImpl;

import java.util.Map;

@SpringBootTest(properties = {
        InteractiveShellApplicationRunner.SPRING_SHELL_INTERACTIVE_ENABLED + "=false"
})
public class CsvParserImplTest {

    @Autowired
    CsvParserImpl parser;

    @Test
    public void parserTest() {
        Map<String,String> map = parser.parseFile();
        Assertions.assertEquals(5, map.size());
    }

}