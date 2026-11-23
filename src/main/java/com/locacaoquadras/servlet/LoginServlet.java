package com.locacaoquadras.servlet;

import com.locacaoquadras.dao.AdministradorDAO;
import com.locacaoquadras.dao.UsuarioDAO;
import com.locacaoquadras.model.Administrador;
import com.locacaoquadras.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.sql.SQLException;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        resp.sendRedirect(req.getContextPath() + "/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");
        String login = req.getParameter("login");
        String senha = req.getParameter("senha");

        Administrador adm;
        Usuario usuario = null;
        try {
            adm = new AdministradorDAO().autenticar(login, senha);
            if (adm == null) {
                usuario = new UsuarioDAO().autenticar(login, senha);
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao autenticar", e);
        }

        if (adm != null) {
            iniciarSessao(req, adm, "ADMIN");
            resp.sendRedirect(req.getContextPath() + "/administrador.jsp");
        } else if (usuario != null) {
            iniciarSessao(req, usuario, "USUARIO");
            resp.sendRedirect(req.getContextPath() + "/index.jsp");
        } else {
            req.setAttribute("erro", "Login ou senha inválidos");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
        }
    }

    private void iniciarSessao(HttpServletRequest req, Object pessoa, String papel) {
        HttpSession antiga = req.getSession(false);
        if (antiga != null) antiga.invalidate();

        HttpSession session = req.getSession(true);
        session.setAttribute("usuarioLogado", pessoa);
        session.setAttribute("papel", papel);
        session.setMaxInactiveInterval(30 * 60);
    }
}