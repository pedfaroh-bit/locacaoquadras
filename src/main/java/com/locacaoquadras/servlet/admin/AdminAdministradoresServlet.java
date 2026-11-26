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

import com.locacaoquadras.dao.AdministradorDAO;
import com.locacaoquadras.model.Administrador;
import com.locacaoquadras.servlet.ServletUtil;
import com.locacaoquadras.util.Sessao;

// Gerenciar Administradores (quem pode entrar no painel)
@WebServlet("/admin/administradores")
public class AdminAdministradoresServlet extends HttpServlet {

    private static final String LISTA = "/admin/administradores";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String busca = ServletUtil.texto(req.getParameter("q"));
        try {
            AdministradorDAO dao = new AdministradorDAO();
            List<Administrador> lista = new ArrayList<>();
            for (Administrador a : dao.findAllAdministradores()) {
                if (busca.isEmpty() || ServletUtil.contem(a.getNome(), busca) || ServletUtil.contem(a.getEmail(), busca)) {
                    lista.add(a);
                }
            }
            req.setAttribute("administradores", lista);

            if (req.getParameter("novo") != null) {
                req.setAttribute("item", new Administrador());
            } else if (!ServletUtil.vazio(req.getParameter("editar"))) {
                req.setAttribute("item",
                        dao.findAdministradorById(ServletUtil.inteiro(req.getParameter("editar"), 0)));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.setAttribute("busca", busca);
        req.setAttribute("menuAtivo", "administradores");
        ServletUtil.abrir(req, resp, "admin/administradores.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        AdministradorDAO dao = new AdministradorDAO();
        Administrador logado = Sessao.admin(req);
        try {
            if ("excluir".equals(req.getParameter("acao"))) {
                int id = ServletUtil.inteiro(req.getParameter("id"), 0);
                if (id == logado.getIdAdministrador()) {
                    ServletUtil.redirecionarComErro(req, resp, LISTA, "Você não pode excluir o administrador com o qual está logado.");
                    return;
                }
                if (dao.contarAdministradores() <= 1) {
                    ServletUtil.redirecionarComErro(req, resp, LISTA, "É preciso manter pelo menos um administrador.");
                    return;
                }
                dao.deleteAdministradorById(id);
                ServletUtil.redirecionarComOk(req, resp, LISTA, "Administrador excluído.");
                return;
            }

            Administrador a = new Administrador();
            a.setIdAdministrador(ServletUtil.inteiro(req.getParameter("id"), 0));
            a.setNome(ServletUtil.texto(req.getParameter("nome")));
            a.setEmail(ServletUtil.texto(req.getParameter("email")));
            a.setSenha(req.getParameter("senha"));

            if (a.getNome().isEmpty() || !a.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "Informe nome e um e-mail válido.");
                return;
            }
            boolean novo = a.getIdAdministrador() == 0;
            boolean trocaSenha = !ServletUtil.vazio(a.getSenha());
            if ((novo || trocaSenha) && (a.getSenha() == null || a.getSenha().length() < 6)) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "A senha precisa ter pelo menos 6 caracteres.");
                return;
            }

            if (novo) {
                dao.createAdministrador(a);
            } else {
                dao.updateAdministrador(a);
                if (a.getIdAdministrador() == logado.getIdAdministrador()) {
                    // mantém o nome exibido no topo atualizado
                    Sessao.entrar(req, Sessao.ADMIN, dao.findAdministradorById(a.getIdAdministrador()));
                }
            }
            ServletUtil.redirecionarComOk(req, resp, LISTA, "Administrador salvo com sucesso.");
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
