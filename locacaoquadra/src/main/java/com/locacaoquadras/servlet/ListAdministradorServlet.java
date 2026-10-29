package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.AdministradorDAO;

// Lista os administradores. Com ?id=X, também carrega esse administrador no formulário para edição
@WebServlet("/administradores")
public class ListAdministradorServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        AdministradorDAO dao = new AdministradorDAO();
        try {
            String id = req.getParameter("id");
            if (!ServletUtil.vazio(id)) {
                req.setAttribute("administrador", dao.findAdministradorById(Integer.parseInt(id)));
            }
            req.setAttribute("administradores", dao.findAllAdministradores());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/administrador/dashboard.jsp").forward(req, resp);
    }
}
