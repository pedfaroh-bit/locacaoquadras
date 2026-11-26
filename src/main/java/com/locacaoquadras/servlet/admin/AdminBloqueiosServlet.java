package com.locacaoquadras.servlet.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.BloqueioDAO;
import com.locacaoquadras.dao.HorarioDAO;
import com.locacaoquadras.dao.ReservaDAO;
import com.locacaoquadras.model.Bloqueio;
import com.locacaoquadras.servlet.ServletUtil;
import com.locacaoquadras.util.Sessao;

// Bloqueios: impede reservas de um horário em uma data (manutenção, evento, feriado...)
@WebServlet("/admin/bloqueios")
public class AdminBloqueiosServlet extends HttpServlet {

    private static final String LISTA = "/admin/bloqueios";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String busca = ServletUtil.texto(req.getParameter("q"));
        try {
            List<Bloqueio> lista = new ArrayList<>();
            for (Bloqueio b : new BloqueioDAO().findAllBloqueios()) {
                if (busca.isEmpty() || ServletUtil.contem(b.getNomeQuadra(), busca)
                        || ServletUtil.contem(b.getMotivo(), busca)
                        || ServletUtil.contem(b.getDataBloqueioFormatada(), busca)) {
                    lista.add(b);
                }
            }
            req.setAttribute("bloqueios", lista);

            if (req.getParameter("novo") != null) {
                req.setAttribute("horarios", new HorarioDAO().findAllHorarios());
                req.setAttribute("hoje", LocalDate.now().toString());
                req.setAttribute("novo", Boolean.TRUE);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.setAttribute("busca", busca);
        req.setAttribute("menuAtivo", "bloqueios");
        ServletUtil.abrir(req, resp, "admin/bloqueios.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BloqueioDAO dao = new BloqueioDAO();
        try {
            if ("excluir".equals(req.getParameter("acao"))) {
                dao.deleteBloqueioById(ServletUtil.inteiro(req.getParameter("id"), 0));
                ServletUtil.redirecionarComOk(req, resp, LISTA, "Bloqueio removido.");
                return;
            }

            int idHorario = ServletUtil.inteiro(req.getParameter("idHorario"), 0);
            LocalDate data = ServletUtil.data(req.getParameter("data"));
            if (idHorario == 0 || data == null) {
                ServletUtil.redirecionarComErro(req, resp, LISTA + "?novo=1", "Escolha o horário e a data.");
                return;
            }
            if (data.isBefore(LocalDate.now())) {
                ServletUtil.redirecionarComErro(req, resp, LISTA + "?novo=1", "A data do bloqueio não pode ser no passado.");
                return;
            }
            if (dao.existeBloqueio(idHorario, data)) {
                ServletUtil.redirecionarComErro(req, resp, LISTA + "?novo=1", "Esse horário já está bloqueado nessa data.");
                return;
            }
            if (new ReservaDAO().existeReservaAtiva(idHorario, data, 0)) {
                ServletUtil.redirecionarComErro(req, resp, LISTA + "?novo=1",
                        "Já existe uma reserva ativa nesse horário. Cancele a reserva antes de bloquear.");
                return;
            }

            Bloqueio b = new Bloqueio();
            b.setIdAdministrador(Sessao.admin(req).getIdAdministrador());
            b.setIdHorario(idHorario);
            b.setDataBloqueio(data);
            b.setMotivo(ServletUtil.texto(req.getParameter("motivo")));
            dao.createBloqueio(b);
            ServletUtil.redirecionarComOk(req, resp, LISTA, "Horário bloqueado.");
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
