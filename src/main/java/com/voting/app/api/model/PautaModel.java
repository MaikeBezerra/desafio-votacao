package com.voting.app.api.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PautaModel {

    private Long id;
    private String campo1;
    private Integer campo2;
    private String idCampoTexto;
    private Integer idCampoNumerico;
    private LocalDate idCampoData;
    private LocalDateTime dataFechamento;

}
