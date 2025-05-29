package com.voting.app;

import com.voting.app.domain.exception.AssociadoJaVotouException;
import com.voting.app.domain.exception.PautaFechadaException;
import com.voting.app.domain.exception.PautaNaoAbertaException;
import com.voting.app.domain.exception.PautaNaoEncontradaException;
import com.voting.app.domain.model.Pauta;
import com.voting.app.domain.repository.PautaRepository;
import com.voting.app.domain.service.PautaServiceImpl;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PautaServiceTest {

    @InjectMocks
    private PautaServiceImpl pautaService;

    @Mock
    private PautaRepository pautaRepository;

    @Test
    void deveCriarNovaPauta() {
        Pauta pauta = new Pauta();

        pautaService.create(pauta);
        verify(pautaRepository, atLeastOnce()).save(pauta);
    }

    @Test
    void deveAbrirUmaPauta_ComDuracaoEspecifica() {
        Pauta pauta = new Pauta();
        when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));

        pautaService.open(pauta.getId(), Integer.valueOf("10"));

        assertNotNull(pauta.getDataFechamento());
        verify(pautaRepository, atLeastOnce()).save(pauta);
    }

    @Test
    void deveAbrirUmaPauta_ComDuracaoNaoEspecifica() {
        Pauta pauta = new Pauta();
        when(pautaRepository.findById(pauta.getId())).thenReturn(Optional.of(pauta));

        pautaService.open(pauta.getId(), null);

        assertNotNull(pauta.getDataFechamento());
        verify(pautaRepository, atLeastOnce()).save(pauta);
    }

    @Test
    void deveRetornarErroAoAbrir_PautaNaoEncontrada() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(PautaNaoEncontradaException.class,
                () -> pautaService.open(1L, Integer.valueOf("10")));

        assertEquals("Não existe cadastro de pauta com o código 1", exception.getMessage());
    }

    @Test
    void deveComputarVotoSim() {
        Pauta pauta = new Pauta();
        pauta.setDataFechamento(LocalDateTime.now().plusMinutes(5L));
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        pautaService.votar(1L, "777", "Sim");

        assertEquals(Integer.valueOf("1"), pauta.getVotoSim());
        verify(pautaRepository, atLeastOnce()).save(pauta);
    }

    @Test
    void deveComputarVotoNao() {
        Pauta pauta = new Pauta();
        pauta.setDataFechamento(LocalDateTime.now().plusMinutes(5L));
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        pautaService.votar(1L, "777", "Nao");

        assertEquals(Integer.valueOf("1"), pauta.getVotoNao());
        verify(pautaRepository, atLeastOnce()).save(pauta);
    }

    @Test
    void deveInvalidarVoto_PautaNaoEncontrada() {
        when(pautaRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(PautaNaoEncontradaException.class,
                () -> pautaService.votar(1L, "777", "Sim"));

        assertEquals("Não existe cadastro de pauta com o código 1", exception.getMessage());
    }

    @Test
    void deveInvalidarVoto_PautaNaoAberta() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        Exception exception = assertThrows(PautaNaoAbertaException.class,
                () -> pautaService.votar(1L, "777", "Nao"));

        assertEquals("Pauta com código 1 não está aberta", exception.getMessage());
    }

    @Test
    void deveInvalidarVoto_PautaExpirada() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDataFechamento(LocalDateTime.now().minusMinutes(5L));
        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        Exception exception = assertThrows(PautaFechadaException.class,
                () -> pautaService.votar(1L, "777", "Nao"));

        assertEquals("Pauta com código 1 já foi encerrada", exception.getMessage());
    }

    @Test
    void deveInvalidarVoto_AssociadoJaVotou() {
        Pauta pauta = new Pauta();
        pauta.setId(1L);
        pauta.setDataFechamento(LocalDateTime.now().plusMinutes(5L));
        pauta.addVoto("777");

        when(pautaRepository.findById(1L)).thenReturn(Optional.of(pauta));

        Exception exception = assertThrows(AssociadoJaVotouException.class,
                () -> pautaService.votar(1L, "777", "Nao"));

        assertEquals("Associado com código 777 já votou para a pauta de código 1", exception.getMessage());
    }
}
