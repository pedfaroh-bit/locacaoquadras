package com.locacaoquadras.model;

// Um horário da quadra em uma data específica, dizendo se dá para reservar.
public class HorarioDisponivel {

    private final Horario horario;
    private final boolean disponivel;
    private final String motivo; // "", "Reservado", "Bloqueado" ou "Encerrado"

    public HorarioDisponivel(Horario horario, boolean disponivel, String motivo) {
        this.horario = horario;
        this.disponivel = disponivel;
        this.motivo = motivo;
    }

    public Horario getHorario() {
        return horario;
    }

    public boolean isDisponivel() {
        return disponivel;
    }

    public String getMotivo() {
        return motivo;
    }
}
