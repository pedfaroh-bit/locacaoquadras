package com.locacaoquadras.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ReservaDAO;
import com.locacaoquadras.model.Reserva;
import com.locacaoquadras.service.ReservaService;
import com.locacaoquadras.util.Formato;
import com.locacaoquadras.util.Sessao;

// O cliente cancela uma reserva SUA que ainda não aconteceu
@WebServlet("/cancelar-reserva")
public class CancelarReservaServlet extends HttpServlet {

    private static final String LISTA = "/minhas-reservas";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ReservaDAO dao = new ReservaDAO();
            Reserva r = dao.findReservaById(ServletUtil.inteiro(req.getParameter("id"), 0));

            if (r == null || r.getIdUsuario() != Sessao.usuario(req).getIdUsuario()) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "Reserva não encontrada.");
                return;
            }
            if (!r.isAtiva() || r.isPassada()) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "Esta reserva não pode mais ser cancelada.");
                return;
            }
            BigDecimal devolvido = ReservaService.valorDevolucao(r);
            dao.cancelarReserva(r.getIdReserva(), devolvido);
            ServletUtil.redirecionarComOk(req, resp, LISTA,
                    "Reserva cancelada. Valor devolvido: " + Formato.moeda(devolvido) + ".");
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
