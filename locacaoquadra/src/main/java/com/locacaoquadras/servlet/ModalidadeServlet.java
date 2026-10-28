package com.locacaoquadras.servlet;

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.model.Modalidade;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/modalidades")
public class ModalidadeServlet extends HttpServlet {

    private static final long serialVersionUID = 1L;
    private final ModalidadeDAO dao = new ModalidadeDAO();

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
                    req.getRequestDispatcher("/modalidade/form.jsp").forward(req, resp);
                    break;

                case "editar": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    Modalidade m = dao.buscarPorId(id);
                    if (m == null) {
                        resp.sendRedirect(req.getContextPath() + "/modalidades");
                        return;
                    }
                    req.setAttribute("modalidade", m);
                    req.getRequestDispatcher("/modalidade/form.jsp").forward(req, resp);
                    break;
                }

                case "excluir":
                    try {
                        dao.excluir(Integer.parseInt(req.getParameter("id")));
                        resp.sendRedirect(req.getContextPath() + "/modalidades");
                    } catch (SQLIntegrityConstraintViolationException e) {
                        mostrarLista(req, resp,
                                "Não é possível excluir: existem quadras usando esta modalidade.");
                    }
                    break;

                default:
                    mostrarLista(req, resp, null);
                    break;
            }
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Parâmetro inválido");
        } catch (SQLException e) {
            throw new ServletException("Erro ao acessar modalidades", e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        try {
            String idTexto = req.getParameter("id");

            Modalidade m = new Modalidade();
            m.setNome(req.getParameter("nome"));
            m.setPrecoSemana(new BigDecimal(req.getParameter("precoSemana").trim().replace(",", ".")));
            m.setPrecoFimdesemana(new BigDecimal(req.getParameter("precoFimdesemana").trim().replace(",", ".")));

            if (idTexto == null || idTexto.trim().isEmpty()) {
                dao.inserir(m);
            } else {
                m.setIdModalidade(Integer.parseInt(idTexto));
                dao.atualizar(m);
            }
            resp.sendRedirect(req.getContextPath() + "/modalidades");

        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Dados inválidos");
        } catch (SQLException e) {
            throw new ServletException("Erro ao salvar modalidade", e);
        }
    }

    private void mostrarLista(HttpServletRequest req, HttpServletResponse resp, String erro)
            throws ServletException, IOException, SQLException {
        req.setAttribute("erro", erro);
        req.setAttribute("lista", dao.listar());
        req.getRequestDispatcher("/modalidade/lista.jsp").forward(req, resp);
    }
}