package ru.otus.spring.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.stereotype.Service;
import ru.otus.spring.controller.Interviewer;
import ru.otus.spring.dao.StudentAnswerDao;
import ru.otus.spring.domain.StudentAnswer;

import java.util.Locale;
import java.util.Map;

@Service
public class TestServiceImpl implements TestService {
    private final Integer QUANTITY_OF_CORRECT_ANSWERS_FOR_COMPLETED_TEST = 3;

    private CsvParser parser;
    private Interviewer interviewer;
    private StudentAnswerDao studentAnswerDao;
    private final MessageSource messageSource;
    private final Locale locale;


    private Integer completedTestAnswerQuantity;

    @Autowired
    public TestServiceImpl(CsvParser parser, Interviewer interviewer, StudentAnswerDao studentAnswerDao,
                           @Value( "${completed.test.answer.quantity}")Integer completedTestAnswerQuantity,
                           MessageSource messageSource, @Value( "${application.locale}")Locale locale) {
        this.parser = parser;
        this.interviewer = interviewer;
        this.studentAnswerDao = studentAnswerDao;
        this.completedTestAnswerQuantity = completedTestAnswerQuantity;
        if (completedTestAnswerQuantity == null){
            completedTestAnswerQuantity = QUANTITY_OF_CORRECT_ANSWERS_FOR_COMPLETED_TEST;
        }
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @Override
    public void testStudents(){
        interviewer.saySomething("Locale"+locale);
        Map<String, String> questionsAndAnswers = parser.parseFile();
        String studentName = interviewer.askQuestion(messageSource.getMessage("enter.your.name", null, locale));
        StudentAnswer studentAnswer= new StudentAnswer(studentName);

        for (String question : questionsAndAnswers.keySet()) {
            String answer = interviewer.askQuestion(question);
            studentAnswer.getResult().put(question, answer);
            if (questionsAndAnswers.get(question).compareToIgnoreCase(answer)==0) {
                interviewer.saySomething(messageSource.getMessage("correct", null, locale));
                studentAnswer.incCorrectAnswers();
            } else {
                interviewer.saySomething(messageSource.getMessage("incorrectly", null, locale));
            }
        }

        studentAnswerDao.save(studentAnswer);
        Integer studentCorrectAnswerQty =  studentAnswer.getCorrectAnswersQty();
        if (studentCorrectAnswerQty.compareTo(completedTestAnswerQuantity)>=0) {
            interviewer.saySomething(messageSource.getMessage("testing.completed", null, locale));
            interviewer.saySomething(messageSource.getMessage("number.of.correct.answers",  new Integer[]{studentCorrectAnswerQty}, locale));
        }else{

            interviewer.saySomething(messageSource.getMessage("testing.failed", null, locale));
            interviewer.saySomething(messageSource.getMessage("number.of.correct.answers",  new Integer[]{studentCorrectAnswerQty}, locale));
        }
    }

}
