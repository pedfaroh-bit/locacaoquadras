package com.locacaoquadras.servlet;

import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

// Funções repetidas por todas as servlets
public final class ServletUtil {

    private static final String VIEWS = "/WEB-INF/views/";

    private ServletUtil() {
    }

    // Abre uma tela (JSP) que fica protegida dentro de /WEB-INF/views
    public static void abrir(HttpServletRequest req, HttpServletResponse resp, String view)
            throws ServletException, IOException {
        req.getRequestDispatcher(VIEWS + view).forward(req, resp);
    }

    public static void redirecionar(HttpServletRequest req, HttpServletResponse resp, String caminho)
            throws IOException {
        resp.sendRedirect(req.getContextPath() + caminho);
    }

    // Redireciona mostrando uma mensagem de erro no topo da página de destino
    public static void redirecionarComErro(HttpServletRequest req, HttpServletResponse resp, String caminho,
            String mensagem) throws IOException {
        redirecionarComParametro(req, resp, caminho, "erro", mensagem);
    }

    // Redireciona mostrando uma mensagem de sucesso no topo da página de destino
    public static void redirecionarComOk(HttpServletRequest req, HttpServletResponse resp, String caminho,
            String mensagem) throws IOException {
        redirecionarComParametro(req, resp, caminho, "ok", mensagem);
    }

    private static void redirecionarComParametro(HttpServletRequest req, HttpServletResponse resp, String caminho,
            String nome, String mensagem) throws IOException {
        String separador = caminho.contains("?") ? "&" : "?";
        resp.sendRedirect(req.getContextPath() + caminho + separador + nome + "="
                + URLEncoder.encode(mensagem, "UTF-8"));
    }

    // Traduz os erros mais comuns do banco para uma mensagem que o usuário entende
    public static String mensagemErro(SQLException e) {
        switch (e.getErrorCode()) {
            case 23505:
                return "Já existe um cadastro com esse e-mail.";
            case 23503:
                return "Este item está ligado a outros cadastros (reservas, horários...) e não pode ser excluído.";
            case 23506:
                return "Um dos itens selecionados não existe mais.";
            default:
                return "Erro no banco de dados: " + e.getMessage();
        }
    }

    public static boolean vazio(String texto) {
        return texto == null || texto.trim().isEmpty();
    }

    public static String texto(String valor) {
        return valor == null ? "" : valor.trim();
    }

    // Aceita "10,50" ou "10.50"
    public static BigDecimal decimal(String texto) {
        return new BigDecimal(texto.trim().replace(",", "."));
    }

    // Número inteiro vindo da URL; devolve o padrão se estiver vazio ou inválido
    public static int inteiro(String texto, int padrao) {
        try {
            return vazio(texto) ? padrao : Integer.parseInt(texto.trim());
        } catch (NumberFormatException e) {
            return padrao;
        }
    }

    // Data no formato yyyy-MM-dd; null se estiver vazia ou inválida
    public static LocalDate data(String texto) {
        try {
            return vazio(texto) ? null : LocalDate.parse(texto.trim());
        } catch (DateTimeParseException e) {
            return null;
        }
    }

    // Busca simples: o texto contém o termo, sem diferenciar maiúsculas
    public static boolean contem(String texto, String busca) {
        return texto != null && texto.toLowerCase().contains(busca.toLowerCase());
    }
}
