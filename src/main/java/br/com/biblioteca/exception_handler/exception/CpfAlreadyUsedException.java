package br.com.biblioteca.exception_handler.exception;

public class CpfAlreadyUsedException extends UnprocessableEntityException {

    public CpfAlreadyUsedException(String message) {
        super(message);
    }

}
