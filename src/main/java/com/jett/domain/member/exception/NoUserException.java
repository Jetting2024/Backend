package com.jett.domain.member.exception;

public class NoUserException extends RuntimeException {
    public NoUserException(String message) {
        super(message);
    }
}
