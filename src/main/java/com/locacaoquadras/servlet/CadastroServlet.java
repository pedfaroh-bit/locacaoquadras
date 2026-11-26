package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.UsuarioDAO;
import com.locacaoquadras.model.Usuario;

// Cadastro de um novo cliente (sempre cria CLIENTE; administradores só são criados dentro do painel)
@WebServlet("/cadastro")
public class CadastroServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletUtil.abrir(req, resp, "cliente/cadastro.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = new Usuario();
        u.setNome(ServletUtil.texto(req.getParameter("nome")));
        u.setEmail(ServletUtil.texto(req.getParameter("email")));
        u.setTelefone(ServletUtil.texto(req.getParameter("telefone")));
        String senha = req.getParameter("senha");
        String confirmar = req.getParameter("confirmarSenha");

        String erro = null;
        if (u.getNome().isEmpty() || u.getEmail().isEmpty()) {
            erro = "Informe nome e e-mail.";
        } else if (!u.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            erro = "Informe um e-mail válido.";
        } else if (senha == null || senha.length() < 6) {
            erro = "A senha precisa ter pelo menos 6 caracteres.";
        } else if (!senha.equals(confirmar)) {
            erro = "As senhas não conferem.";
        }

        if (erro == null) {
            u.setSenha(senha);
            try {
                new UsuarioDAO().createUsuario(u);
                ServletUtil.redirecionarComOk(req, resp, "/login", "Conta criada! Faça login para continuar.");
                return;
            } catch (SQLException e) {
                erro = ServletUtil.mensagemErro(e);
            }
        }

        req.setAttribute("erro", erro);
        req.setAttribute("usuarioForm", u); // devolve o que foi digitado (menos a senha)
        ServletUtil.abrir(req, resp, "cliente/cadastro.jsp");
    }
}
