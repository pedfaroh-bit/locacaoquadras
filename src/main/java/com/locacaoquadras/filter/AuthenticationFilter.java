package com.locacaoquadras.filter;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

@WebFilter("/*")
public class AuthenticationFilter implements Filter {

    // Acessíveis sem login
    private static final List<String> PUBLICAS = Arrays.asList(
            "/login", "/login.jsp", "/cadastro.jsp", "/logout",
            "/create-usuario",
            "/css/", "/index.jsp", "/imagens/",
            "/console", "/seed" // TEMPORÁRIO: remova depois de testar
    );

    // Só ADMIN acessa
    private static final List<String> SO_ADMIN = Arrays.asList(
            "/administrador.jsp", "/admin-reservas.jsp", "/admin-usuarios.jsp",
            "/bloqueio.jsp", "/horario.jsp", "/modalidade.jsp", "/quadra.jsp",
            "/create-administrador", "/delete-administrador", "/list-administrador",
            "/create-bloqueio", "/delete-bloqueio", "/list-bloqueio",
            "/create-horario", "/delete-horario",
            "/create-modalidade", "/delete-modalidade",
            "/create-quadra", "/delete-quadra",
            "/list-usuario", "/delete-usuario"
    );

    @Override
    public void init(FilterConfig config) {}

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = req.getRequestURI().substring(req.getContextPath().length()).toLowerCase();

        // Log para diagnóstico (pode apagar depois)
        System.out.println("[AuthFilter] " + req.getMethod() + " " + path);

        if (path.equals("/") || casa(PUBLICAS, path)) {
            chain.doFilter(request, response);
            return;
        }

        HttpSession session = req.getSession(false);
        boolean logado = session != null && session.getAttribute("usuarioLogado") != null;

        if (!logado) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }

        String papel = (String) session.getAttribute("papel");

        if (casa(SO_ADMIN, path) && !"ADMIN".equals(papel)) {
            resp.sendError(HttpServletResponse.SC_FORBIDDEN, "Acesso restrito a administradores");
            return;
        }

        chain.doFilter(request, response);
    }

    private boolean casa(List<String> lista, String path) {
        for (String p : lista) if (path.startsWith(p)) return true;
        return false;
    }

    @Override
    public void destroy() {}
}