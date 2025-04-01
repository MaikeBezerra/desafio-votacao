package com.voting.app.domain.exception;

public class PautaNaoAbertaException extends NegocioException {
    public PautaNaoAbertaException(String message) {
        super(message);
    }

    public PautaNaoAbertaException(Long id) {
        this(String.format("Pauta com código %d não está aberta", id));
    }
}
