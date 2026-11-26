package com.locacaoquadras.servlet.admin;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.HorarioDAO;
import com.locacaoquadras.dao.ReservaDAO;
import com.locacaoquadras.dao.UsuarioDAO;
import com.locacaoquadras.model.Reserva;
import com.locacaoquadras.model.Usuario;
import com.locacaoquadras.service.ReservaService;
import com.locacaoquadras.servlet.ServletUtil;
import com.locacaoquadras.util.Formato;

// Gerenciar Reservas: ver todas, criar para um cliente, cancelar (com devolução) e excluir
@WebServlet("/admin/reservas")
public class AdminReservasServlet extends HttpServlet {

    private static final String LISTA = "/admin/reservas";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String busca = ServletUtil.texto(req.getParameter("q"));
        try {
            ReservaDAO dao = new ReservaDAO();
            List<Reserva> lista = new ArrayList<>();
            for (Reserva r : dao.findAllReservas()) {
                if (busca.isEmpty() || ServletUtil.contem(r.getNomeUsuario(), busca)
                        || ServletUtil.contem(r.getNomeQuadra(), busca)
                        || ServletUtil.contem(r.getStatusExibicao(), busca)
                        || ServletUtil.contem(r.getDataReservaFormatada(), busca)) {
                    lista.add(r);
                }
            }
            req.setAttribute("reservas", lista);

            if (req.getParameter("novo") != null) {
                List<Usuario> ativos = new ArrayList<>();
                for (Usuario u : new UsuarioDAO().findAllUsuarios()) {
                    if (u.isAtivo()) {
                        ativos.add(u);
                    }
                }
                req.setAttribute("usuarios", ativos);
                req.setAttribute("horarios", new HorarioDAO().findAllHorarios());
                req.setAttribute("hoje", java.time.LocalDate.now().toString());
                req.setAttribute("novo", Boolean.TRUE);
            } else if (!ServletUtil.vazio(req.getParameter("ver"))) {
                req.setAttribute("detalhe", dao.findReservaById(ServletUtil.inteiro(req.getParameter("ver"), 0)));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.setAttribute("busca", busca);
        req.setAttribute("menuAtivo", "reservas");
        ServletUtil.abrir(req, resp, "admin/reservas.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String acao = ServletUtil.texto(req.getParameter("acao"));
        ReservaDAO dao = new ReservaDAO();
        try {
            if ("criar".equals(acao)) {
                String erro = ReservaService.reservar(
                        ServletUtil.inteiro(req.getParameter("idUsuario"), 0),
                        ServletUtil.inteiro(req.getParameter("idHorario"), 0),
                        ServletUtil.data(req.getParameter("data")));
                if (erro != null) {
                    ServletUtil.redirecionarComErro(req, resp, LISTA + "?novo=1", erro);
                } else {
                    ServletUtil.redirecionarComOk(req, resp, LISTA, "Reserva criada.");
                }
                return;
            }

            int id = ServletUtil.inteiro(req.getParameter("id"), 0);
            Reserva r = dao.findReservaById(id);
            if (r == null) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "Reserva não encontrada.");
                return;
            }

            if ("cancelar".equals(acao)) {
                BigDecimal devolvido = r.getValorPago(); // padrão: devolve tudo
                String digitado = req.getParameter("valorDevolvido");
                if (!ServletUtil.vazio(digitado)) {
                    try {
                        devolvido = ServletUtil.decimal(digitado);
                    } catch (NumberFormatException e) {
                        ServletUtil.redirecionarComErro(req, resp, LISTA + "?ver=" + id, "Valor devolvido inválido.");
                        return;
                    }
                }
                if (devolvido.signum() < 0 || devolvido.compareTo(r.getValorPago()) > 0) {
                    ServletUtil.redirecionarComErro(req, resp, LISTA + "?ver=" + id,
                            "O valor devolvido deve ficar entre R$ 0,00 e " + Formato.moeda(r.getValorPago()) + ".");
                    return;
                }
                if (dao.cancelarReserva(id, devolvido)) {
                    ServletUtil.redirecionarComOk(req, resp, LISTA, "Reserva cancelada. Devolvido: " + Formato.moeda(devolvido) + ".");
                } else {
                    ServletUtil.redirecionarComErro(req, resp, LISTA, "Esta reserva já estava cancelada.");
                }
            } else if ("excluir".equals(acao)) {
                dao.deleteReservaById(id);
                ServletUtil.redirecionarComOk(req, resp, LISTA, "Reserva excluída.");
            } else {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "Ação inválida.");
            }
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
