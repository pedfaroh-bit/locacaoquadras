package com.locacaoquadras.model;

public class Modalidade {

    private int idModalidade;
    private String nome;

    public Modalidade() {
    }

    public Modalidade(int idModalidade, String nome) {
        this.idModalidade = idModalidade;
        this.nome = nome;
    }

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
}