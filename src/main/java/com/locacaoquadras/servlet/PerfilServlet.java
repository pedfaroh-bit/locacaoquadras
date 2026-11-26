package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.UsuarioDAO;
import com.locacaoquadras.model.Usuario;
import com.locacaoquadras.util.Sessao;

// Meu Perfil: o cliente edita seus próprios dados (e, se quiser, troca a senha)
@WebServlet("/perfil")
public class PerfilServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("perfil", new UsuarioDAO().findUsuarioById(Sessao.usuario(req).getIdUsuario()));
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        ServletUtil.abrir(req, resp, "cliente/perfil.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Usuario u = new Usuario();
        u.setIdUsuario(Sessao.usuario(req).getIdUsuario()); // sempre o usuário da sessão, nunca um id vindo do formulário
        u.setNome(ServletUtil.texto(req.getParameter("nome")));
        u.setEmail(ServletUtil.texto(req.getParameter("email")));
        u.setTelefone(ServletUtil.texto(req.getParameter("telefone")));
        String novaSenha = req.getParameter("novaSenha");
        String confirmar = req.getParameter("confirmarSenha");

        String erro = null;
        if (u.getNome().isEmpty() || u.getEmail().isEmpty()) {
            erro = "Informe nome e e-mail.";
        } else if (!u.getEmail().matches("^[^@\\s]+@[^@\\s]+\\.[^@\\s]+$")) {
            erro = "Informe um e-mail válido.";
        } else if (!ServletUtil.vazio(novaSenha)) {
            if (novaSenha.length() < 6) {
                erro = "A nova senha precisa ter pelo menos 6 caracteres.";
            } else if (!novaSenha.equals(confirmar)) {
                erro = "As senhas não conferem.";
            } else {
                u.setSenha(novaSenha);
            }
        }

        if (erro == null) {
            try {
                UsuarioDAO dao = new UsuarioDAO();
                dao.updateUsuario(u);
                // Atualiza o nome mostrado no topo do site
                Sessao.entrar(req, Sessao.USUARIO, dao.findUsuarioById(u.getIdUsuario()));
                ServletUtil.redirecionarComOk(req, resp, "/perfil", "Dados atualizados com sucesso.");
                return;
            } catch (SQLException e) {
                erro = ServletUtil.mensagemErro(e);
            }
        }

        req.setAttribute("erro", erro);
        req.setAttribute("perfil", u);
        ServletUtil.abrir(req, resp, "cliente/perfil.jsp");
    }
}
