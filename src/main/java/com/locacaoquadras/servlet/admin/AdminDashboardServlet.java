package com.locacaoquadras.servlet.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.dao.ReservaDAO;
import com.locacaoquadras.dao.UsuarioDAO;
import com.locacaoquadras.model.Quadra;
import com.locacaoquadras.model.Reserva;
import com.locacaoquadras.servlet.ServletUtil;
import com.locacaoquadras.util.Formato;

// Dashboard: números gerais e as próximas reservas
@WebServlet({"/admin", "/admin/dashboard"})
public class AdminDashboardServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int quadrasAtivas = 0;
            for (Quadra q : new QuadraDAO().findAllQuadras()) {
                if (q.isAtiva()) {
                    quadrasAtivas++;
                }
            }

            LocalDate hoje = LocalDate.now();
            int reservasHoje = 0;
            int reservasFuturas = 0;
            BigDecimal receitaMes = BigDecimal.ZERO;
            List<Reserva> proximas = new ArrayList<>();

            for (Reserva r : new ReservaDAO().findAllReservas()) {
                if (!r.isAtiva()) {
                    continue;
                }
                if (r.getDataReserva().equals(hoje)) {
                    reservasHoje++;
                }
                if (!r.isPassada()) {
                    reservasFuturas++;
                    proximas.add(r);
                }
                if (r.getDataReserva().getYear() == hoje.getYear()
                        && r.getDataReserva().getMonth() == hoje.getMonth()) {
                    receitaMes = receitaMes.add(r.getValorPago());
                }
            }
            // findAllReservas vem da mais recente para a mais antiga; queremos as mais próximas primeiro
            java.util.Collections.reverse(proximas);
            if (proximas.size() > 5) {
                proximas = new ArrayList<>(proximas.subList(0, 5));
            }

            req.setAttribute("quadrasAtivas", quadrasAtivas);
            req.setAttribute("totalUsuarios", new UsuarioDAO().contarUsuarios());
            req.setAttribute("reservasHoje", reservasHoje);
            req.setAttribute("reservasFuturas", reservasFuturas);
            req.setAttribute("receitaMes", Formato.moeda(receitaMes));
            req.setAttribute("proximas", proximas);
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.setAttribute("menuAtivo", "dashboard");
        ServletUtil.abrir(req, resp, "admin/dashboard.jsp");
    }
}
