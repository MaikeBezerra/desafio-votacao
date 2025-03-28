package com.voting.app.api;

import com.voting.app.domain.model.Pauta;
import com.voting.app.domain.service.PautaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/pautas")
public class PautaController {

    private final PautaService pautaService;

    @Autowired
    public PautaController(PautaService pautaService) {
        this.pautaService = pautaService;
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

}
