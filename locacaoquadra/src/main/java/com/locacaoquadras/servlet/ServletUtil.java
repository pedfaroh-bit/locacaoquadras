package com.locacaoquadras.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.sql.SQLException;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Funções repetidas por todas as servlets
public final class ServletUtil {

    private ServletUtil() {
    }

    public static void redirecionar(HttpServletRequest req, HttpServletResponse resp, String caminho)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + caminho);
    }

    // Volta para a listagem mostrando a mensagem de erro no topo da página
    public static void redirecionarComErro(HttpServletRequest req, HttpServletResponse resp, String caminho,
            String mensagem) throws IOException {
        resp.sendRedirect(req.getContextPath() + caminho + "?erro="
            + URLEncoder.encode(mensagem, StandardCharsets.UTF_8));
    }

    // Traduz os erros mais comuns do banco para uma mensagem que o usuário entende
    public static String mensagemErro(SQLException e) {
        switch (e.getErrorCode()) {
            case 23505:
                return "Já existe um cadastro com esse e-mail.";
            case 23503:
                return "Este item está ligado a outros cadastros e não pode ser excluído.";
            case 23506:
                return "Um dos itens selecionados não existe mais.";
            default:
                return "Erro no banco de dados: " + e.getMessage();
        }
    }

    public static boolean vazio(String texto) {
        return texto == null || texto.isBlank();
    }

    // Aceita "10,50" ou "10.50"
    public static BigDecimal decimal(String texto) {
        return new BigDecimal(texto.trim().replace(",", "."));
    }
}
