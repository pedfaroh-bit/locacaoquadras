package com.locacaoquadras.servlet.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeParseException;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.BloqueioDAO;
import com.locacaoquadras.dao.HorarioDAO;
import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.dao.ReservaDAO;
import com.locacaoquadras.model.Bloqueio;
import com.locacaoquadras.model.Horario;
import com.locacaoquadras.model.LinhaGrade;
import com.locacaoquadras.model.Quadra;
import com.locacaoquadras.model.Reserva;
import com.locacaoquadras.servlet.ServletUtil;

// Gerenciar Horários: grade da semana (Livre / Reservado / Bloqueado) de uma quadra
// + cadastro dos horários de funcionamento da quadra.
@WebServlet("/admin/horarios")
public class AdminHorariosServlet extends HttpServlet {

    private static final String[] DIAS = {"Seg", "Ter", "Qua", "Qui", "Sex", "Sáb", "Dom"};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            List<Quadra> quadras = new QuadraDAO().findAllQuadras();
            req.setAttribute("quadras", quadras);

            int idQuadra = ServletUtil.inteiro(req.getParameter("quadra"), 0);
            if (idQuadra == 0 && !quadras.isEmpty()) {
                idQuadra = quadras.get(0).getIdQuadra();
            }
            req.setAttribute("idQuadra", idQuadra);

            LocalDate ref = ServletUtil.data(req.getParameter("semana"));
            if (ref == null) {
                ref = LocalDate.now();
            }
            LocalDate segunda = ref.with(TemporalAdjusters.previousOrSame(DayOfWeek.MONDAY));
            LocalDate domingo = segunda.plusDays(6);
            req.setAttribute("semanaAnterior", segunda.minusWeeks(1).toString());
            req.setAttribute("semanaProxima", segunda.plusWeeks(1).toString());
            req.setAttribute("semana", segunda.toString());

            List<String> cabecalho = new ArrayList<>();
            for (int i = 0; i < 7; i++) {
                LocalDate d = segunda.plusDays(i);
                cabecalho.add(DIAS[i] + " " + String.format("%02d/%02d", d.getDayOfMonth(), d.getMonthValue()));
            }
            req.setAttribute("cabecalho", cabecalho);

            HorarioDAO horarioDAO = new HorarioDAO();
            if (idQuadra != 0) {
                Set<String> reservados = new HashSet<>();
                for (Reserva r : new ReservaDAO().findAtivasByQuadraEntre(idQuadra, segunda, domingo)) {
                    reservados.add(r.getIdHorario() + "|" + r.getDataReserva());
                }
                Set<String> bloqueados = new HashSet<>();
                for (Bloqueio b : new BloqueioDAO().findByQuadraEntre(idQuadra, segunda, domingo)) {
                    bloqueados.add(b.getIdHorario() + "|" + b.getDataBloqueio());
                }

                LocalDate hoje = LocalDate.now();
                List<LinhaGrade> linhas = new ArrayList<>();
                for (Horario h : horarioDAO.findByQuadra(idQuadra)) {
                    List<String> celulas = new ArrayList<>();
                    for (int i = 0; i < 7; i++) {
                        LocalDate d = segunda.plusDays(i);
                        String chave = h.getIdHorario() + "|" + d;
                        if (reservados.contains(chave)) {
                            celulas.add("Reservado");
                        } else if (bloqueados.contains(chave)) {
                            celulas.add("Bloqueado");
                        } else if (d.isBefore(hoje)) {
                            celulas.add("-");
                        } else {
                            celulas.add("Livre");
                        }
                    }
                    linhas.add(new LinhaGrade(h, celulas));
                }
                req.setAttribute("linhas", linhas);
            }

            if (req.getParameter("novo") != null) {
                Horario novo = new Horario();
                novo.setIdQuadra(idQuadra);
                req.setAttribute("item", novo);
            } else if (!ServletUtil.vazio(req.getParameter("editar"))) {
                Horario h = horarioDAO.findHorarioById(ServletUtil.inteiro(req.getParameter("editar"), 0));
                req.setAttribute("item", h);
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.setAttribute("menuAtivo", "horarios");
        ServletUtil.abrir(req, resp, "admin/horarios.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int idQuadra = ServletUtil.inteiro(req.getParameter("idQuadra"), 0);
        String lista = "/admin/horarios?quadra=" + idQuadra;
        HorarioDAO dao = new HorarioDAO();
        try {
            if ("excluir".equals(req.getParameter("acao"))) {
                dao.deleteHorarioById(ServletUtil.inteiro(req.getParameter("id"), 0));
                ServletUtil.redirecionarComOk(req, resp, lista, "Horário excluído.");
                return;
            }

            Horario h = new Horario();
            h.setIdHorario(ServletUtil.inteiro(req.getParameter("id"), 0));
            h.setIdQuadra(idQuadra);
            try {
                h.setHoraInicio(LocalTime.parse(req.getParameter("horaInicio")));
                h.setHoraFim(LocalTime.parse(req.getParameter("horaFim")));
            } catch (DateTimeParseException | NullPointerException e) {
                ServletUtil.redirecionarComErro(req, resp, lista, "Informe a hora de início e a hora de fim.");
                return;
            }
            if (idQuadra == 0) {
                ServletUtil.redirecionarComErro(req, resp, lista, "Selecione a quadra.");
                return;
            }
            if (!h.getHoraFim().isAfter(h.getHoraInicio())) {
                ServletUtil.redirecionarComErro(req, resp, lista, "A hora de fim precisa ser depois da hora de início.");
                return;
            }
            // Dois horários da mesma quadra não podem se sobrepor
            for (Horario outro : dao.findByQuadra(idQuadra)) {
                if (outro.getIdHorario() != h.getIdHorario()
                        && h.getHoraInicio().isBefore(outro.getHoraFim())
                        && outro.getHoraInicio().isBefore(h.getHoraFim())) {
                    ServletUtil.redirecionarComErro(req, resp, lista, "Esse horário se sobrepõe a "
                            + outro.getHoraInicio() + "–" + outro.getHoraFim() + ", que já existe nesta quadra.");
                    return;
                }
            }

            if (h.getIdHorario() == 0) {
                dao.createHorario(h);
            } else {
                dao.updateHorario(h);
            }
            ServletUtil.redirecionarComOk(req, resp, lista, "Horário salvo com sucesso.");
        } catch (SQLException e) {
            String msg = e.getErrorCode() == 23503
                    ? "Este horário já tem reservas ou bloqueios e não pode ser excluído."
                    : ServletUtil.mensagemErro(e);
            ServletUtil.redirecionarComErro(req, resp, lista, msg);
        }
    }
}
