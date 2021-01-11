package ru.otus.spring.shellCommand;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.MessageSource;
import org.springframework.shell.standard.ShellComponent;
import org.springframework.shell.standard.ShellMethod;
import org.springframework.shell.standard.ShellOption;
import ru.otus.spring.controller.Interviewer;
import ru.otus.spring.dao.StudentAnswerDao;
import ru.otus.spring.domain.StudentAnswer;
import ru.otus.spring.service.TestService;
import java.util.Collection;
import java.util.Locale;

@ShellComponent
public class TestShellCommand {

    private TestService testService;
    private StudentAnswerDao studentAnswerDao;
    private Interviewer interviewer;
    private final MessageSource messageSource;
    private final Locale locale;

    @Autowired
    public TestShellCommand(TestService tester, StudentAnswerDao studentAnswerDao, Interviewer interviewer,
                            MessageSource messageSource, @Value( "${application.locale}")Locale locale) {
        this.testService = tester;
        this.studentAnswerDao = studentAnswerDao;
        this.interviewer = interviewer;
        this.messageSource = messageSource;
        this.locale = locale;
    }

    @ShellMethod(value = "Run tests", key = "start")
    public void startTesting() {
        testService.testStudents();
    }

    @ShellMethod(value = "Show all results", key = "show-all-results")
    public void showAllResults() {
        Collection<StudentAnswer> answers = studentAnswerDao.getAll();
        if (answers.size() == 0) {
            interviewer.saySomething(messageSource.getMessage("no.results", null, locale));
        }
        else{
            for (StudentAnswer answer : answers) {
                interviewer.saySomething(messageSource.getMessage("result.by.name", new String[]{answer.getName(), answer.getCorrectAnswersQty().toString()}, locale));
            }
        }
    }

    @ShellMethod(value = "Show results by name", key = "show-result")
    public void showResultsForName(@ShellOption(help = "name") String name) {
        StudentAnswer answer = studentAnswerDao.getByName(name);
        if (answer == null){
            interviewer.saySomething(messageSource.getMessage("no.results", null, locale));
        }else {
            interviewer.saySomething(messageSource.getMessage("result.by.name", new String[]{name, answer.getCorrectAnswersQty().toString()}, locale));
        }

    }
}
