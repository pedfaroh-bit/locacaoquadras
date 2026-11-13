package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ModalidadeDAO;

@WebServlet("/delete-modalidade")
public class DeleteModalidadeServlet extends HttpServlet {

    private static final String LISTA = "/modalidades";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            new ModalidadeDAO().deleteModalidadeById(Integer.parseInt(req.getParameter("id")));
            ServletUtil.redirecionar(req, resp, LISTA);
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
