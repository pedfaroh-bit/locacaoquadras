package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.HorarioDAO;
import com.locacaoquadras.model.Horario;

// Cadastra (sem id) ou atualiza (com id) um horário de uma quadra
@WebServlet("/create-horario")
public class CreateHorarioServlet extends HttpServlet {

    private static final String LISTA = "/horarios";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        try {
            Horario h = new Horario();
            h.setHoraInicio(LocalTime.parse(req.getParameter("horaInicio")));
            h.setHoraFim(LocalTime.parse(req.getParameter("horaFim")));
            h.setIdQuadra(Integer.parseInt(req.getParameter("idQuadra")));

            if (!h.getHoraFim().isAfter(h.getHoraInicio())) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "A hora de fim precisa ser depois da hora de início.");
                return;
            }

            HorarioDAO dao = new HorarioDAO();
            if (ServletUtil.vazio(id)) {
                dao.createHorario(h);
            } else {
                h.setIdHorario(Integer.parseInt(id));
                dao.updateHorario(h);
            }
            ServletUtil.redirecionar(req, resp, LISTA);
        } catch (NumberFormatException | DateTimeParseException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, "Preencha a quadra e os horários corretamente.");
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
