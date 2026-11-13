package com.locacaoquadras.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.locacaoquadras.config.ConnectionPoolConfig;
import com.locacaoquadras.model.Bloqueio;

public class BloqueioDAO {

    // Traz também o nome do administrador, a quadra e o horário para mostrar na tabela
    private static final String SELECT =
        "SELECT b.*, a.nome AS nome_administrador, q.nome AS nome_quadra, h.hora_inicio, h.hora_fim "
        + "FROM bloqueio b "
        + "JOIN administrador a ON a.id_administrador = b.id_administrador "
        + "JOIN horario h ON h.id_horario = b.id_horario "
        + "JOIN quadra q ON q.id_quadra = h.id_quadra";

    public void createBloqueio(Bloqueio b) throws SQLException {
        String sql = "INSERT INTO bloqueio (id_administrador, id_horario, data_bloqueio, motivo) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, b.getIdAdministrador());
            ps.setInt(2, b.getIdHorario());
            ps.setObject(3, b.getDataBloqueio());
            ps.setString(4, b.getMotivo());
            ps.executeUpdate();
        }
    }

    public List<Bloqueio> findAllBloqueios() throws SQLException {
        String sql = SELECT + " ORDER BY b.data_bloqueio DESC, h.hora_inicio";
        List<Bloqueio> lista = new ArrayList<>();
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(montar(rs));
            }
        }
        return lista;
    }

    public Bloqueio findBloqueioById(int id) throws SQLException {
        String sql = SELECT + " WHERE b.id_bloqueio = ?";
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

    public void updateBloqueio(Bloqueio b) throws SQLException {
        String sql = "UPDATE bloqueio SET id_administrador = ?, id_horario = ?, data_bloqueio = ?, motivo = ? WHERE id_bloqueio = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, b.getIdAdministrador());
            ps.setInt(2, b.getIdHorario());
            ps.setObject(3, b.getDataBloqueio());
            ps.setString(4, b.getMotivo());
            ps.setInt(5, b.getIdBloqueio());
            ps.executeUpdate();
        }
    }

    public void deleteBloqueioById(int id) throws SQLException {
        String sql = "DELETE FROM bloqueio WHERE id_bloqueio = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Verifica se o horário está bloqueado na data
    public boolean existeBloqueio(int idHorario, LocalDate data) throws SQLException {
        String sql = "SELECT COUNT(*) FROM bloqueio WHERE id_horario = ? AND data_bloqueio = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, idHorario);
            ps.setObject(2, data);
            try (ResultSet rs = ps.executeQuery()) {
                rs.next();
                return rs.getInt(1) > 0;
            }
        }
    }

    private Bloqueio montar(ResultSet rs) throws SQLException {
        Bloqueio b = new Bloqueio();
        b.setIdBloqueio(rs.getInt("id_bloqueio"));
        b.setIdAdministrador(rs.getInt("id_administrador"));
        b.setIdHorario(rs.getInt("id_horario"));
        b.setDataBloqueio(rs.getObject("data_bloqueio", LocalDate.class));
        b.setMotivo(rs.getString("motivo"));
        b.setNomeAdministrador(rs.getString("nome_administrador"));
        b.setNomeQuadra(rs.getString("nome_quadra"));
        b.setHoraInicio(rs.getObject("hora_inicio", LocalTime.class));
        b.setHoraFim(rs.getObject("hora_fim", LocalTime.class));
        return b;
    }
}
