package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ReservaDAO;
import com.locacaoquadras.model.Reserva;
import com.locacaoquadras.util.Sessao;

// Minhas Reservas: abas Futuras e Passadas (histórico)
@WebServlet("/minhas-reservas")
public class MinhasReservasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Reserva> futuras = new ArrayList<>();
        List<Reserva> passadas = new ArrayList<>();
        try {
            for (Reserva r : new ReservaDAO().findByUsuario(Sessao.usuario(req).getIdUsuario())) {
                (r.isPassada() ? passadas : futuras).add(r);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        Collections.reverse(futuras); // a mais próxima primeiro

        boolean verPassadas = "passadas".equals(req.getParameter("aba"));
        req.setAttribute("aba", verPassadas ? "passadas" : "futuras");
        req.setAttribute("reservas", verPassadas ? passadas : futuras);
        req.setAttribute("qtdFuturas", futuras.size());
        req.setAttribute("qtdPassadas", passadas.size());
        ServletUtil.abrir(req, resp, "cliente/minhas-reservas.jsp");
    }
}
