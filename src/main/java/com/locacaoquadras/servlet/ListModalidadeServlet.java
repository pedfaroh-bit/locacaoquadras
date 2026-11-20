package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ModalidadeDAO;

@WebServlet({"/inicio", "/modalidades"})
public class ListModalidadeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ModalidadeDAO dao = new ModalidadeDAO();
        try {
            String id = req.getParameter("id");
            if (!ServletUtil.vazio(id)) {
                req.setAttribute("modalidade", dao.findModalidadeById(Integer.parseInt(id)));
            }
            req.setAttribute("modalidades", dao.findAllModalidades());
            req.setAttribute("homeCarregada", Boolean.TRUE);
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        String path = req.getServletPath();
        if ("/inicio".equals(path)) {
            req.getRequestDispatcher("/index.jsp").forward(req, resp);
        } else {
            req.getRequestDispatcher("/modalidade.jsp").forward(req, resp);
        }
    }
}