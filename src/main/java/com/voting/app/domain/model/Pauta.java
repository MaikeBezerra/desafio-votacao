package com.voting.app.domain.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Objects.isNull;

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

    // Momento que a pauta será fechada
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm:ss")
    private LocalDateTime dataFechamento;

    // Quantidade de votos sim
    private Integer votoSim;

    // Quantidade de votos nao
    private Integer votoNao;

    // Guarda a lista de CPF dos associados votantes
    // Em uma solução mais complexa poderiamos considerar uma Lista de objetos reperesentando os associados
    @ElementCollection
    @CollectionTable(name = "pauta_associados", joinColumns = @JoinColumn(name = "id"))
    @Column(name = "idAssociado")
    private Set<String> associados;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public LocalDateTime getDataFechamento() {
        return dataFechamento;
    }

    public void setDataFechamento(LocalDateTime dataFechamento) {
        this.dataFechamento = dataFechamento;
    }

    public Integer getVotoSim() {
        // Evitar null pointer, outra solução seria criar a tabela com valor default 0
        return votoSim == null ? 0 : votoSim;
    }

    public void setVotoSim(Integer votoSim) {
        this.votoSim = votoSim;
    }

    public Integer getVotoNao() {
        // Evitar null pointer, outra solução seria criar a tabela com valor default 0
        return votoNao == null ? 0 : votoNao;
    }

    public void setVotoNao(Integer votoNao) {
        this.votoNao = votoNao;
    }

    public boolean addVoto(String idAssociado) {
        if (isNull(this.associados)) {
            associados = new LinkedHashSet<>();
        }

        return associados.add(idAssociado);
    }
}
