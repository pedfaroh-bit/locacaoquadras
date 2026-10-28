package com.locacaoquadras.servlet;

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.model.Modalidade;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/modalidade")   // AJUSTE: mantenha o mesmo caminho que você já usava
public class ModalidadeServlet extends HttpServlet {

    private ModalidadeDAO dao = new ModalidadeDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        String acao = req.getParameter("acao");
        if (acao == null) {
            acao = "listar";
        }

        try {
            switch (acao) {
                case "novo": {
                    // AJUSTE: caminho do seu JSP de formulário
                    req.getRequestDispatcher("/modalidade/form.jsp").forward(req, resp);
                    break;
                }
                case "editar": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    Modalidade m = dao.buscarPorId(id);
                    if (m == null) {
                        resp.sendRedirect(req.getContextPath() + "/modalidade");
                        return;
                    }
                    req.setAttribute("modalidade", m);
                    req.getRequestDispatcher("/modalidade/form.jsp").forward(req, resp);
                    break;
                }
                case "excluir": {
                    int id = Integer.parseInt(req.getParameter("id"));
                    dao.excluir(id);
                    resp.sendRedirect(req.getContextPath() + "/modalidade");
                    break;
                }
                default: {
                    List<Modalidade> lista = dao.listar();
                    req.setAttribute("modalidades", lista);
                    // AJUSTE: caminho do seu JSP de listagem
                    req.getRequestDispatcher("/modalidade/lista.jsp").forward(req, resp);
                    break;
                }
            }
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        try {
            String idParam = req.getParameter("id");
            int id = (idParam == null || idParam.isEmpty()) ? 0 : Integer.parseInt(idParam);
            String nome = req.getParameter("nome");

            dao.salvar(id, nome);
            resp.sendRedirect(req.getContextPath() + "/modalidade");
        } catch (Exception e) {
            throw new ServletException(e);
        }
    }
}