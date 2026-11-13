package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.HorarioDAO;
import com.locacaoquadras.dao.QuadraDAO;

// Lista os horários. Com ?id=X, também carrega esse horário no formulário para edição
@WebServlet("/horarios")
public class ListHorarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HorarioDAO dao = new HorarioDAO();
        try {
            String id = req.getParameter("id");
            if (!ServletUtil.vazio(id)) {
                req.setAttribute("horario", dao.findHorarioById(Integer.parseInt(id)));
            }
            req.setAttribute("horarios", dao.findAllHorarios());
            // para a lista de seleção do formulário
            req.setAttribute("quadras", new QuadraDAO().findAllQuadras());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/horario/dashboard.jsp").forward(req, resp);
    }
}
