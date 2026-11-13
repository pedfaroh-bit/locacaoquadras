package com.locacaoquadras.servlet;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.locacaoquadras.dao.AdministradorDAO;
import com.locacaoquadras.dao.BloqueioDAO;
import com.locacaoquadras.dao.HorarioDAO;

// Lista os bloqueios. Com ?id=X, também carrega esse bloqueio no formulário para edição
@WebServlet("/bloqueios")
public class ListBloqueioServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        BloqueioDAO dao = new BloqueioDAO();
        try {
            String id = req.getParameter("id");
            if (!ServletUtil.vazio(id)) {
                req.setAttribute("bloqueio", dao.findBloqueioById(Integer.parseInt(id)));
            }
            req.setAttribute("bloqueios", dao.findAllBloqueios());
            // para as listas de seleção do formulário
            req.setAttribute("administradores", new AdministradorDAO().findAllAdministradores());
            req.setAttribute("horarios", new HorarioDAO().findAllHorarios());
        } catch (SQLException e) {
            throw new ServletException(e);
        }
        req.getRequestDispatcher("/bloqueio/dashboard.jsp").forward(req, resp);
    }
}
