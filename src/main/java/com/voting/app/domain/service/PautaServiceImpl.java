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
    @Transactional
    public void open(Long id, Integer duracao) {
        Pauta pauta = findById(id);
        LocalDateTime dataFechamento = nonNull(duracao) ? getDataFechamento(duracao) : getDataFechamento(1);

        pauta.setDataFechamento(dataFechamento);
        pautaRepository.save(pauta);
    }

    @Override
    @Transactional
    public void votar(Long id, String idAssociado, String valorVoto) {
        Pauta pauta = findById(id);

        // Se o associado pode votar
        if (pauta.addVoto(idAssociado)) {

            if ("Sim".trim().equalsIgnoreCase(valorVoto)) {
                Integer votosSim = pauta.getVotoSim() + 1;
                pauta.setVotoSim(votosSim);
            } else {
                // Todos os votos diferentes de sim serão considerados como não
                Integer votosNao = pauta.getVotoNao() + 1;
                pauta.setVotoNao(votosNao);
            }

            pautaRepository.save(pauta);
        }
    }

    public Pauta findById(Long id) {
        return pautaRepository.findById(id).orElseThrow(RuntimeException::new);
    }

    private LocalDateTime getDataFechamento(Integer duracao) {
        return LocalDateTime.of(LocalDate.now(), LocalTime.now().plusMinutes(duracao).withNano(0));
    }

}
