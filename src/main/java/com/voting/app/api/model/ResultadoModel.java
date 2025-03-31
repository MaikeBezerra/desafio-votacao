package com.voting.app.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class ResultadoModel {

    @JsonProperty("sessaoId")
    private Long id;

    @JsonProperty("Sim")
    private Integer votoSim;

    @JsonProperty("Nao")
    private Integer votoNao;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getVotoSim() {
        return votoSim;
    }

    public void setVotoSim(Integer votoSim) {
        this.votoSim = votoSim;
    }

    public Integer getVotoNao() {
        return votoNao;
    }

    public void setVotoNao(Integer votoNao) {
        this.votoNao = votoNao;
    }
}
