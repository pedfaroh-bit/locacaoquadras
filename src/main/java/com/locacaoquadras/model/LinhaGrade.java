package com.locacaoquadras.model;

import java.util.List;

// Uma linha da grade semanal de horários do administrador: o horário e o estado de cada dia da semana
public class LinhaGrade {

    private final Horario horario;
    private final List<String> celulas; // "Livre", "Reservado", "Bloqueado" ou "-" (dia que já passou)

    public LinhaGrade(Horario horario, List<String> celulas) {
        this.horario = horario;
        this.celulas = celulas;
    }

    public Horario getHorario() {
        return horario;
    }

    public List<String> getCelulas() {
        return celulas;
    }
}
