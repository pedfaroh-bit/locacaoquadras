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
import com.locacaoquadras.model.Reserva;

// Cadastra (sem id) ou atualiza (com id) uma reserva.
// O valor é calculado pelo preço da modalidade (dia de semana ou fim de semana).
@WebServlet("/create-reserva")
public class CreateReservaServlet extends HttpServlet {

    private static final String LISTA = "/reservas";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        try {
            Reserva r = new Reserva();
            r.setIdUsuario(Integer.parseInt(req.getParameter("idUsuario")));
            r.setIdHorario(Integer.parseInt(req.getParameter("idHorario")));
            r.setDataReserva(LocalDate.parse(req.getParameter("dataReserva")));

            // Toda reserva nova começa como Ativa. Na edição, o status vem do formulário.
            String status = req.getParameter("status");
            r.setStatus(ServletUtil.vazio(id) || ServletUtil.vazio(status) ? "Ativa" : status);

            String valorDevolvido = req.getParameter("valorDevolvido");
            if ("Cancelada".equals(r.getStatus()) && !ServletUtil.vazio(valorDevolvido)) {
                r.setValorDevolvido(ServletUtil.decimal(valorDevolvido));
            }

            int idReserva = ServletUtil.vazio(id) ? 0 : Integer.parseInt(id);
            ReservaDAO dao = new ReservaDAO();

            if ("Ativa".equals(r.getStatus())) {
                if (new BloqueioDAO().existeBloqueio(r.getIdHorario(), r.getDataReserva())) {
                    ServletUtil.redirecionarComErro(req, resp, LISTA, "Este horário está bloqueado nessa data.");
                    return;
                }
                if (dao.existeReservaAtiva(r.getIdHorario(), r.getDataReserva(), idReserva)) {
                    ServletUtil.redirecionarComErro(req, resp, LISTA, "Este horário já está reservado nessa data.");
                    return;
                }
            }

            r.setValorPago(dao.calcularValor(r.getIdHorario(), r.getDataReserva()));

            if (idReserva == 0) {
                dao.createReserva(r);
            } else {
                r.setIdReserva(idReserva);
                dao.updateReserva(r);
            }
            ServletUtil.redirecionar(req, resp, LISTA);
        } catch (NumberFormatException | DateTimeParseException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, "Preencha usuário, horário e data corretamente.");
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
