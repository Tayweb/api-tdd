package br.com.tayweb.api.service.exceptions;

public class DataIntegratyViolationException extends RuntimeException{

    public DataIntegratyViolationException(String message) {
        super(message);
    }
}
