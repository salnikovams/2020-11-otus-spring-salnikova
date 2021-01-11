package ru.otus.spring.controller;

import org.springframework.stereotype.Controller;

import java.io.InputStreamReader;
import java.util.Scanner;

@Controller
public class InterviewerImpl implements Interviewer {

    private Scanner scanner = new Scanner(new InputStreamReader(System.in));

    @Override
    public String askQuestion(String question) {
        System.out.println(question);
        return scanner.nextLine();
    }

    @Override
    public void saySomething(String text) {
        System.out.println(text);
    }

    @Override
    public void close() {
        if (scanner != null) {
            scanner.close();

        }
    }
}
