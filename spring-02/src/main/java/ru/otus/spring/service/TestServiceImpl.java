package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import ru.otus.spring.controller.Interviewer;
import ru.otus.spring.dao.StudentAnswerDao;
import ru.otus.spring.domain.StudentAnswer;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {
    private final Integer QUANTITY_OF_CORRECT_ANSWERS_FOR_COMPLETED_TEST = 3;

    private CsvParser parser;
    private Interviewer interviewer;
    private StudentAnswerDao studentAnswerDao;


    private Integer completedTestAnswerQuantity;

    @Autowired
    public TestServiceImpl(CsvParser parser, Interviewer interviewer, StudentAnswerDao studentAnswerDao, @Value( "${completed.test.answer.quantity}")Integer completedTestAnswerQuantity) {
        this.parser = parser;
        this.interviewer = interviewer;
        this.studentAnswerDao = studentAnswerDao;
        this.completedTestAnswerQuantity = completedTestAnswerQuantity;
        if (completedTestAnswerQuantity == null){
            completedTestAnswerQuantity = QUANTITY_OF_CORRECT_ANSWERS_FOR_COMPLETED_TEST;
        }
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
        Integer studentCorrectAnswerQty =  studentAnswer.getCorrectAnswersQty();
        if (studentCorrectAnswerQty.compareTo(completedTestAnswerQuantity)>=0) {
            interviewer.saySomething(String.format("Testing completed. Number of correct answers: %s", studentCorrectAnswerQty));
        }else{
            interviewer.saySomething(String.format("Testing failed. Number of correct answers: %s", studentCorrectAnswerQty));
        }
    }
}
