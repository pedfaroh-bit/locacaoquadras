package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.BloqueioDAO;

@WebServlet("/delete-bloqueio")
public class DeleteBloqueioServlet extends HttpServlet {

    private static final String LISTA = "/bloqueios";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            new BloqueioDAO().deleteBloqueioById(Integer.parseInt(req.getParameter("id")));
            ServletUtil.redirecionar(req, resp, LISTA);
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
