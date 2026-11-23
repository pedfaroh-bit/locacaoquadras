package com.locacaoquadras.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.mindrot.jbcrypt.BCrypt;

import com.locacaoquadras.config.ConnectionPoolConfig;
import com.locacaoquadras.model.Administrador;

public class AdministradorDAO {

    public void createAdministrador(Administrador a) throws SQLException {
        String sql = "INSERT INTO administrador (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, a.getNome());
            ps.setString(2, a.getEmail());
            ps.setString(3, BCrypt.hashpw(a.getSenha(), BCrypt.gensalt()));
            ps.executeUpdate();
        }
    }

    public List<Administrador> findAllAdministradores() throws SQLException {
        String sql = "SELECT * FROM administrador ORDER BY nome";
        List<Administrador> lista = new ArrayList<>();
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                lista.add(montar(rs));
            }
        }
        return lista;
    }

    public Administrador findAdministradorById(int id) throws SQLException {
        String sql = "SELECT * FROM administrador WHERE id_administrador = ?";
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
    public void updateAdministrador(Administrador a) throws SQLException {
        boolean trocarSenha = a.getSenha() != null && !a.getSenha().trim().isEmpty();
        String sql = trocarSenha
                ? "UPDATE administrador SET nome = ?, email = ?, senha = ? WHERE id_administrador = ?"
                : "UPDATE administrador SET nome = ?, email = ? WHERE id_administrador = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            int i = 1;
            ps.setString(i++, a.getNome());
            ps.setString(i++, a.getEmail());
            if (trocarSenha) {
                ps.setString(i++, BCrypt.hashpw(a.getSenha(), BCrypt.gensalt()));
            }
            ps.setInt(i, a.getIdAdministrador());
            ps.executeUpdate();
        }
    }

    public void deleteAdministradorById(int id) throws SQLException {
        String sql = "DELETE FROM administrador WHERE id_administrador = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    public Administrador autenticar(String login, String senha) throws SQLException {
        if (login == null || senha == null || login.trim().isEmpty()) return null;

        String sql = "SELECT id_administrador, nome, email, senha FROM administrador WHERE email = ?";
        try (Connection con = ConnectionPoolConfig.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, login.trim());
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next() && BCrypt.checkpw(senha, rs.getString("senha"))) {
                    Administrador a = new Administrador();
                    a.setIdAdministrador(rs.getInt("id_administrador"));
                    a.setNome(rs.getString("nome"));
                    a.setEmail(rs.getString("email"));
                    return a;
                }
            }
        }
        return null;
    }

    private Administrador montar(ResultSet rs) throws SQLException {
        Administrador a = new Administrador();
        a.setIdAdministrador(rs.getInt("id_administrador"));
        a.setNome(rs.getString("nome"));
        a.setEmail(rs.getString("email"));
        return a;
    }
}