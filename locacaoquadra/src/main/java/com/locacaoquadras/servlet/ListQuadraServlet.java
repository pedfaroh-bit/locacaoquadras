package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.dao.QuadraDAO;

// Lista as quadras. Com ?id=X, também carrega essa quadra no formulário para edição
@WebServlet("/quadras")
public class ListQuadraServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QuadraDAO dao = new QuadraDAO();
        try {
            String id = req.getParameter("id");
            if (!ServletUtil.vazio(id)) {
                req.setAttribute("quadra", dao.findQuadraById(Integer.parseInt(id)));
            }
            req.setAttribute("quadras", dao.findAllQuadras());
            // para a lista de seleção do formulário
            req.setAttribute("modalidades", new ModalidadeDAO().findAllModalidades());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/quadra/dashboard.jsp").forward(req, resp);
    }
}
