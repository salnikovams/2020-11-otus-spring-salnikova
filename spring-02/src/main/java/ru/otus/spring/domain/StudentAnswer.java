package ru.otus.spring.domain;

import java.util.HashMap;
import java.util.Map;

public class StudentAnswer {

    private final String name;
    private Map<String, String> result = new HashMap<>();
    private Integer correctAnswersQty = 0;

    public StudentAnswer(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public Map<String, String> getResult() {
        return result;
    }
    public void setResult(Map<String, String> result) {
        this.result = result;
    }

    public void incCorrectAnswers(){
        this.correctAnswersQty++;
    }

    public Integer getCorrectAnswersQty() {
        return correctAnswersQty;
    }
}
