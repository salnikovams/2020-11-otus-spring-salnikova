package ru.otus.spring.service;

import java.io.InputStreamReader;
import java.util.*;

public class CsvParserImpl implements CsvParser{

    private String fileName;

    @Override
    public Map<String,  String> parseFile() {
        Map<String, String> result = new HashMap<>();
        Scanner scanner = new Scanner(new InputStreamReader(getClass().getResourceAsStream("/" + fileName)));

        while(scanner.hasNext()) {
            String s = scanner.nextLine();
            String[] arr = s.split(";");
            String question = arr[0];
            String answer = arr[1];
            result.put(question, answer.substring(1, arr[1].length()));
        }

        return result;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }
}
