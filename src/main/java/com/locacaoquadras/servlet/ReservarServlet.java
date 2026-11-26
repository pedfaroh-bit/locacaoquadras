package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.YearMonth;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.model.DiaCalendario;
import com.locacaoquadras.model.Quadra;
import com.locacaoquadras.service.DisponibilidadeService;
import com.locacaoquadras.service.ReservaService;
import com.locacaoquadras.util.Formato;
import com.locacaoquadras.util.Sessao;

// Realizar Reserva (só cliente logado - o filtro garante).
// GET  /reservar?id=QUADRA&data=AAAA-MM-DD&horario=ID  -> calendário + horários + total
// POST /reservar                                       -> confirma a reserva
@WebServlet("/reservar")
public class ReservarServlet extends HttpServlet {

    private static final String[] MESES = {"Janeiro", "Fevereiro", "Março", "Abril", "Maio", "Junho",
        "Julho", "Agosto", "Setembro", "Outubro", "Novembro", "Dezembro"};

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = ServletUtil.inteiro(req.getParameter("id"), 0);
        LocalDate hoje = LocalDate.now();
        LocalDate data = ServletUtil.data(req.getParameter("data"));
        if (data == null || data.isBefore(hoje)) {
            data = hoje;
        }

        // Mês exibido no calendário (padrão: o mês da data escolhida)
        YearMonth mes = YearMonth.from(data);
        String mesParam = req.getParameter("mes");
        if (!ServletUtil.vazio(mesParam)) {
            try {
                mes = YearMonth.parse(mesParam.trim());
            } catch (java.time.format.DateTimeParseException e) {
                // mantém o mês da data
            }
        }
        YearMonth primeiro = YearMonth.from(hoje);
        YearMonth ultimo = primeiro.plusMonths(6);
        if (mes.isBefore(primeiro)) {
            mes = primeiro;
        }
        if (mes.isAfter(ultimo)) {
            mes = ultimo;
        }

        try {
            Quadra quadra = new QuadraDAO().findQuadraById(id);
            if (quadra == null || !quadra.isAtiva()) {
                ServletUtil.redirecionarComErro(req, resp, "/quadras", "Quadra não encontrada.");
                return;
            }
            req.setAttribute("quadra", quadra);
            req.setAttribute("horarios", new DisponibilidadeService().listar(id, data));
            req.setAttribute("total", Formato.moeda(DisponibilidadeService.preco(quadra, data)));
        } catch (SQLException e) {
            throw new ServletException(e);
        }

        // Dias do mês (domingo é a primeira coluna)
        List<DiaCalendario> dias = new ArrayList<>();
        int vazios = mes.atDay(1).getDayOfWeek().getValue() % 7;
        for (int i = 0; i < vazios; i++) {
            dias.add(new DiaCalendario(null, true, false));
        }
        for (int d = 1; d <= mes.lengthOfMonth(); d++) {
            LocalDate dia = mes.atDay(d);
            dias.add(new DiaCalendario(dia, dia.isBefore(hoje), dia.equals(data)));
        }

        req.setAttribute("dias", dias);
        req.setAttribute("mesNome", MESES[mes.getMonthValue() - 1] + " " + mes.getYear());
        req.setAttribute("mesAnterior", mes.isAfter(primeiro) ? mes.minusMonths(1).toString() : "");
        req.setAttribute("mesProximo", mes.isBefore(ultimo) ? mes.plusMonths(1).toString() : "");
        req.setAttribute("data", data.toString());
        req.setAttribute("dataFormatada", data.format(java.time.format.DateTimeFormatter.ofPattern("dd/MM/yyyy")));
        req.setAttribute("horarioSel", ServletUtil.inteiro(req.getParameter("horario"), 0));
        ServletUtil.abrir(req, resp, "cliente/reservar.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int idQuadra = ServletUtil.inteiro(req.getParameter("id"), 0);
        int idHorario = ServletUtil.inteiro(req.getParameter("idHorario"), 0);
        LocalDate data = ServletUtil.data(req.getParameter("data"));
        String volta = "/reservar?id=" + idQuadra + (data == null ? "" : "&data=" + data);

        if (idHorario == 0) {
            ServletUtil.redirecionarComErro(req, resp, volta, "Escolha um horário.");
            return;
        }
        try {
            String erro = ReservaService.reservar(Sessao.usuario(req).getIdUsuario(), idHorario, data);
            if (erro != null) {
                ServletUtil.redirecionarComErro(req, resp, volta, erro);
                return;
            }
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, volta, ServletUtil.mensagemErro(e));
            return;
        }
        ServletUtil.redirecionarComOk(req, resp, "/minhas-reservas", "Reserva confirmada!");
    }
}
