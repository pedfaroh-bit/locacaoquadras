package com.locacaoquadras.util;

import java.security.SecureRandom;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import com.locacaoquadras.model.Administrador;
import com.locacaoquadras.model.Usuario;

// Guarda quem está logado. Cliente e administrador usam atributos DIFERENTES da sessão,
// então um login de cliente nunca dá acesso ao painel, e vice-versa.
public final class Sessao {

    public static final String USUARIO = "usuarioLogado";
    public static final String ADMIN = "adminLogado";
    public static final String CSRF = "csrf";
    public static final String VOLTAR_PARA = "voltarPara";

    private static final SecureRandom ALEATORIO = new SecureRandom();

    private Sessao() {
    }

    public static Usuario usuario(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s == null ? null : (Usuario) s.getAttribute(USUARIO);
    }

    public static Administrador admin(HttpServletRequest req) {
        HttpSession s = req.getSession(false);
        return s == null ? null : (Administrador) s.getAttribute(ADMIN);
    }

    // Token anti-CSRF: todo formulário POST leva este valor no campo "_csrf"
    public static String token(HttpSession s) {
        String t = (String) s.getAttribute(CSRF);
        if (t == null) {
            byte[] b = new byte[24];
            ALEATORIO.nextBytes(b);
            StringBuilder sb = new StringBuilder();
            for (byte x : b) {
                sb.append(String.format("%02x", x));
            }
            t = sb.toString();
            s.setAttribute(CSRF, t);
        }
        return t;
    }

    // Faz o login de "chave" (USUARIO ou ADMIN). Cria uma sessão nova (evita fixação de sessão)
    // e preserva o login do outro papel, caso exista.
    public static void entrar(HttpServletRequest req, String chave, Object pessoa) {
        HttpSession antiga = req.getSession(false);
        Object usuario = null;
        Object admin = null;
        Object voltar = null;
        if (antiga != null) {
            usuario = antiga.getAttribute(USUARIO);
            admin = antiga.getAttribute(ADMIN);
            voltar = antiga.getAttribute(VOLTAR_PARA);
            antiga.invalidate();
        }
        HttpSession nova = req.getSession(true);
        if (usuario != null) {
            nova.setAttribute(USUARIO, usuario);
        }
        if (admin != null) {
            nova.setAttribute(ADMIN, admin);
        }
        if (voltar != null) {
            nova.setAttribute(VOLTAR_PARA, voltar);
        }
        nova.setAttribute(chave, pessoa);
        nova.setMaxInactiveInterval(30 * 60);
        token(nova);
    }

    // Sai de um papel. Se não sobrar nenhum login, encerra a sessão.
    public static void sair(HttpServletRequest req, String chave) {
        HttpSession s = req.getSession(false);
        if (s == null) {
            return;
        }
        s.removeAttribute(chave);
        if (s.getAttribute(USUARIO) == null && s.getAttribute(ADMIN) == null) {
            s.invalidate();
        }
    }

    // Só aceita voltar para um caminho DENTRO deste site (evita redirecionamento para sites externos)
    public static String destinoSeguro(HttpServletRequest req, String destino, String padrao) {
        String base = req.getContextPath() + "/";
        if (destino != null && destino.startsWith(base) && !destino.startsWith(base + "/")
                && destino.indexOf('\\') < 0 && destino.indexOf("://") < 0) {
            return destino;
        }
        return req.getContextPath() + padrao;
    }
}
