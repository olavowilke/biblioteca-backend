package br.com.biblioteca.exception_handler.exception;

public class LivroIndisponivelException extends UnprocessableEntityException {

    public LivroIndisponivelException(String message) {
        super(message);
    }

}
