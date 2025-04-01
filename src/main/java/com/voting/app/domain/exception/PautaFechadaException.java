package com.voting.app.domain.exception;

public class PautaFechadaException extends NegocioException {
    public PautaFechadaException(String message) {
        super(message);
    }

    public PautaFechadaException(Long id) {
        this(String.format("Pauta com código %d já foi encerrada", id));
    }
}
