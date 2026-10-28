package com.locacaoquadras.servlet;

import com.locacaoquadras.dao.ModalidadeDAO;
import com.locacaoquadras.model.Modalidade;

<<<<<<< HEAD
import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
=======
>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/modalidade")   // AJUSTE: mantenha o mesmo caminho que você já usava
public class ModalidadeServlet extends HttpServlet {

<<<<<<< HEAD
    private final ModalidadeDAO dao = new ModalidadeDAO();
=======
    private ModalidadeDAO dao = new ModalidadeDAO();
>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)

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
<<<<<<< HEAD
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
=======
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
>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)
                    req.getRequestDispatcher("/modalidade/lista.jsp").forward(req, resp);
                    break;
                }
            }
<<<<<<< HEAD
        } catch (SQLException e) {
=======
        } catch (Exception e) {
>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)
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
<<<<<<< HEAD
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
=======
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
>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)
