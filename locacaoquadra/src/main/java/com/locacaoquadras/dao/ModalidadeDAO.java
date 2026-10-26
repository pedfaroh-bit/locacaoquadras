package com.locacaoquadras.dao;

import com.locacaoquadras.model.Modalidade;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ModalidadeDAO {

    private static final String URL = "jdbc:mysql://localhost:3306/locacao_quadras?useSSL=false&serverTimezone=UTC";
    private static final String USUARIO = "root";
    private static final String SENHA = "sua_senha";

    private Connection conectar() throws SQLException {
        return DriverManager.getConnection(URL, USUARIO, SENHA);
    }

    public void inserir(Modalidade m) throws SQLException {
        String sql = "INSERT INTO modalidade (nome, preco_semana, preco_fimdesemana) VALUES (?, ?, ?)";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNome());
            ps.setBigDecimal(2, m.getPrecoSemana());
            ps.setBigDecimal(3, m.getPrecoFimdesemana());
            ps.executeUpdate();
        }
    }

    public List<Modalidade> listar() throws SQLException {
        String sql = "SELECT * FROM modalidade ORDER BY id_modalidade";
        List<Modalidade> lista = new ArrayList<>();
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(montar(rs));
            }
        }
        return lista;
    }

    public Modalidade buscarPorId(int id) throws SQLException {
        String sql = "SELECT * FROM modalidade WHERE id_modalidade = ?";
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

    public void atualizar(Modalidade m) throws SQLException {
        String sql = "UPDATE modalidade SET nome = ?, preco_semana = ?, preco_fimdesemana = ? WHERE id_modalidade = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, m.getNome());
            ps.setBigDecimal(2, m.getPrecoSemana());
            ps.setBigDecimal(3, m.getPrecoFimdesemana());
            ps.setInt(4, m.getIdModalidade());
            ps.executeUpdate();
        }
    }

    public void excluir(int id) throws SQLException {
        String sql = "DELETE FROM modalidade WHERE id_modalidade = ?";
        try (Connection con = conectar();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Modalidade montar(ResultSet rs) throws SQLException {
        Modalidade m = new Modalidade();
        m.setIdModalidade(rs.getInt("id_modalidade"));
        m.setNome(rs.getString("nome"));
        m.setPrecoSemana(rs.getBigDecimal("preco_semana"));
        m.setPrecoFimdesemana(rs.getBigDecimal("preco_fimdesemana"));
        return m;
    }
}
