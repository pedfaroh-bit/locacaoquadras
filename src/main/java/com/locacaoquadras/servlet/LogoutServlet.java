package com.locacaoquadras.servlet;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.util.Sessao;

// Sai da conta de cliente (o login de administrador, se existir, continua)
@WebServlet("/logout")
public class LogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Sessao.sair(req, Sessao.USUARIO);
        ServletUtil.redirecionar(req, resp, "/login");
    }
}
