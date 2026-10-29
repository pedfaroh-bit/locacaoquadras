package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ModalidadeDAO;

// Lista as modalidades. Com ?id=X, também carrega essa modalidade no formulário para edição
@WebServlet("/modalidades")
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
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/modalidade/dashboard.jsp").forward(req, resp);
    }
}
