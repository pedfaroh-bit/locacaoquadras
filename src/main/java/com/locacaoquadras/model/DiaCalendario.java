package com.locacaoquadras.model;

import java.time.LocalDate;

// Uma célula do calendário da tela "Realizar Reserva". Células vazias (data nula) só alinham a semana.
public class DiaCalendario {

    private final LocalDate data;
    private final boolean passado;
    private final boolean selecionado;

    public DiaCalendario(LocalDate data, boolean passado, boolean selecionado) {
        this.data = data;
        this.passado = passado;
        this.selecionado = selecionado;
    }

    public boolean isVazio() {
        return data == null;
    }

    public int getNumero() {
        return data == null ? 0 : data.getDayOfMonth();
    }

    public String getIso() {
        return data == null ? "" : data.toString();
    }

    public boolean isPassado() {
        return passado;
    }

    public boolean isSelecionado() {
        return selecionado;
    }
}
