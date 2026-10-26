package com.locacaoquadras.servlet;

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.model.Modalidade;
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/modalidades")
public class ModalidadeServlet extends HttpServlet {

    private final ModalidadeDAO dao = new ModalidadeDAO();

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
                case "editar":
                    int id = Integer.parseInt(req.getParameter("id"));
                    req.setAttribute("modalidade", dao.buscarPorId(id));
                    req.getRequestDispatcher("/modalidade/form.jsp").forward(req, resp);
                    break;
                case "excluir":
                    dao.excluir(Integer.parseInt(req.getParameter("id")));
                    resp.sendRedirect("modalidades");
                    break;
                default:
                    req.setAttribute("lista", dao.listar());
                    req.getRequestDispatcher("/modalidade/lista.jsp").forward(req, resp);
                    break;
            }
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }

    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String idTexto = req.getParameter("id");

        Modalidade m = new Modalidade();
        m.setNome(req.getParameter("nome"));
        m.setPrecoSemana(new BigDecimal(req.getParameter("precoSemana").replace(",", ".")));
        m.setPrecoFimdesemana(new BigDecimal(req.getParameter("precoFimdesemana").replace(",", ".")));

        try {
            if (idTexto == null || idTexto.isEmpty()) {
                dao.inserir(m);
            } else {
                m.setIdModalidade(Integer.parseInt(idTexto));
                dao.atualizar(m);
            }
            resp.sendRedirect("modalidades");
        } catch (SQLException e) {
            throw new ServletException(e);
        }
    }
}
