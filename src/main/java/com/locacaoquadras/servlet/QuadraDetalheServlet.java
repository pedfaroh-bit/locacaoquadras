package com.locacaoquadras.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.dao.QuadraDAO;

// /quadra-detalhe?id=X -> Detalhes da Quadra
// /reservar?id=X       -> Realizar Reserva
@WebServlet({"/quadra-detalhe", "/reservar"})
public class QuadraDetalheServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String voltar = req.getContextPath() + "/lista-quadras";
        String id = req.getParameter("id");
        if (ServletUtil.vazio(id)) {
            resp.sendRedirect(voltar);
            return;
        }
        try {
            Object quadra = new QuadraDAO().findQuadraById(Integer.parseInt(id));
            if (quadra == null) {
                resp.sendRedirect(voltar);
                return;
            }
            req.setAttribute("quadra", quadra);
            req.setAttribute("modalidades", new ModalidadeDAO().findAllModalidades());
        } catch (NumberFormatException e) {
            resp.sendRedirect(voltar);
            return;
        } catch (Exception e) {
            throw new ServletException(e);
        }
        String destino = "/reservar".equals(req.getServletPath()) ? "/reservar.jsp" : "/quadra-detalhe.jsp";
        req.getRequestDispatcher(destino).forward(req, resp);
    }
}