package com.voting.app.domain.exception;

public class PautaNaoEncontradaException extends EntidadeNaoEncontradaException {
    public PautaNaoEncontradaException(String message) {
        super(message);
    }

    public PautaNaoEncontradaException(Long pautaId) {
        this(String.format("Não existe cadastro de pauta com o código %d", pautaId));
    }
}
