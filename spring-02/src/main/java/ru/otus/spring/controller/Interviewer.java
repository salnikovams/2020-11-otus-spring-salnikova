package ru.otus.spring.controller;

public interface Interviewer {
    public String askQuestion(String question);
    public void saySomething(String text);
    public void close();
}
