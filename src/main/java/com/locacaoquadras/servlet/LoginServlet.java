package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.locacaoquadras.dao.UsuarioDAO;
import com.locacaoquadras.model.Usuario;
import com.locacaoquadras.util.Sessao;

// Login do CLIENTE do site. O administrador entra por outra porta: /admin/login
@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Sessao.usuario(req) != null) {
            ServletUtil.redirecionar(req, resp, "/inicio");
            return;
        }
        ServletUtil.abrir(req, resp, "cliente/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String login = ServletUtil.texto(req.getParameter("login"));
        String senha = req.getParameter("senha");

        Usuario usuario;
        try {
            usuario = new UsuarioDAO().autenticar(login, senha);
        } catch (SQLException e) {
            throw new ServletException("Erro ao autenticar", e);
        }

        if (usuario == null) {
            req.setAttribute("erro", "E-mail/telefone ou senha inválidos.");
            req.setAttribute("loginDigitado", login);
            ServletUtil.abrir(req, resp, "cliente/login.jsp");
            return;
        }
        if (!usuario.isAtivo()) {
            req.setAttribute("erro", "Sua conta está desativada. Fale com a administração.");
            req.setAttribute("loginDigitado", login);
            ServletUtil.abrir(req, resp, "cliente/login.jsp");
            return;
        }

        Sessao.entrar(req, Sessao.USUARIO, usuario);

        // Se o cliente foi mandado para o login por tentar abrir outra página, volta para ela
        HttpSession s = req.getSession(false);
        String voltar = (String) s.getAttribute(Sessao.VOLTAR_PARA);
        s.removeAttribute(Sessao.VOLTAR_PARA);
        resp.sendRedirect(Sessao.destinoSeguro(req, voltar, "/inicio"));
    }
}
