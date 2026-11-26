package com.locacaoquadras.servlet.admin;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.AdministradorDAO;
import com.locacaoquadras.model.Administrador;
import com.locacaoquadras.servlet.ServletUtil;
import com.locacaoquadras.util.Sessao;

// Login da ÁREA ADMINISTRATIVA. Só aceita administradores (tabela "administrador"),
// nunca clientes. O cliente entra por /login.
@WebServlet("/admin/login")
public class AdminLoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (Sessao.admin(req) != null) {
            ServletUtil.redirecionar(req, resp, "/admin");
            return;
        }
        ServletUtil.abrir(req, resp, "admin/login.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String email = ServletUtil.texto(req.getParameter("email"));
        String senha = req.getParameter("senha");

        Administrador adm;
        try {
            adm = new AdministradorDAO().autenticar(email, senha);
        } catch (SQLException e) {
            throw new ServletException("Erro ao autenticar", e);
        }

        if (adm == null) {
            req.setAttribute("erro", "E-mail ou senha de administrador inválidos.");
            req.setAttribute("emailDigitado", email);
            ServletUtil.abrir(req, resp, "admin/login.jsp");
            return;
        }
        Sessao.entrar(req, Sessao.ADMIN, adm);
        ServletUtil.redirecionar(req, resp, "/admin");
    }
}
