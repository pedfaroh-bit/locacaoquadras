package com.locacaoquadras.servlet.admin;

import java.io.IOException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.servlet.ServletUtil;
import com.locacaoquadras.util.Sessao;

// Sai do painel (o login de cliente, se existir, continua)
@WebServlet("/admin/logout")
public class AdminLogoutServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Sessao.sair(req, Sessao.ADMIN);
        ServletUtil.redirecionar(req, resp, "/admin/login");
    }
}
