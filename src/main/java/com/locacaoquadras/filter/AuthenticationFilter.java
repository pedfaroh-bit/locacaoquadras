package com.locacaoquadras.filter;

import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.locacaoquadras.util.Sessao;

// Porteiro do site. Regras:
//  - /admin/**           -> só administrador logado (exceto /admin/login e /admin/logout)
//  - páginas do cliente  -> só usuário logado (reservar, minhas reservas, perfil)
//  - todo o resto        -> público
// Também confere o token anti-CSRF em todo POST.
// Registrado no web.xml DEPOIS do EncodingFilter.
public class AuthenticationFilter implements Filter {

    private static final List<String> ESTATICOS = Arrays.asList("/css/", "/js/", "/imagens/");

    // Páginas que exigem login de CLIENTE
    private static final List<String> EXIGEM_USUARIO = Arrays.asList(
            "/reservar", "/minhas-reservas", "/cancelar-reserva", "/perfil");

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {

        HttpServletRequest req = (HttpServletRequest) request;
        HttpServletResponse resp = (HttpServletResponse) response;

        String path = caminho(req);

        for (String prefixo : ESTATICOS) {
            if (path.startsWith(prefixo)) {
                chain.doFilter(request, response);
                return;
            }
        }

        HttpSession session = req.getSession(true);
        String token = Sessao.token(session);

        // O console do H2 (/console) é de terceiros: não envia o nosso token, mas só abre para administrador logado
        boolean console = path.equals("/console") || path.startsWith("/console/");

        if ("POST".equalsIgnoreCase(req.getMethod()) && !console) {
            String enviado = req.getParameter("_csrf");
            if (enviado == null || !enviado.equals(token)) {
                resp.sendError(HttpServletResponse.SC_FORBIDDEN,
                        "Sessão expirada ou formulário inválido. Volte à página anterior, recarregue e tente de novo.");
                return;
            }
        }

        boolean areaAdmin = path.equals("/admin") || path.startsWith("/admin/") || console;
        boolean loginAdmin = path.equals("/admin/login") || path.equals("/admin/logout");

        if (areaAdmin && !loginAdmin) {
            if (Sessao.admin(req) == null) {
                resp.sendRedirect(req.getContextPath() + "/admin/login");
                return;
            }
        } else if (!areaAdmin && EXIGEM_USUARIO.contains(path) && Sessao.usuario(req) == null) {
            if ("GET".equalsIgnoreCase(req.getMethod())) {
                String q = req.getQueryString();
                session.setAttribute(Sessao.VOLTAR_PARA,
                        req.getContextPath() + path + (q == null ? "" : "?" + q));
            }
            resp.sendRedirect(req.getContextPath() + "/login");
            return;
        }

        // Páginas dinâmicas nunca ficam no cache (senão o "voltar" do navegador mostraria dados antigos)
        resp.setHeader("Cache-Control", "no-store");
        chain.doFilter(request, response);
    }

    // Caminho já tratado pelo Tomcat (sem contexto, sem ;jsessionid, sem ../)
    private String caminho(HttpServletRequest req) {
        String p = req.getServletPath() == null ? "" : req.getServletPath();
        if (req.getPathInfo() != null) {
            p += req.getPathInfo();
        }
        p = p.toLowerCase();
        if (p.length() > 1 && p.endsWith("/")) {
            p = p.substring(0, p.length() - 1);
        }
        return p.isEmpty() ? "/" : p;
    }

    @Override
    public void destroy() {
    }
}
