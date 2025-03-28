package com.voting.app.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

import java.time.LocalDate;

@Entity
public class Pauta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String campo1;

    private Integer campo2;

    private String idCampoTexto;

    private Integer idCampoNumerico;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate idCampoData;

    public Long getId() {
        return id;
    }

    public String getCampo1() {
        return campo1;
    }

    public void setCampo1(String campo1) {
        this.campo1 = campo1;
    }

    public Integer getCampo2() {
        return campo2;
    }

    public void setCampo2(Integer campo2) {
        this.campo2 = campo2;
    }

    public String getIdCampoTexto() {
        return idCampoTexto;
    }

    public void setIdCampoTexto(String idCampoTexto) {
        this.idCampoTexto = idCampoTexto;
    }

    public Integer getIdCampoNumerico() {
        return idCampoNumerico;
    }

    public void setIdCampoNumerico(Integer idCampoNumerico) {
        this.idCampoNumerico = idCampoNumerico;
    }

    public LocalDate getIdCampoData() {
        return idCampoData;
    }

    public void setIdCampoData(LocalDate idCampoData) {
        this.idCampoData = idCampoData;
    }
}
