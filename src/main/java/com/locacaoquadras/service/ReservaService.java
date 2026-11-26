package com.locacaoquadras.service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.locacaoquadras.dao.BloqueioDAO;
import com.locacaoquadras.dao.HorarioDAO;
import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.dao.ReservaDAO;
import com.locacaoquadras.dao.UsuarioDAO;
import com.locacaoquadras.model.Horario;
import com.locacaoquadras.model.Quadra;
import com.locacaoquadras.model.Reserva;
import com.locacaoquadras.model.Usuario;

// Regras de negócio das reservas, usadas tanto pelo cliente quanto pelo administrador.
public final class ReservaService {

    // Cancelando com pelo menos 24h de antecedência devolve 100%; depois disso, 50%.
    // Para mudar a política, altere só estas duas constantes.
    public static final long HORAS_PARA_REEMBOLSO_TOTAL = 24;
    public static final BigDecimal PERCENTUAL_TARDIO = new BigDecimal("0.50");

    // Impede duas reservas simultâneas do mesmo horário
    private static final Object TRAVA = new Object();

    private ReservaService() {
    }

    // Retorna null se reservou, ou a mensagem de erro para mostrar na tela
    public static String reservar(int idUsuario, int idHorario, LocalDate data) throws SQLException {
        if (data == null) {
            return "Escolha a data da reserva.";
        }
        Usuario cliente = new UsuarioDAO().findUsuarioById(idUsuario);
        if (cliente == null || !cliente.isAtivo()) {
            return "Este usuário não pode fazer reservas.";
        }
        Horario h = new HorarioDAO().findHorarioById(idHorario);
        if (h == null) {
            return "Horário não encontrado.";
        }
        Quadra q = new QuadraDAO().findQuadraById(h.getIdQuadra());
        if (q == null || !q.isAtiva()) {
            return "Esta quadra não está disponível para reservas.";
        }
        if (data.atTime(h.getHoraInicio()).isBefore(LocalDateTime.now())) {
            return "Esse horário já passou. Escolha outro.";
        }
        if (data.isAfter(LocalDate.now().plusMonths(6))) {
            return "Só é possível reservar com até 6 meses de antecedência.";
        }

        synchronized (TRAVA) {
            if (new BloqueioDAO().existeBloqueio(idHorario, data)) {
                return "Este horário está bloqueado nessa data.";
            }
            ReservaDAO dao = new ReservaDAO();
            if (dao.existeReservaAtiva(idHorario, data, 0)) {
                return "Este horário já foi reservado por outra pessoa.";
            }
            Reserva r = new Reserva();
            r.setIdUsuario(idUsuario);
            r.setIdHorario(idHorario);
            r.setDataReserva(data);
            r.setValorPago(dao.calcularValor(idHorario, data));
            dao.createReserva(r);
        }
        return null;
    }

    // Valor devolvido ao cliente conforme a antecedência do cancelamento
    public static BigDecimal valorDevolucao(Reserva r) {
        LocalDateTime inicio = r.getDataReserva().atTime(r.getHoraInicio());
        boolean cedo = !LocalDateTime.now().plusHours(HORAS_PARA_REEMBOLSO_TOTAL).isAfter(inicio);
        if (cedo) {
            return r.getValorPago();
        }
        return r.getValorPago().multiply(PERCENTUAL_TARDIO).setScale(2, RoundingMode.HALF_UP);
    }
}
