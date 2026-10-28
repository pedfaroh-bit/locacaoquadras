package com.locacaoquadras.servlet;

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.model.Quadra;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/quadras")
public class QuadraServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final QuadraDAO dao = new QuadraDAO();
    private final ModalidadeDAO modalidadeDao = new ModalidadeDAO();

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
                    abrirFormulario(req, resp, null);
                    break;

                case "editar": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    Quadra quadra = dao.buscarPorId(id);
                    if (quadra == null) {
                        resp.sendRedirect(req.getContextPath() + "/quadras");
                        return;
                    }
                    abrirFormulario(req, resp, quadra);
                    break;
                }

                case "excluir":
                    try {
                        dao.excluir(Integer.parseInt(req.getParameter("id")));
                        resp.sendRedirect(req.getContextPath() + "/quadras");
                    } catch (SQLIntegrityConstraintViolationException e) {
                        mostrarLista(req, resp,
                                "Não é possível excluir: esta quadra possui horários cadastrados.");
                    }
                    break;

                default:
                    mostrarLista(req, resp, null);
                    break;
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro inválido");
        } catch (SQLException e) {
            throw new ServletException("Erro ao acessar quadras", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        try {
            String idTexto = req.getParameter("idQuadra");

            Quadra q = new Quadra();
            q.setNome(req.getParameter("nome"));
            q.setEndereco(req.getParameter("endereco"));
            q.setDescricao(req.getParameter("descricao"));
            q.setIdModalidade(Integer.parseInt(req.getParameter("idModalidade")));

            if (idTexto == null || idTexto.trim().isEmpty()) {
                dao.inserir(q);
            } else {
                q.setIdQuadra(Integer.parseInt(idTexto));
                dao.atualizar(q);
            }
            resp.sendRedirect(req.getContextPath() + "/quadras");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos");
        } catch (SQLException e) {
            throw new ServletException("Erro ao salvar quadra", e);
        }
    }

    private void abrirFormulario(HttpServletRequest req, HttpServletResponse resp, Quadra quadra)
            throws ServletException, IOException, SQLException {
        req.setAttribute("quadra", quadra);
        req.setAttribute("modalidades", modalidadeDao.listar());
        req.getRequestDispatcher("/quadra/form.jsp").forward(req, resp);
    }

    private void mostrarLista(HttpServletRequest req, HttpServletResponse resp, String erro)
            throws ServletException, IOException, SQLException {
        req.setAttribute("erro", erro);
        req.setAttribute("quadras", dao.listar());
        req.getRequestDispatcher("/quadra/lista.jsp").forward(req, resp);
    }
}