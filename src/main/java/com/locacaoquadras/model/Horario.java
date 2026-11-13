package com.locacaoquadras.model;

import java.time.LocalTime;

public class Horario {

    private int idHorario;
    private LocalTime horaInicio;
    private LocalTime horaFim;
    private int idQuadra;
    private String nomeQuadra;

    public int getIdHorario() {
        return idHorario;
    }

    public void setIdHorario(int idHorario) {
        this.idHorario = idHorario;
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

    public int getIdQuadra() {
        return idQuadra;
    }

    public void setIdQuadra(int idQuadra) {
        this.idQuadra = idQuadra;
    }

    public String getNomeQuadra() {
        return nomeQuadra;
    }

    public void setNomeQuadra(String nomeQuadra) {
        this.nomeQuadra = nomeQuadra;
    }

    // Texto usado nas listas de seleção, ex.: "Quadra 1 - 08:00 às 09:00"
    public String getDescricao() {
        return nomeQuadra + " - " + horaInicio + " às " + horaFim;
    }
}
