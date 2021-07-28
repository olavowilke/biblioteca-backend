package br.com.biblioteca.exception_handler.exception;

public class EstoqueNaoAtribuidoException extends UnprocessableEntityException {

    public EstoqueNaoAtribuidoException(String message) {
        super(message);
    }

}
