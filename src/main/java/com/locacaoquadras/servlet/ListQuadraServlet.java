package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.dao.QuadraDAO;
import com.locacaoquadras.model.Quadra;

@WebServlet({"/quadras", "/lista-quadras"})
public class ListQuadraServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        QuadraDAO dao = new QuadraDAO();
        try {
            String id = req.getParameter("id");
            if (!ServletUtil.vazio(id)) {
                req.setAttribute("quadra", dao.findQuadraById(Integer.parseInt(id)));
            }

            List<Quadra> quadras = dao.findAllQuadras();

            String modalidadeParam = req.getParameter("modalidade");
            if (!ServletUtil.vazio(modalidadeParam)) {
                int idMod = Integer.parseInt(modalidadeParam);
                quadras = quadras.stream()
                        .filter(q -> q.getIdModalidade() == idMod)
                        .collect(Collectors.toList());
            }

            String cidadeParam = req.getParameter("cidade");
            if (!ServletUtil.vazio(cidadeParam)) {
                String busca = cidadeParam.trim().toLowerCase();
                quadras = quadras.stream()
                        .filter(q -> (q.getEndereco() != null && q.getEndereco().toLowerCase().contains(busca))
                                || (q.getNome() != null && q.getNome().toLowerCase().contains(busca)))
                        .collect(Collectors.toList());
            }

            req.setAttribute("quadras", quadras);
            req.setAttribute("modalidades", new ModalidadeDAO().findAllModalidades());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        String destino = "/lista-quadras".equals(req.getServletPath()) ? "/lista-quadras.jsp" : "/quadra.jsp";
        req.getRequestDispatcher(destino).forward(req, resp);
    }
}