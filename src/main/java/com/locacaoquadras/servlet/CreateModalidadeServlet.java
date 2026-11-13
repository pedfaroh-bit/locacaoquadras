package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.model.Modalidade;

// Cadastra (sem id) ou atualiza (com id) uma modalidade
@WebServlet("/create-modalidade")
public class CreateModalidadeServlet extends HttpServlet {

    private static final String LISTA = "/modalidades";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        try {
            Modalidade m = new Modalidade();
            m.setNome(req.getParameter("nome"));
            m.setPrecoSemana(ServletUtil.decimal(req.getParameter("precoSemana")));
            m.setPrecoFimdesemana(ServletUtil.decimal(req.getParameter("precoFimdesemana")));

            ModalidadeDAO dao = new ModalidadeDAO();
            if (ServletUtil.vazio(id)) {
                dao.createModalidade(m);
            } else {
                m.setIdModalidade(Integer.parseInt(id));
                dao.updateModalidade(m);
            }
            ServletUtil.redirecionar(req, resp, LISTA);
        } catch (NumberFormatException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, "Preço inválido. Use números, ex.: 80,00");
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
