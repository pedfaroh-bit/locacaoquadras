package com.locacaoquadras.config;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Enumeration;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

// Faz acentos (ç, ã, é...) funcionarem em formulários e na barra de endereço.
// Registrado no web.xml como PRIMEIRO filtro (precisa rodar antes de qualquer leitura de parâmetro).
public class EncodingFilter implements Filter {

    @Override
    public void init(FilterConfig config) {
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {
        req.setCharacterEncoding("UTF-8"); // corpo do POST
        resp.setCharacterEncoding("UTF-8");

        // Parâmetros na URL (?q=São): o Tomcat 7 lê como ISO-8859-1 por padrão. Aqui lemos como UTF-8.
        if (req instanceof HttpServletRequest && "GET".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            req = new QueryUtf8Request((HttpServletRequest) req);
        }
        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
    }

    private static class QueryUtf8Request extends HttpServletRequestWrapper {

        private final Map<String, String[]> parametros = new LinkedHashMap<String, String[]>();

        QueryUtf8Request(HttpServletRequest request) {
            super(request);
            String query = request.getQueryString();
            if (query == null || query.isEmpty()) {
                return;
            }
            Map<String, List<String>> temp = new LinkedHashMap<String, List<String>>();
            for (String par : query.split("&")) {
                if (par.isEmpty()) {
                    continue;
                }
                int igual = par.indexOf('=');
                String nome = decodificar(igual < 0 ? par : par.substring(0, igual));
                String valor = igual < 0 ? "" : decodificar(par.substring(igual + 1));
                List<String> valores = temp.get(nome);
                if (valores == null) {
                    valores = new ArrayList<String>();
                    temp.put(nome, valores);
                }
                valores.add(valor);
            }
            for (Map.Entry<String, List<String>> e : temp.entrySet()) {
                parametros.put(e.getKey(), e.getValue().toArray(new String[0]));
            }
        }

        private static String decodificar(String texto) {
            try {
                return URLDecoder.decode(texto, "UTF-8");
            } catch (UnsupportedEncodingException e) {
                return texto;
            } catch (IllegalArgumentException e) {
                return texto; // %-sequência inválida: usa o texto como veio
            }
        }

        @Override
        public String getParameter(String nome) {
            String[] v = parametros.get(nome);
            return v == null || v.length == 0 ? null : v[0];
        }

        @Override
        public String[] getParameterValues(String nome) {
            return parametros.get(nome);
        }

        @Override
        public Map<String, String[]> getParameterMap() {
            return Collections.unmodifiableMap(parametros);
        }

        @Override
        public Enumeration<String> getParameterNames() {
            return Collections.enumeration(parametros.keySet());
        }
    }
}
