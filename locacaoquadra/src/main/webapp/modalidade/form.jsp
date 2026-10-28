<<<<<<< HEAD
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.ModalidadeDAO" %>
<%
    // Captura os parâmetros enviados pelo formulário ou pela URL
    String idStr = request.getParameter("id");
    String nome = request.getParameter("nome");
    String acao = request.getParameter("acao");

    // Lógica para processar o salvamento quando o formulário é submetido
    if ("salvar".equals(acao) && nome != null && !nome.trim().isEmpty()) {
        ModalidadeDAO dao = new ModalidadeDAO();
        int id = (idStr != null && !idStr.trim().isEmpty()) ? Integer.parseInt(idStr) : 0;
        dao.salvar(id, nome);

        // Redireciona para a lista após guardar os dados
        response.sendRedirect("lista.jsp");
        return;
    }
%>
>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
<<<<<<< HEAD
    <title>Modalidade</title>
</head>
<body>
    <h1>${empty modalidade ? 'Nova modalidade' : 'Editar modalidade'}</h1>
    <form method="post" action="${pageContext.request.contextPath}/modalidades">
        <input type="hidden" name="id" value="${modalidade.idModalidade}">
        <p>
            <label>Nome:</label>
            <input type="text" name="nome" value="${modalidade.nome}" required>
        </p>
        <p>
            <label>Preço semana:</label>
            <input type="text" name="precoSemana" value="${modalidade.precoSemana}" required>
        </p>
        <p>
            <label>Preço fim de semana:</label>
            <input type="text" name="precoFimdesemana" value="${modalidade.precoFimdesemana}" required>
        </p>
        <button type="submit">Salvar</button>
        <a href="${pageContext.request.contextPath}/modalidades">Voltar</a>
    </form>
=======
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Cadastrar Modalidade - QuadraDas</title>
    <!-- Tailwind CSS para estilização rápida -->
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 flex items-center justify-center min-h-screen p-4">

    <div class="max-w-md w-full bg-white rounded-2xl shadow-md p-6 border border-gray-200">
        <!-- Título Dinâmico -->
        <h2 class="text-2xl font-bold text-slate-800 mb-6 border-b pb-3">
            <%= (idStr != null && !idStr.isEmpty()) ? "Editar Modalidade" : "Nova Modalidade" %>
        </h2>

        <!-- Formulário de Cadastro / Edição -->
        <form action="form.jsp" method="POST" class="space-y-4">
            <!-- Campos Ocultos para Ação e ID -->
            <input type="hidden" name="acao" value="salvar">
            <input type="hidden" name="id" value="<%= (idStr != null) ? idStr : "" %>">

            <!-- Campo de Texto para o Nome da Modalidade -->
            <div>
                <label for="nome" class="block text-sm font-semibold text-gray-700 mb-1">
                    Nome da Modalidade
                </label>
                <input
                    type="text"
                    id="nome"
                    name="nome"
                    placeholder="Ex: Futebol, Futsal, Beach Tennis"
                    value="<%= (nome != null) ? nome : "" %>"
                    required
                    class="w-full border border-gray-300 rounded-lg p-3 text-slate-800 focus:ring-2 focus:ring-blue-500 focus:outline-none transition"
                >
            </div>

            <!-- Botões de Ação -->
            <div class="flex gap-3 pt-2">
                <a href="lista.jsp" class="w-1/2 bg-gray-200 hover:bg-gray-300 text-slate-700 font-semibold py-3 rounded-lg text-center transition">
                    Cancelar
                </a>
                <button type="submit" class="w-1/2 bg-blue-600 hover:bg-blue-700 text-white font-semibold py-3 rounded-lg transition shadow-sm">
                    Salvar
                </button>
            </div>
        </form>
    </div>

>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)
</body>
</html>