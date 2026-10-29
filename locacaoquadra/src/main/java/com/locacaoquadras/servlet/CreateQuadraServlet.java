package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.model.Quadra;

// Cadastra (sem id) ou atualiza (com id) uma quadra
@WebServlet("/create-quadra")
public class CreateQuadraServlet extends HttpServlet {

    private static final String LISTA = "/quadras";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String id = req.getParameter("id");
        try {
            Quadra q = new Quadra();
            q.setNome(req.getParameter("nome"));
            q.setEndereco(req.getParameter("endereco"));
            q.setDescricao(req.getParameter("descricao"));
            q.setIdModalidade(Integer.parseInt(req.getParameter("idModalidade")));

            QuadraDAO dao = new QuadraDAO();
            if (ServletUtil.vazio(id)) {
                dao.createQuadra(q);
            } else {
                q.setIdQuadra(Integer.parseInt(id));
                dao.updateQuadra(q);
            }
            ServletUtil.redirecionar(req, resp, LISTA);
        } catch (NumberFormatException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, "Selecione a modalidade da quadra.");
        } catch (SQLException e) {
            ServletUtil.redirecionarComErro(req, resp, LISTA, ServletUtil.mensagemErro(e));
        }
    }
}
