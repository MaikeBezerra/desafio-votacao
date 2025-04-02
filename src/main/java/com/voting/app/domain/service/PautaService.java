package com.voting.app.domain.service;

import com.voting.app.domain.exception.AssociadoJaVotouException;
import com.voting.app.domain.exception.PautaFechadaException;
import com.voting.app.domain.exception.PautaNaoAbertaException;
import com.voting.app.domain.exception.PautaNaoEncontradaException;
import com.voting.app.domain.model.Pauta;

public interface PautaService {

    /**
     * Cria uma nova pauta
     *
     * @param pauta propriedades de uma pauta
     * @return uma nova pauta criada
     */
    Pauta create(Pauta pauta);

    /**
     * Abre uma pauta para votação
     *
     * @param id código identificador de uma pauta
     * @param duracao tempo em minitos que uma pauta permanecerá aberta
     * @throws PautaNaoEncontradaException Se uma pauta não está cadastrada
     */
    void open(Long id, Integer duracao);

    /**
     * Cadastra um voto em uma pauta
     *
     * @param id código identificador de uma pauta
     * @param idAssociado código identificador de uma associado
     * @param valorVoto valor do voto Sim/Não
     * @throws PautaNaoEncontradaException Se uma pauta não está cadastrada
     * @throws PautaNaoAbertaException Se uma pauta não está aberta para votação
     * @throws PautaFechadaException Se uma pauta já expirou
     * @throws AssociadoJaVotouException Se um associado já realizou voto em uma pauta
     */
    void votar(Long id, String idAssociado, String valorVoto);

    /**
     * Busca uma pauta pelo ID
     *
     * @param id código identificador de uma pauta
     * @return Uma pauta
     * @throws PautaNaoEncontradaException Se uma pauta não está cadastrada para o identificador informado
     */
    Pauta findById(Long id);
}
