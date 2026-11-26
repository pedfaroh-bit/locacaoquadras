package com.locacaoquadras.servlet.admin;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.model.Quadra;
import com.locacaoquadras.servlet.ServletUtil;

// Gerenciar Quadras: listar, pesquisar, adicionar, editar, ativar/desativar e excluir
@WebServlet("/admin/quadras")
public class AdminQuadrasServlet extends HttpServlet {

    private static final String LISTA = "/admin/quadras";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String busca = ServletUtil.texto(req.getParameter("q"));
        try {
            QuadraDAO dao = new QuadraDAO();
            List<Quadra> lista = new ArrayList<>();
            for (Quadra q : dao.findAllQuadras()) {
                if (busca.isEmpty() || ServletUtil.contem(q.getNome(), busca)
                        || ServletUtil.contem(q.getEndereco(), busca)
                        || ServletUtil.contem(q.getNomeModalidade(), busca)) {
                    lista.add(q);
                }
            }
            req.setAttribute("quadras", lista);
            req.setAttribute("modalidades", new ModalidadeDAO().findAllModalidades());

            if (req.getParameter("novo") != null) {
                Quadra nova = new Quadra();
                req.setAttribute("item", nova);
            } else if (!ServletUtil.vazio(req.getParameter("editar"))) {
                req.setAttribute("item", dao.findQuadraById(ServletUtil.inteiro(req.getParameter("editar"), 0)));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.setAttribute("busca", busca);
        req.setAttribute("menuAtivo", "quadras");
        ServletUtil.abrir(req, resp, "admin/quadras.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        QuadraDAO dao = new QuadraDAO();
        try {
            if ("excluir".equals(req.getParameter("acao"))) {
                dao.deleteQuadraById(ServletUtil.inteiro(req.getParameter("id"), 0));
                ServletUtil.redirecionarComOk(req, resp, LISTA, "Quadra excluída.");
                return;
            }

            Quadra q = new Quadra();
            q.setIdQuadra(ServletUtil.inteiro(req.getParameter("id"), 0));
            q.setNome(ServletUtil.texto(req.getParameter("nome")));
            q.setEndereco(ServletUtil.texto(req.getParameter("endereco")));
            q.setDescricao(ServletUtil.texto(req.getParameter("descricao")));
            q.setIdModalidade(ServletUtil.inteiro(req.getParameter("idModalidade"), 0));
            q.setAtiva(req.getParameter("ativa") != null);

            if (q.getNome().isEmpty() || q.getEndereco().isEmpty() || q.getIdModalidade() == 0) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "Informe nome, localização e modalidade da quadra.");
                return;
            }
            if (q.getIdQuadra() == 0) {
                dao.createQuadra(q);
            } else {
                dao.updateQuadra(q);
            }
            ServletUtil.redirecionarComOk(req, resp, LISTA, "Quadra salva com sucesso.");
        } catch (SQLException e) {
            String msg = e.getErrorCode() == 23503
                    ? "Esta quadra já tem horários ou reservas e não pode ser excluída. Desative-a em vez de excluir."
                    : ServletUtil.mensagemErro(e);
            ServletUtil.redirecionarComErro(req, resp, LISTA, msg);
        }
    }
}
