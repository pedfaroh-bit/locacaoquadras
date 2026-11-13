package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.UsuarioDAO;
import com.locacaoquadras.model.Usuario;

// Cadastra (sem id) ou atualiza (com id) um usuário
@WebServlet("/create-usuario")
public class CreateUsuarioServlet extends HttpServlet {

    private static final String LISTA = "/usuarios";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        Usuario u = new Usuario();
        u.setNome(req.getParameter("nome"));
        u.setEmail(req.getParameter("email"));
        u.setSenha(req.getParameter("senha"));
        u.setTelefone(req.getParameter("telefone"));

        try {
            UsuarioDAO dao = new UsuarioDAO();
            if (ServletUtil.vazio(id)) {
                if (ServletUtil.vazio(u.getSenha())) {
                    ServletUtil.redirecionarComErro(req, resp, LISTA, "Informe a senha do usuário.");
                    return;
                }
                dao.createUsuario(u);
            } else {
                u.setIdUsuario(Integer.parseInt(id));
                dao.updateUsuario(u);
            }
            ServletUtil.redirecionar(req, resp, LISTA);
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
