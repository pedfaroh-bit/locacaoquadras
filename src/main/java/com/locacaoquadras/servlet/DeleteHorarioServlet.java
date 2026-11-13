package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.HorarioDAO;

@WebServlet("/delete-horario")
public class DeleteHorarioServlet extends HttpServlet {

    private static final String LISTA = "/horarios";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        try {
            new HorarioDAO().deleteHorarioById(Integer.parseInt(req.getParameter("id")));
            ServletUtil.redirecionar(req, resp, LISTA);
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
