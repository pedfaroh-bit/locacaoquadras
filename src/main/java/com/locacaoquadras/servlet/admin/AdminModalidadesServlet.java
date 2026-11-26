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

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.model.Modalidade;
import com.locacaoquadras.servlet.ServletUtil;
import com.locacaoquadras.util.Formato;

// Modalidades e preços (semana x fim de semana). O preço de cada quadra vem da sua modalidade.
@WebServlet("/admin/modalidades")
public class AdminModalidadesServlet extends HttpServlet {

    private static final String LISTA = "/admin/modalidades";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String busca = ServletUtil.texto(req.getParameter("q"));
        try {
            ModalidadeDAO dao = new ModalidadeDAO();
            List<Modalidade> lista = new ArrayList<>();
            for (Modalidade m : dao.findAllModalidades()) {
                if (busca.isEmpty() || ServletUtil.contem(m.getNome(), busca)) {
                    lista.add(m);
                }
            }
            req.setAttribute("modalidades", lista);

            if (req.getParameter("novo") != null) {
                req.setAttribute("item", new Modalidade());
            } else if (!ServletUtil.vazio(req.getParameter("editar"))) {
                Modalidade m = dao.findModalidadeById(ServletUtil.inteiro(req.getParameter("editar"), 0));
                req.setAttribute("item", m);
                if (m != null) {
                    req.setAttribute("precoSemanaTxt", m.getPrecoSemana().toPlainString());
                    req.setAttribute("precoFimTxt", m.getPrecoFimdesemana().toPlainString());
                }
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.setAttribute("busca", busca);
        req.setAttribute("menuAtivo", "modalidades");
        ServletUtil.abrir(req, resp, "admin/modalidades.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        ModalidadeDAO dao = new ModalidadeDAO();
        try {
            if ("excluir".equals(req.getParameter("acao"))) {
                dao.deleteModalidadeById(ServletUtil.inteiro(req.getParameter("id"), 0));
                ServletUtil.redirecionarComOk(req, resp, LISTA, "Modalidade excluída.");
                return;
            }

            Modalidade m = new Modalidade();
            m.setIdModalidade(ServletUtil.inteiro(req.getParameter("id"), 0));
            m.setNome(ServletUtil.texto(req.getParameter("nome")));
            try {
                m.setPrecoSemana(ServletUtil.decimal(req.getParameter("precoSemana")));
                m.setPrecoFimdesemana(ServletUtil.decimal(req.getParameter("precoFimdesemana")));
            } catch (NumberFormatException | NullPointerException e) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "Informe os preços em números (ex.: 120,00).");
                return;
            }
            if (m.getNome().isEmpty()) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "Informe o nome da modalidade.");
                return;
            }
            if (m.getPrecoSemana().signum() < 0 || m.getPrecoFimdesemana().signum() < 0) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "Os preços não podem ser negativos.");
                return;
            }
            if (m.getIdModalidade() == 0) {
                dao.createModalidade(m);
            } else {
                dao.updateModalidade(m);
            }
            ServletUtil.redirecionarComOk(req, resp, LISTA, "Modalidade salva (semana "
                    + Formato.moeda(m.getPrecoSemana()) + ", fim de semana " + Formato.moeda(m.getPrecoFimdesemana()) + ").");
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
