package ru.otus.spring.service;

import java.util.Map;

public interface CsvParser {
    Map<String, String> parseFile();
}
