package com.locacaoquadras.servlet;

import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.model.Quadra;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quadras")
public class QuadraServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final QuadraDAO dao = new QuadraDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "listar";
        }

        try {
            switch (acao) {
                case "novo":
                    req.getRequestDispatcher("/quadra/form.jsp").forward(req, resp);
                    break;

                case "editar":
                    int idEditar = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("quadra", dao.buscarPorId(idEditar));
                    req.getRequestDispatcher("/quadra/form.jsp").forward(req, resp);
                    break;

                case "excluir":
                    int idExcluir = Integer.parseInt(req.getParameter("id"));
                    dao.excluir(idExcluir);
                    resp.sendRedirect(req.getContextPath() + "/quadras");
                    break;

                default:
                    req.setAttribute("quadras", dao.listar());
                    req.getRequestDispatcher("/quadra/lista.jsp").forward(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException("Erro ao acessar quadras", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String idTexto = req.getParameter("idQuadra");
        Quadra q = new Quadra();
        q.setNome(req.getParameter("nome"));
        q.setEndereco(req.getParameter("endereco"));
        q.setDescricao(req.getParameter("descricao"));
        q.setIdModalidade(Integer.parseInt(req.getParameter("idModalidade")));

        try {
            if (idTexto == null || idTexto.trim().isEmpty()) {
                dao.inserir(q);
            } else {
                q.setIdQuadra(Integer.parseInt(idTexto));
                dao.atualizar(q);
            }
            resp.sendRedirect(req.getContextPath() + "/quadras");
        } catch (SQLException e) {
            throw new ServletException("Erro ao salvar quadra", e);
        }
    }
}