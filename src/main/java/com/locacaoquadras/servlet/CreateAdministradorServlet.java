package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.AdministradorDAO;
import com.locacaoquadras.model.Administrador;

// Cadastra (sem id) ou atualiza (com id) um administrador
@WebServlet("/create-administrador")
public class CreateAdministradorServlet extends HttpServlet {

    private static final String LISTA = "/administradores";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        Administrador a = new Administrador();
        a.setNome(req.getParameter("nome"));
        a.setEmail(req.getParameter("email"));
        a.setSenha(req.getParameter("senha"));

        try {
            AdministradorDAO dao = new AdministradorDAO();
            if (ServletUtil.vazio(id)) {
                if (ServletUtil.vazio(a.getSenha())) {
                    ServletUtil.redirecionarComErro(req, resp, LISTA, "Informe a senha do administrador.");
                    return;
                }
                dao.createAdministrador(a);
            } else {
                a.setIdAdministrador(Integer.parseInt(id));
                dao.updateAdministrador(a);
            }
            ServletUtil.redirecionar(req, resp, LISTA);
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
