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
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Set;

import static java.util.Objects.isNull;

@Entity
@Getter
@Setter
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
