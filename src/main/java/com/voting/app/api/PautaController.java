package com.voting.app.api;

import com.voting.app.api.assembler.PautaModelAssembler;
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
    public Pauta create(@RequestBody Pauta pauta) {
        return this.pautaService.create(pauta);
    }

    @PutMapping("/abrir")
    public void abrirPauta(@RequestParam Long id, @RequestParam(required = false) Integer duracao) {
        pautaService.open(id, duracao);
    }

    @PatchMapping("/votar")
    public void votar(@RequestParam Long id, @RequestParam String idAssociado, @RequestParam String valorVoto) {
        pautaService.votar(id, idAssociado, valorVoto);
    }

    @GetMapping("/resultado")
    public ResponseEntity<ResultadoModel> gerarResultado(@RequestParam Long id) {
        Pauta pauta = pautaService.findById(id);

        return ResponseEntity.ok(pautaModelAssembler.toResultadoModel(pauta));
    }
}
