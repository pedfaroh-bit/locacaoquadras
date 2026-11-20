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

@WebServlet("/horarios")
public class ListHorarioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HorarioDAO horarioDAO = new HorarioDAO();
        QuadraDAO quadraDAO = new QuadraDAO();
        try {
            String id = req.getParameter("id");
            if (!ServletUtil.vazio(id)) {
                req.setAttribute("horario", horarioDAO.findHorarioById(Integer.parseInt(id)));
            }
            req.setAttribute("horarios", horarioDAO.findAllHorarios());
            req.setAttribute("quadras", quadraDAO.findAllQuadras());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/horario.jsp").forward(req, resp);
    }
}