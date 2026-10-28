package com.locacaoquadras.model;

import java.math.BigDecimal;

public class Modalidade {
    private int idModalidade;
    private String nome;
    private BigDecimal precoSemana;
    private BigDecimal precoFimdesemana;

    public int getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(int idModalidade) {
        this.idModalidade = idModalidade;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public BigDecimal getPrecoSemana() {
        return precoSemana;
    }

    public void setPrecoSemana(BigDecimal precoSemana) {
        this.precoSemana = precoSemana;
    }

    public BigDecimal getPrecoFimdesemana() {
        return precoFimdesemana;
    }

    public void setPrecoFimdesemana(BigDecimal precoFimdesemana) {
        this.precoFimdesemana = precoFimdesemana;
    }
}