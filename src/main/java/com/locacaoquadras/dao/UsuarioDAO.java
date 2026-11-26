package com.locacaoquadras.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.locacaoquadras.config.ConnectionPoolConfig;
import com.locacaoquadras.model.Usuario;

public class UsuarioDAO {

    public void createUsuario(Usuario u) throws SQLException {
        String sql = "INSERT INTO usuario (nome, email, senha, telefone, ativo) VALUES (?, ?, ?, ?, ?)";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, u.getNome());
            ps.setString(2, u.getEmail());
            ps.setString(3, BCrypt.hashpw(u.getSenha(), BCrypt.gensalt()));
            ps.setString(4, u.getTelefone());
            ps.setBoolean(5, u.isAtivo());
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
        boolean trocarSenha = u.getSenha() != null && !u.getSenha().trim().isEmpty();
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
                ps.setString(i++, BCrypt.hashpw(u.getSenha(), BCrypt.gensalt()));
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

    public Usuario autenticar(String login, String senha) throws SQLException {
        if (login == null || senha == null || login.trim().isEmpty()) return null;

        String sql = "SELECT id_usuario, nome, email, telefone, senha, ativo "
                + "FROM usuario WHERE email = ? OR telefone = ?";

        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, login.trim());
            ps.setString(2, login.trim());

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    if (BCrypt.checkpw(senha, rs.getString("senha"))) {
                        Usuario u = new Usuario();
                        u.setIdUsuario(rs.getInt("id_usuario"));
                        u.setNome(rs.getString("nome"));
                        u.setEmail(rs.getString("email"));
                        u.setTelefone(rs.getString("telefone"));
                        u.setAtivo(rs.getBoolean("ativo"));
                        return u;
                    }
                }
            }
        }
        return null;
    }

    private Usuario montar(ResultSet rs) throws SQLException {
        Usuario u = new Usuario();
        u.setIdUsuario(rs.getInt("id_usuario"));
        u.setNome(rs.getString("nome"));
        u.setEmail(rs.getString("email"));
        u.setTelefone(rs.getString("telefone"));
        u.setAtivo(rs.getBoolean("ativo"));
        return u;
    }

    // Usado pelo administrador para ativar/desativar o acesso de um cliente
    public void updateAtivo(int idUsuario, boolean ativo) throws SQLException {
        String sql = "UPDATE usuario SET ativo = ? WHERE id_usuario = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setBoolean(1, ativo);
            ps.setInt(2, idUsuario);
            ps.executeUpdate();
        }
    }

    public int contarUsuarios() throws SQLException {
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement("SELECT COUNT(*) FROM usuario");
             ResultSet rs = ps.executeQuery()) {
            rs.next();
            return rs.getInt(1);
        }
    }
}
