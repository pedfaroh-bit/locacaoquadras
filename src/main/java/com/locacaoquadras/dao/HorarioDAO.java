package com.locacaoquadras.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import com.locacaoquadras.config.ConnectionPoolConfig;
import com.locacaoquadras.model.Horario;

public class HorarioDAO {

    // Traz também o nome da quadra para mostrar na tabela
    private static final String SELECT =
        "SELECT h.*, q.nome AS nome_quadra FROM horario h "
        + "JOIN quadra q ON q.id_quadra = h.id_quadra";

    public void createHorario(Horario h) throws SQLException {
        String sql = "INSERT INTO horario (hora_inicio, hora_fim, id_quadra) VALUES (?, ?, ?)";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, h.getHoraInicio());
            ps.setObject(2, h.getHoraFim());
            ps.setInt(3, h.getIdQuadra());
            ps.executeUpdate();
        }
    }

    public List<Horario> findAllHorarios() throws SQLException {
        String sql = SELECT + " ORDER BY q.nome, h.hora_inicio";
        List<Horario> lista = new ArrayList<>();
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(montar(rs));
            }
        }
        return lista;
    }

    public Horario findHorarioById(int id) throws SQLException {
        String sql = SELECT + " WHERE h.id_horario = ?";
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

    public void updateHorario(Horario h) throws SQLException {
        String sql = "UPDATE horario SET hora_inicio = ?, hora_fim = ?, id_quadra = ? WHERE id_horario = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setObject(1, h.getHoraInicio());
            ps.setObject(2, h.getHoraFim());
            ps.setInt(3, h.getIdQuadra());
            ps.setInt(4, h.getIdHorario());
            ps.executeUpdate();
        }
    }

    public void deleteHorarioById(int id) throws SQLException {
        String sql = "DELETE FROM horario WHERE id_horario = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Horario montar(ResultSet rs) throws SQLException {
        Horario h = new Horario();
        h.setIdHorario(rs.getInt("id_horario"));
        h.setHoraInicio(rs.getObject("hora_inicio", LocalTime.class));
        h.setHoraFim(rs.getObject("hora_fim", LocalTime.class));
        h.setIdQuadra(rs.getInt("id_quadra"));
        h.setNomeQuadra(rs.getString("nome_quadra"));
        return h;
    }
}
