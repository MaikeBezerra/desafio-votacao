package com.voting.app.api;

import com.voting.app.api.assembler.PautaModelAssembler;
import com.voting.app.api.model.PautaModel;
import com.voting.app.api.model.input.PautaAberturaInput;
import com.voting.app.api.model.input.PautaVotoInput;
import com.voting.app.api.model.ResultadoModel;
import com.voting.app.domain.model.Pauta;
import com.voting.app.domain.service.PautaService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pautas")
@RequiredArgsConstructor
public class PautaController {

    private final PautaService pautaService;

    private final PautaModelAssembler pautaModelAssembler;

    /**
     * Cadastro de uma pauta
     *
     * @param pauta contém propriedades de uma pauta
     * @return Object model com detalhes de uma pauta
     */
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PautaModel create(@RequestBody Pauta pauta) {
        return pautaModelAssembler.toPautaModel(this.pautaService.create(pauta));
    }

    /**
     * Abre sessão de votação de uma pauta
     *
     * @param id código identificador de uma pauta
     * @param aberturaInput contém a propriedade de duração de uma pauta, caso nulo o valor será atribuido como 1 minuto
     */
    @PutMapping("/{pautaId}/abrir")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrirPauta(@PathVariable(value = "pautaId") Long id, @RequestBody(required = false) PautaAberturaInput aberturaInput) {
        pautaService.open(id, aberturaInput == null ? null : aberturaInput.getDuracao());
    }

    /**
     * Recebe a votação dos associados para uma pauta aberta
     *
     * @param id código identificador de uma pauta
     * @param votoInput contém a propriedade de idAssociado representando um CPF de um associado, e o valorVoto
     *                  representando o valor de um voto 'Sim'/'Não'
     */
    @PutMapping("/{pautaId}/votar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void votar(@PathVariable(value = "pautaId") Long id, @RequestBody PautaVotoInput votoInput) {
        pautaService.votar(id, votoInput.getIdAssociado(), votoInput.getValorVoto());
    }

    /**
     * Contabiliza o resultado de votação de uma pauta
     *
     * @param id código identificador de uma pauta
     * @return Objeto resultadoModel com propriedades código identificador de uma sessão, votoSim representando a
     * quantidade de votos sim, e votoNao para a quantidade de votos não
     * */
    @GetMapping("/{pautaId}/resultado")
    public ResponseEntity<ResultadoModel> gerarResultado(@PathVariable("pautaId") Long id) {
        Pauta pauta = pautaService.findById(id);

        return ResponseEntity.ok(pautaModelAssembler.toResultadoModel(pauta));
    }
}
