package com.voting.app.domain.service;

import com.voting.app.domain.model.Pauta;
import com.voting.app.domain.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PautaServiceImpl implements PautaService {

    private final PautaRepository pautaRepository;

    @Autowired
    public PautaServiceImpl(PautaRepository pautaRepository) {
        this.pautaRepository = pautaRepository;
    }

    @Override
    @Transactional
    public Pauta create(Pauta pauta) {
        return this.pautaRepository.save(pauta);
    }

}
