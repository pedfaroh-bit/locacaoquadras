package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.model.Quadra;
import com.locacaoquadras.service.DisponibilidadeService;

// Lista de Quadras: busca por nome/bairro/cidade, modalidade e (opcional) data e horário livres
@WebServlet("/quadras")
public class QuadrasServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String busca = ServletUtil.texto(req.getParameter("q"));
        int modalidade = ServletUtil.inteiro(req.getParameter("modalidade"), 0);
        LocalDate data = ServletUtil.data(req.getParameter("data"));
        String horarioTexto = ServletUtil.texto(req.getParameter("horario"));
        LocalTime horario = null;
        if (!horarioTexto.isEmpty()) {
            try {
                horario = LocalTime.parse(horarioTexto);
            } catch (DateTimeParseException e) {
                horarioTexto = "";
            }
        }

        try {
            DisponibilidadeService disponibilidade = new DisponibilidadeService();
            List<Quadra> resultado = new ArrayList<>();
            for (Quadra q : new QuadraDAO().findAllQuadras()) {
                if (!q.isAtiva()) {
                    continue;
                }
                if (modalidade > 0 && q.getIdModalidade() != modalidade) {
                    continue;
                }
                if (!busca.isEmpty() && !(ServletUtil.contem(q.getNome(), busca)
                        || ServletUtil.contem(q.getEndereco(), busca)
                        || ServletUtil.contem(q.getNomeModalidade(), busca))) {
                    continue;
                }
                if (data != null && !disponibilidade.temDisponivel(q.getIdQuadra(), data, horario)) {
                    continue;
                }
                resultado.add(q);
            }

            req.setAttribute("quadras", resultado);
            req.setAttribute("modalidades", new ModalidadeDAO().findAllModalidades());
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        req.setAttribute("busca", busca);
        req.setAttribute("modalidadeSel", modalidade);
        req.setAttribute("dataSel", data == null ? "" : data.toString());
        req.setAttribute("horarioSel", horarioTexto);
        req.setAttribute("hoje", LocalDate.now().toString());
        ServletUtil.abrir(req, resp, "cliente/quadras.jsp");
    }
}
