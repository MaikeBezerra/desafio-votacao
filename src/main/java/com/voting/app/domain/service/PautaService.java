package com.voting.app.domain.service;

import com.voting.app.domain.model.Pauta;

public interface PautaService {

    Pauta create(Pauta pauta);

    void open(Long id, Integer duracao);
}
