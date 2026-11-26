package com.locacaoquadras.model;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.locacaoquadras.util.Formato;

public class Quadra {

    private int idQuadra;
    private String nome;
    private String endereco;
    private String descricao;
    private int idModalidade;
    private String nomeModalidade;
    private BigDecimal precoSemana;
    private BigDecimal precoFimdesemana;
    private boolean ativa = true;

    public int getIdQuadra() {
        return idQuadra;
    }

    public void setIdQuadra(int idQuadra) {
        this.idQuadra = idQuadra;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public int getIdModalidade() {
        return idModalidade;
    }

    public void setIdModalidade(int idModalidade) {
        this.idModalidade = idModalidade;
    }

    public String getNomeModalidade() {
        return nomeModalidade;
    }

    public void setNomeModalidade(String nomeModalidade) {
        this.nomeModalidade = nomeModalidade;
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

    public boolean isAtiva() {
        return ativa;
    }

    public void setAtiva(boolean ativa) {
        this.ativa = ativa;
    }

    public String getPrecoSemanaFormatado() {
        return Formato.moeda(precoSemana);
    }

    public String getPrecoFimdesemanaFormatado() {
        return Formato.moeda(precoFimdesemana);
    }

    // A descrição é um texto separado por vírgula (ex.: "Grama sintética, Iluminação LED").
    // Cada pedaço vira um cartão em "Descrição e estrutura".
    public List<String> getItensEstrutura() {
        List<String> itens = new ArrayList<>();
        if (descricao != null) {
            for (String parte : descricao.split("[,;\\n]")) {
                if (!parte.trim().isEmpty()) {
                    itens.add(parte.trim());
                }
            }
        }
        return itens;
    }
}
