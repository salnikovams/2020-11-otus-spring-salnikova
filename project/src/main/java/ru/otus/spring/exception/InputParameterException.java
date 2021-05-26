package ru.otus.spring.exception;


public class InputParameterException extends Exception {
    public InputParameterException(Throwable throwable) {
        super(throwable);
    }

    public InputParameterException(String s) {
        super(s);
    }
}
