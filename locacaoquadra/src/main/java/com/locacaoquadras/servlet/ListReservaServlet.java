package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.HorarioDAO;
import com.locacaoquadras.dao.ReservaDAO;
import com.locacaoquadras.dao.UsuarioDAO;

// Lista as reservas. Com ?id=X, também carrega essa reserva no formulário para edição
@WebServlet("/reservas")
public class ListReservaServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ReservaDAO dao = new ReservaDAO();
        try {
            String id = req.getParameter("id");
            if (!ServletUtil.vazio(id)) {
                req.setAttribute("reserva", dao.findReservaById(Integer.parseInt(id)));
            }
            req.setAttribute("reservas", dao.findAllReservas());
            // para as listas de seleção do formulário
            req.setAttribute("usuarios", new UsuarioDAO().findAllUsuarios());
            req.setAttribute("horarios", new HorarioDAO().findAllHorarios());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/reserva/dashboard.jsp").forward(req, resp);
    }
}
