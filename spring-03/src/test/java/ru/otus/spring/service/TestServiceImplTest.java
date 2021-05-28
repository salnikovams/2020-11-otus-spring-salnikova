package ru.otus.spring.service;


import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.MessageSource;
import ru.otus.spring.controller.Interviewer;
import ru.otus.spring.dao.StudentAnswerDao;
import ru.otus.spring.domain.StudentAnswer;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@SpringBootTest
public class TestServiceImplTest {

    @Autowired
    StudentAnswerDao studentAnswerDao;

    @MockBean
    Interviewer interviewer;

    @MockBean
    CsvParser parser;

    @MockBean
    MessageSource messageSource;

    @Autowired
    TestServiceImpl testService;

    @Test
    public void testStudentTest() {

        final String question1 = "1+1=?";
        final String answer1 = "2";
        final String question2 = "2+2=?";
        final String answer2 = "4";
        final String question3 = "3+3=?";
        final String answer3 = "6";
        final String studentName = "Peter";
        final String enterYourName = "enter.your.name";

        Map<String,String> questionMap = new LinkedHashMap<>();
        questionMap.put(question1, answer1);
        questionMap.put(question2, answer2);
        questionMap.put(question3, answer3);
        when(parser.parseFile()).thenReturn(questionMap);

        when(messageSource.getMessage(eq(enterYourName), any(), any())).thenReturn(enterYourName);
        when(interviewer.askQuestion(eq(enterYourName))).thenReturn(studentName);

        when(interviewer.askQuestion(question1)).thenReturn(answer1);
        when(interviewer.askQuestion(question2)).thenReturn(answer2);
        when(interviewer.askQuestion(question3)).thenReturn(answer3);
        testService.testStudents();
        StudentAnswer answer = studentAnswerDao.getByName(studentName);
        Assertions.assertEquals(answer.getCorrectAnswersQty(), 3);

    }

}

