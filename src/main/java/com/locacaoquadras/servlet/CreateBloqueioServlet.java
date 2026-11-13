package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.BloqueioDAO;
import com.locacaoquadras.dao.ReservaDAO;
import com.locacaoquadras.model.Bloqueio;

// Cadastra (sem id) ou atualiza (com id) um bloqueio de horário
@WebServlet("/create-bloqueio")
public class CreateBloqueioServlet extends HttpServlet {

    private static final String LISTA = "/bloqueios";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        try {
            Bloqueio b = new Bloqueio();
            b.setIdAdministrador(Integer.parseInt(req.getParameter("idAdministrador")));
            b.setIdHorario(Integer.parseInt(req.getParameter("idHorario")));
            b.setDataBloqueio(LocalDate.parse(req.getParameter("dataBloqueio")));
            b.setMotivo(req.getParameter("motivo"));

            if (new ReservaDAO().existeReservaAtiva(b.getIdHorario(), b.getDataBloqueio(), 0)) {
                ServletUtil.redirecionarComErro(req, resp, LISTA,
                    "Existe uma reserva ativa nesse horário e data. Cancele a reserva antes de bloquear.");
                return;
            }

            BloqueioDAO dao = new BloqueioDAO();
            if (ServletUtil.vazio(id)) {
                dao.createBloqueio(b);
            } else {
                b.setIdBloqueio(Integer.parseInt(id));
                dao.updateBloqueio(b);
            }
            ServletUtil.redirecionar(req, resp, LISTA);
        } catch (NumberFormatException | DateTimeParseException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, "Preencha administrador, horário e data corretamente.");
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
