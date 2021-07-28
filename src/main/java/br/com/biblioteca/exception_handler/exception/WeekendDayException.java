package br.com.biblioteca.exception_handler.exception;

public class WeekendDayException extends UnprocessableEntityException {

    public WeekendDayException(String message) {
        super(message);
    }

}
