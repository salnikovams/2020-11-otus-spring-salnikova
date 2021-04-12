package ru.otus.spring.exception;

public class NoBookFindByIdException extends RuntimeException {

    public NoBookFindByIdException(String message) {
        super(message);
    }
}
