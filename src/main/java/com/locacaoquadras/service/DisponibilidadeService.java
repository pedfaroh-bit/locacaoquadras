package com.locacaoquadras.service;

import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import com.locacaoquadras.dao.BloqueioDAO;
import com.locacaoquadras.dao.HorarioDAO;
import com.locacaoquadras.dao.ReservaDAO;
import com.locacaoquadras.model.Horario;
import com.locacaoquadras.model.HorarioDisponivel;
import com.locacaoquadras.model.Quadra;

public class DisponibilidadeService {

    private final HorarioDAO horarioDAO = new HorarioDAO();
    private final ReservaDAO reservaDAO = new ReservaDAO();
    private final BloqueioDAO bloqueioDAO = new BloqueioDAO();

    // Todos os horários da quadra em uma data, marcando quais dá para reservar
    public List<HorarioDisponivel> listar(int idQuadra, LocalDate data) throws SQLException {
        List<Horario> horarios = horarioDAO.findByQuadra(idQuadra);
        Set<Integer> reservados = reservaDAO.idsHorariosReservados(idQuadra, data);
        Set<Integer> bloqueados = bloqueioDAO.idsHorariosBloqueados(idQuadra, data);
        LocalDateTime agora = LocalDateTime.now();

        List<HorarioDisponivel> lista = new ArrayList<>();
        for (Horario h : horarios) {
            if (data.atTime(h.getHoraInicio()).isBefore(agora)) {
                lista.add(new HorarioDisponivel(h, false, "Encerrado"));
            } else if (bloqueados.contains(h.getIdHorario())) {
                lista.add(new HorarioDisponivel(h, false, "Bloqueado"));
            } else if (reservados.contains(h.getIdHorario())) {
                lista.add(new HorarioDisponivel(h, false, "Reservado"));
            } else {
                lista.add(new HorarioDisponivel(h, true, ""));
            }
        }
        return lista;
    }

    // A quadra tem pelo menos um horário livre na data (e, se informado, a partir da hora pedida)?
    public boolean temDisponivel(int idQuadra, LocalDate data, java.time.LocalTime aPartirDe) throws SQLException {
        for (HorarioDisponivel hd : listar(idQuadra, data)) {
            if (hd.isDisponivel()
                    && (aPartirDe == null || !hd.getHorario().getHoraInicio().isBefore(aPartirDe))) {
                return true;
            }
        }
        return false;
    }

    // Sábado e domingo usam o preço de fim de semana
    public static BigDecimal preco(Quadra q, LocalDate data) {
        DayOfWeek dia = data.getDayOfWeek();
        boolean fimDeSemana = dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
        return fimDeSemana ? q.getPrecoFimdesemana() : q.getPrecoSemana();
    }
}
