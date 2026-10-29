package com.locacaoquadras.model;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Bloqueio {

    private int idBloqueio;
    private int idAdministrador;
    private int idHorario;
    private LocalDate dataBloqueio;
    private String motivo;

    // Dados das tabelas ligadas, só para exibir na tela
    private String nomeAdministrador;
    private String nomeQuadra;
    private LocalTime horaInicio;
    private LocalTime horaFim;

    public int getIdBloqueio() {
        return idBloqueio;
    }

    public void setIdBloqueio(int idBloqueio) {
        this.idBloqueio = idBloqueio;
    }

    public int getIdAdministrador() {
        return idAdministrador;
    }

    public void setIdAdministrador(int idAdministrador) {
        this.idAdministrador = idAdministrador;
    }

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
    }

    public LocalDate getDataBloqueio() {
        return dataBloqueio;
    }

    public void setDataBloqueio(LocalDate dataBloqueio) {
        this.dataBloqueio = dataBloqueio;
    }

    public String getMotivo() {
        return motivo;
    }

    public void setMotivo(String motivo) {
        this.motivo = motivo;
    }

    public String getNomeAdministrador() {
        return nomeAdministrador;
    }

    public void setNomeAdministrador(String nomeAdministrador) {
        this.nomeAdministrador = nomeAdministrador;
    }

    public String getNomeQuadra() {
        return nomeQuadra;
    }

    public void setNomeQuadra(String nomeQuadra) {
        this.nomeQuadra = nomeQuadra;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFim() {
        return horaFim;
    }

    public void setHoraFim(LocalTime horaFim) {
        this.horaFim = horaFim;
    }

    public String getDataBloqueioFormatada() {
        return dataBloqueio == null ? "" : dataBloqueio.format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }
}
