package com.voting.app.api.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ResultadoModel {

    @JsonProperty("pauta_codigo")
    private Long id;

    @JsonProperty("total_sim")
    private Integer votoSim;

    @JsonProperty("total_nao")
    private Integer votoNao;

}
