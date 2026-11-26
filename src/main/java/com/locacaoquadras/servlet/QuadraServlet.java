package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.model.Quadra;
import com.locacaoquadras.service.DisponibilidadeService;
import com.locacaoquadras.util.Formato;

// Detalhes da Quadra: dados, estrutura e horários livres da data escolhida
@WebServlet("/quadra")
public class QuadraServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = ServletUtil.inteiro(req.getParameter("id"), 0);
        LocalDate hoje = LocalDate.now();
        LocalDate data = ServletUtil.data(req.getParameter("data"));
        if (data == null || data.isBefore(hoje)) {
            data = hoje;
        }

        try {
            Quadra quadra = new QuadraDAO().findQuadraById(id);
            if (quadra == null || !quadra.isAtiva()) {
                ServletUtil.redirecionarComErro(req, resp, "/quadras", "Quadra não encontrada.");
                return;
            }
            req.setAttribute("quadra", quadra);
            req.setAttribute("horarios", new DisponibilidadeService().listar(id, data));
            req.setAttribute("preco", Formato.moeda(DisponibilidadeService.preco(quadra, data)));
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        req.setAttribute("data", data.toString());
        req.setAttribute("hoje", hoje.toString());
        ServletUtil.abrir(req, resp, "cliente/quadra-detalhe.jsp");
    }
}
