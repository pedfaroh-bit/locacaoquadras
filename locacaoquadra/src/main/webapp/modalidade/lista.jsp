<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ page import="dao.ModalidadeDAO, java.util.List" %>
<%
    ModalidadeDAO dao = new ModalidadeDAO();
    String acao = request.getParameter("acao");
    String idDel = request.getParameter("id");

    if ("deletar".equals(acao) && idDel != null) {
        dao.deletar(Integer.parseInt(idDel));
        response.sendRedirect("lista.jsp");
        return;
    }

    List<String[]> lista = dao.listar();
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <title>Lista de Modalidades</title>
    <script src="https://cdn.tailwindcss.com"></script>
</head>
<body class="bg-gray-100 p-8">
    <div class="max-w-2xl mx-auto bg-white p-6 rounded-xl shadow">
        <div class="flex justify-between items-center mb-6">
            <h2 class="text-2xl font-bold">Modalidades Cadastradas</h2>
            <a href="form.jsp" class="bg-blue-600 text-white px-4 py-2 rounded font-semibold">+ Nova Modalidade</a>
        </div>
        <table class="w-full text-left border-collapse">
            <thead>
                <tr class="border-b">
                    <th class="p-2">ID</th>
                    <th class="p-2">Nome</th>
                    <th class="p-2">Ações</th>
                </tr>
            </thead>
            <tbody>
                <% for (String[] item : lista) { %>
                <tr class="border-b hover:bg-gray-50">
                    <td class="p-2"><%= item[0] %></td>
                    <td class="p-2 font-medium"><%= item[1] %></td>
                    <td class="p-2 space-x-2">
                        <a href="form.jsp?id=<%= item[0] %>&nome=<%= item[1] %>" class="text-blue-600">Editar</a>
                        <a href="lista.jsp?acao=deletar&id=<%= item[0] %>" onclick="return confirm('Excluir?')" class="text-red-600">Excluir</a>
                    </td>
                </tr>
                <% } %>
            </tbody>
        </table>
    </div>
</body>
</html>