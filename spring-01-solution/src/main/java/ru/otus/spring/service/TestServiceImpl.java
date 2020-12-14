package ru.otus.spring.service;

import ru.otus.spring.controller.Interviewer;
import ru.otus.spring.dao.StudentAnswerDao;
import ru.otus.spring.domain.StudentAnswer;
import java.util.Map;

public class TestServiceImpl implements TestService {
    private CsvParser parser;
    private Interviewer interviewer;
    private StudentAnswerDao studentAnswerDao;


    public TestServiceImpl(CsvParser parser, Interviewer interviewer, StudentAnswerDao studentAnswerDao) {
        this.parser = parser;
        this.interviewer = interviewer;
        this.studentAnswerDao = studentAnswerDao;
    }

    @Override
    public void testStudents(){
        Map<String, String> questionsAndAnswers = parser.parseFile();
        String studentName = interviewer.askQuestion("Enter your name");
        StudentAnswer studentAnswer= new StudentAnswer(studentName);

        for (String question : questionsAndAnswers.keySet()) {
            String answer = interviewer.askQuestion(question);
            studentAnswer.getResult().put(question, answer);
            if (questionsAndAnswers.get(question).compareToIgnoreCase(answer)==0) {
                interviewer.saySomething("correct");
                studentAnswer.incCorrectAnswers();
            } else {
                interviewer.saySomething("incorrectly");
            }
        }

        studentAnswerDao.save(studentAnswer);

        interviewer.saySomething(String.format("Testing completed. Number of correct answers: %s", studentAnswer.getCorrectAnswersQty()));
    }
}
