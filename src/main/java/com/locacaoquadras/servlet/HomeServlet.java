package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ModalidadeDAO;

// Página inicial: busca rápida + categorias (as modalidades cadastradas)
@WebServlet("/inicio")
public class HomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("modalidades", new ModalidadeDAO().findAllModalidades());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        ServletUtil.abrir(req, resp, "cliente/inicio.jsp");
    }
}
