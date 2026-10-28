package com.locacaoquadras.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.locacaoquadras.model.Modalidade;

public class ModalidadeDAO {

    private Connection conectar() throws SQLException {
        return ConexaoBD.getConexao();
    }

    // Salvar ou Atualizar
    public void salvar(int id, String nome) throws SQLException {
        try (Connection con = conectar()) {
            if (id > 0) {
                String sql = "UPDATE modalidade SET nome = ? WHERE id_modalidade = ?";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, nome);
                    ps.setInt(2, id);
                    ps.executeUpdate();
                }
            } else {
                String sql = "INSERT INTO modalidade (nome) VALUES (?)";
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, nome);
                    ps.executeUpdate();
                }
            }
        }
    }

    public List<Modalidade> listar() throws SQLException {
        String sql = "SELECT id_modalidade, nome FROM modalidade ORDER BY nome";
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
        String sql = "SELECT id_modalidade, nome FROM modalidade WHERE id_modalidade = ?";
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
        return m;
    }
}