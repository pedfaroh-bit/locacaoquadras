package com.locacaoquadras.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.locacaoquadras.config.ConnectionPoolConfig;
import com.locacaoquadras.model.Usuario;

public class UsuarioDAO {

    public void createUsuario(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha, telefone) VALUES (?, ?, ?, ?)";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setString(3, u.getSenha());
            ps.setString(4, u.getTelefone());
            ps.executeUpdate();
        }
    }

    public List<Usuario> findAllUsuarios() throws SQLException {
        String sql = "SELECT * FROM usuario ORDER BY nome";
        List<Usuario> lista = new ArrayList<>();
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(montar(rs));
            }
        }
        return lista;
    }

    public Usuario findUsuarioById(int id) throws SQLException {
        String sql = "SELECT * FROM usuario WHERE id_usuario = ?";
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

    // Se a senha vier vazia, mantém a senha atual
    public void updateUsuario(Usuario u) throws SQLException {
        boolean trocarSenha = u.getSenha() != null && !u.getSenha().isBlank();
        String sql = trocarSenha
            ? "UPDATE usuario SET nome = ?, email = ?, telefone = ?, senha = ? WHERE id_usuario = ?"
            : "UPDATE usuario SET nome = ?, email = ?, telefone = ? WHERE id_usuario = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, u.getNome());
            ps.setString(i++, u.getEmail());
            ps.setString(i++, u.getTelefone());
            if (trocarSenha) {
                ps.setString(i++, u.getSenha());
            }
            ps.setInt(i, u.getIdUsuario());
            ps.executeUpdate();
        }
    }

    public void deleteUsuarioById(int id) throws SQLException {
        String sql = "DELETE FROM usuario WHERE id_usuario = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    private Usuario montar(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNome(rs.getString("nome"));
        u.setEmail(rs.getString("email"));
        u.setTelefone(rs.getString("telefone"));
        // a senha não é carregada para não aparecer nas telas
        return u;
    }
}
