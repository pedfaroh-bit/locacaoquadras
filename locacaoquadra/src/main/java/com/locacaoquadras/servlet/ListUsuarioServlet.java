package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.UsuarioDAO;

// Lista os usuários. Com ?id=X, também carrega esse usuário no formulário para edição
@WebServlet("/usuarios")
public class ListUsuarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UsuarioDAO dao = new UsuarioDAO();
        try {
            String id = req.getParameter("id");
            if (!ServletUtil.vazio(id)) {
                req.setAttribute("usuario", dao.findUsuarioById(Integer.parseInt(id)));
            }
            req.setAttribute("usuarios", dao.findAllUsuarios());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/usuario/dashboard.jsp").forward(req, resp);
    }
}
