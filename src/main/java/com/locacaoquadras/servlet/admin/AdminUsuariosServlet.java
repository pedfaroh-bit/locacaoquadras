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

import com.locacaoquadras.dao.UsuarioDAO;
import com.locacaoquadras.model.Usuario;
import com.locacaoquadras.servlet.ServletUtil;

// Gerenciar Usuários (clientes do site)
@WebServlet("/admin/usuarios")
public class AdminUsuariosServlet extends HttpServlet {

    private static final String LISTA = "/admin/usuarios";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String busca = ServletUtil.texto(req.getParameter("q"));
        try {
            UsuarioDAO dao = new UsuarioDAO();
            List<Usuario> lista = new ArrayList<>();
            for (Usuario u : dao.findAllUsuarios()) {
                if (busca.isEmpty() || ServletUtil.contem(u.getNome(), busca)
                        || ServletUtil.contem(u.getEmail(), busca) || ServletUtil.contem(u.getTelefone(), busca)) {
                    lista.add(u);
                }
            }
            req.setAttribute("usuarios", lista);

            if (req.getParameter("novo") != null) {
                req.setAttribute("item", new Usuario());
            } else if (!ServletUtil.vazio(req.getParameter("editar"))) {
                req.setAttribute("item", dao.findUsuarioById(ServletUtil.inteiro(req.getParameter("editar"), 0)));
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.setAttribute("busca", busca);
        req.setAttribute("menuAtivo", "usuarios");
        ServletUtil.abrir(req, resp, "admin/usuarios.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        UsuarioDAO dao = new UsuarioDAO();
        try {
            if ("excluir".equals(req.getParameter("acao"))) {
                dao.deleteUsuarioById(ServletUtil.inteiro(req.getParameter("id"), 0));
                ServletUtil.redirecionarComOk(req, resp, LISTA, "Usuário excluído.");
                return;
            }

            Usuario u = new Usuario();
            u.setIdUsuario(ServletUtil.inteiro(req.getParameter("id"), 0));
            u.setNome(ServletUtil.texto(req.getParameter("nome")));
            u.setEmail(ServletUtil.texto(req.getParameter("email")));
            u.setTelefone(ServletUtil.texto(req.getParameter("telefone")));
            u.setSenha(req.getParameter("senha"));
            u.setAtivo(req.getParameter("ativo") != null);

            if (u.getNome().isEmpty() || !u.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "Informe nome e um e-mail válido.");
                return;
            }
            boolean novo = u.getIdUsuario() == 0;
            boolean trocaSenha = !ServletUtil.vazio(u.getSenha());
            if ((novo || trocaSenha) && (u.getSenha() == null || u.getSenha().length() < 6)) {
                ServletUtil.redirecionarComErro(req, resp, LISTA, "A senha precisa ter pelo menos 6 caracteres.");
                return;
            }

            if (novo) {
                dao.createUsuario(u);
            } else {
                dao.updateUsuario(u);
                dao.updateAtivo(u.getIdUsuario(), u.isAtivo());
            }
            ServletUtil.redirecionarComOk(req, resp, LISTA, "Usuário salvo com sucesso.");
        } catch (SQLException e) {
            String msg = e.getErrorCode() == 23503
                    ? "Este usuário tem reservas e não pode ser excluído. Desative-o em vez de excluir."
                    : ServletUtil.mensagemErro(e);
            ServletUtil.redirecionarComErro(req, resp, LISTA, msg);
        }
    }
}
