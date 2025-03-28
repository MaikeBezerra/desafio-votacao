package com.voting.app.domain.service;

import com.voting.app.domain.model.Pauta;
import com.voting.app.domain.repository.PautaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static java.util.Objects.nonNull;

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

    @Override
    public void open(Long id, Integer duracao) {
        Pauta pauta = pautaRepository.findById(id).orElseThrow(RuntimeException::new);
        LocalDateTime dataFechamento = nonNull(duracao) ? getDataFechamento(duracao) : getDataFechamento(1);

        pauta.setDataFechamento(dataFechamento);
        pautaRepository.save(pauta);
    }

    private LocalDateTime getDataFechamento(Integer duracao) {
        return LocalDateTime.of(LocalDate.now(), LocalTime.now().plusMinutes(duracao).withNano(0));
    }

}
