package com.locacaoquadras.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.locacaoquadras.model.Quadra;

public class QuadraDAO {

    private static final String URL =
            "jdbc:mysql://localhost:3306/locacao_quadras?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "sua_senha";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public void inserir(Quadra q) throws SQLException {
        String sql = "INSERT INTO quadra (nome, endereco, descricao, id_modalidade) VALUES (?, ?, ?, ?)";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, q.getNome());
            ps.setString(2, q.getEndereco());
            ps.setString(3, q.getDescricao());
            ps.setInt(4, q.getIdModalidade());
            ps.executeUpdate();
        }
    }

    public List<Quadra> listar() throws SQLException {
        String sql = "SELECT * FROM quadra ORDER BY id_quadra";
        List<Quadra> lista = new ArrayList<>();

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(montar(rs));
            }
        }
        return lista;
    }

    // NOVO: busca por nome, endereço ou modalidade
    public List<Quadra> buscar(String termo) throws SQLException {
        if (termo == null || termo.trim().isEmpty()) {
            return listar();
        }
        String sql = SELECT_BASE
                + "WHERE q.nome ILIKE ? OR q.endereco ILIKE ? OR m.nome ILIKE ? "
                + "ORDER BY q.id_quadra";
        String filtro = "%" + termo.trim() + "%";
        List<Quadra> lista = new ArrayList<>();
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, filtro);
            ps.setString(2, filtro);
            ps.setString(3, filtro);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    lista.add(montar(rs));
                }
            }
        }
        return lista;
    }

    public Quadra buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM quadra WHERE id_quadra = ?";

        try (Connection con = conectar();
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

    public void atualizar(Quadra q) throws SQLException {
        String sql = "UPDATE quadra SET nome = ?, endereco = ?, descricao = ?, id_modalidade = ? WHERE id_quadra = ?";

        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, q.getNome());
            ps.setString(2, q.getEndereco());
            ps.setString(3, q.getDescricao());
            ps.setInt(4, q.getIdModalidade());
            ps.setInt(5, q.getIdQuadra());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM quadra WHERE id_quadra = ?";

        try (Connection con = conectar();
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
        return q;
    }
}