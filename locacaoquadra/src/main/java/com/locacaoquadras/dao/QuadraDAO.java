package com.locacaoquadras.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.locacaoquadras.config.ConnectionPoolConfig;
import com.locacaoquadras.model.Quadra;

public class QuadraDAO {

    // Traz também o nome da modalidade para mostrar na tabela
    private static final String SELECT =
        "SELECT q.*, m.nome AS nome_modalidade FROM quadra q "
        + "JOIN modalidade m ON m.id_modalidade = q.id_modalidade";

    public void createQuadra(Quadra q) throws SQLException {
        String sql = "INSERT INTO quadra (nome, endereco, descricao, id_modalidade) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, q.getNome());
            ps.setString(2, q.getEndereco());
            ps.setString(3, q.getDescricao());
            ps.setInt(4, q.getIdModalidade());
            ps.executeUpdate();
        }
    }

    public List<Quadra> findAllQuadras() throws SQLException {
        String sql = SELECT + " ORDER BY q.nome";
        List<Quadra> lista = new ArrayList<>();

        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(montar(rs));
            }
        }
        return lista;
    }

    public Quadra findQuadraById(int id) throws SQLException {
        String sql = SELECT + " WHERE q.id_quadra = ?";

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

    public void updateQuadra(Quadra q) throws SQLException {
        String sql = "UPDATE quadra SET nome = ?, endereco = ?, descricao = ?, id_modalidade = ? WHERE id_quadra = ?";

        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, q.getNome());
            ps.setString(2, q.getEndereco());
            ps.setString(3, q.getDescricao());
            ps.setInt(4, q.getIdModalidade());
            ps.setInt(5, q.getIdQuadra());
            ps.executeUpdate();
        }
    }

    public void deleteQuadraById(int id) throws SQLException {
        String sql = "DELETE FROM quadra WHERE id_quadra = ?";

        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Quadra montar(ResultSet rs) throws SQLException {
        Quadra q = new Quadra();
        q.setIdQuadra(rs.getInt("id_quadra"));
        q.setNome(rs.getString("nome"));
        q.setEndereco(rs.getString("endereco"));
        q.setDescricao(rs.getString("descricao"));
        q.setIdModalidade(rs.getInt("id_modalidade"));
        q.setNomeModalidade(rs.getString("nome_modalidade"));
        return q;
    }
}
