package br.com.biblioteca.exception_handler.exception;

public class NomeAlreadyUsedException extends UnprocessableEntityException {

    public NomeAlreadyUsedException(String message) {
        super(message);
    }

}
