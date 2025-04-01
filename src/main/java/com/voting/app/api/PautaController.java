package com.voting.app.api;

import com.voting.app.api.assembler.PautaModelAssembler;
import com.voting.app.api.model.PautaModel;
import com.voting.app.api.model.input.PautaAberturaInput;
import com.voting.app.api.model.input.PautaVotoInput;
import com.voting.app.api.model.ResultadoModel;
import com.voting.app.domain.model.Pauta;
import com.voting.app.domain.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pautas")
public class PautaController {

    private final PautaService pautaService;

    private final PautaModelAssembler pautaModelAssembler;

    @Autowired
    public PautaController(PautaService pautaService, PautaModelAssembler pautaModelAssembler) {
        this.pautaService = pautaService;
        this.pautaModelAssembler = pautaModelAssembler;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PautaModel create(@RequestBody Pauta pauta) {
        return pautaModelAssembler.toPautaModel(this.pautaService.create(pauta));
    }

    @PutMapping("/{pautaId}/abrir")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void abrirPauta(@PathVariable(value = "pautaId") Long id, @RequestBody(required = false) PautaAberturaInput aberturaInput) {
        pautaService.open(id, aberturaInput == null ? null : aberturaInput.getDuracao());
    }

    @PutMapping("/{pautaId}/votar")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void votar(@PathVariable(value = "pautaId") Long id, @RequestBody PautaVotoInput votoInput) {
        pautaService.votar(id, votoInput.getIdAssociado(), votoInput.getValorVoto());
    }

    @GetMapping("/{pautaId}/resultado")
    public ResponseEntity<ResultadoModel> gerarResultado(@PathVariable("pautaId") Long id) {
        Pauta pauta = pautaService.findById(id);

        return ResponseEntity.ok(pautaModelAssembler.toResultadoModel(pauta));
    }
}
