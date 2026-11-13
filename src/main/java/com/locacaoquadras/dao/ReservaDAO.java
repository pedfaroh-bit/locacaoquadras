package com.locacaoquadras.dao;

import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.locacaoquadras.config.ConnectionPoolConfig;
import com.locacaoquadras.model.Reserva;

public class ReservaDAO {

    // Traz também o nome do usuário, a quadra e o horário para mostrar na tabela
    private static final String SELECT =
        "SELECT r.*, u.nome AS nome_usuario, q.nome AS nome_quadra, h.hora_inicio, h.hora_fim "
        + "FROM reserva r "
        + "JOIN usuario u ON u.id_usuario = r.id_usuario "
        + "JOIN horario h ON h.id_horario = r.id_horario "
        + "JOIN quadra q ON q.id_quadra = h.id_quadra";

    public void createReserva(Reserva r) throws SQLException {
        String sql = "INSERT INTO reserva (id_usuario, id_horario, data_reserva, status, valor_pago) "
            + "VALUES (?, ?, ?, 'Ativa', ?)";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getIdUsuario());
            ps.setInt(2, r.getIdHorario());
            ps.setObject(3, r.getDataReserva());
            ps.setBigDecimal(4, r.getValorPago());
            ps.executeUpdate();
        }
    }

    public List<Reserva> findAllReservas() throws SQLException {
        String sql = SELECT + " ORDER BY r.data_reserva DESC, h.hora_inicio";
        List<Reserva> lista = new ArrayList<>();
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(montar(rs));
            }
        }
        return lista;
    }

    public Reserva findReservaById(int id) throws SQLException {
        String sql = SELECT + " WHERE r.id_reserva = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return montar(rs);
                }
            }
        }
        return null;
    }

    // Ao cancelar, grava a data do cancelamento (só na primeira vez). Ao reativar, limpa os dados do cancelamento.
    public void updateReserva(Reserva r) throws SQLException {
        String sql = "UPDATE reserva SET id_usuario = ?, id_horario = ?, data_reserva = ?, status = ?, "
            + "valor_pago = ?, valor_devolvido = ?, "
            + "data_cancelamento = CASE WHEN ? = 'Cancelada' THEN COALESCE(data_cancelamento, CURRENT_TIMESTAMP) ELSE NULL END "
            + "WHERE id_reserva = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, r.getIdUsuario());
            ps.setInt(2, r.getIdHorario());
            ps.setObject(3, r.getDataReserva());
            ps.setString(4, r.getStatus());
            ps.setBigDecimal(5, r.getValorPago());
            ps.setBigDecimal(6, r.getValorDevolvido());
            ps.setString(7, r.getStatus());
            ps.setInt(8, r.getIdReserva());
            ps.executeUpdate();
        }
    }

    public void deleteReservaById(int id) throws SQLException {
        String sql = "DELETE FROM reserva WHERE id_reserva = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Verifica se já existe outra reserva ativa no mesmo horário e data
    public boolean existeReservaAtiva(int idHorario, LocalDate data, int idReservaIgnorada) throws SQLException {
        String sql = "SELECT COUNT(*) FROM reserva WHERE id_horario = ? AND data_reserva = ? "
            + "AND status = 'Ativa' AND id_reserva <> ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idHorario);
            ps.setObject(2, data);
            ps.setInt(3, idReservaIgnorada);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    // Preço da modalidade da quadra: sábado e domingo usam o preço de fim de semana
    public BigDecimal calcularValor(int idHorario, LocalDate data) throws SQLException {
        String sql = "SELECT m.preco_semana, m.preco_fimdesemana FROM horario h "
            + "JOIN quadra q ON q.id_quadra = h.id_quadra "
            + "JOIN modalidade m ON m.id_modalidade = q.id_modalidade "
            + "WHERE h.id_horario = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idHorario);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    throw new SQLException("Horário não encontrado");
                }
                DayOfWeek dia = data.getDayOfWeek();
                boolean fimDeSemana = dia == DayOfWeek.SATURDAY || dia == DayOfWeek.SUNDAY;
                return fimDeSemana ? rs.getBigDecimal("preco_fimdesemana") : rs.getBigDecimal("preco_semana");
            }
        }
    }

    private Reserva montar(ResultSet rs) throws SQLException {
        Reserva r = new Reserva();
        r.setIdReserva(rs.getInt("id_reserva"));
        r.setIdUsuario(rs.getInt("id_usuario"));
        r.setIdHorario(rs.getInt("id_horario"));
        r.setDataReserva(rs.getObject("data_reserva", LocalDate.class));
        r.setStatus(rs.getString("status"));
        r.setValorPago(rs.getBigDecimal("valor_pago"));
        r.setValorDevolvido(rs.getBigDecimal("valor_devolvido"));
        r.setDataCancelamento(rs.getObject("data_cancelamento", LocalDateTime.class));
        r.setNomeUsuario(rs.getString("nome_usuario"));
        r.setNomeQuadra(rs.getString("nome_quadra"));
        r.setHoraInicio(rs.getObject("hora_inicio", LocalTime.class));
        r.setHoraFim(rs.getObject("hora_fim", LocalTime.class));
        return r;
    }
}
