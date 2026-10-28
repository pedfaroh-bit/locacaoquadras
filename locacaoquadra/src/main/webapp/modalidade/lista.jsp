<<<<<<< HEAD
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
=======
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
>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
<<<<<<< HEAD
    <title>Modalidades</title>
</head>
<body>
    <h1>Modalidades</h1>
    <p><a href="${pageContext.request.contextPath}/modalidades?acao=novo">Nova modalidade</a></p>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Preço semana</th>
            <th>Preço fim de semana</th>
            <th>Ações</th>
        </tr>
        <c:forEach var="m" items="${lista}">
            <tr>
                <td>${m.idModalidade}</td>
                <td>${m.nome}</td>
                <td><fmt:formatNumber value="${m.precoSemana}" minFractionDigits="2" maximumFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${m.precoFimdesemana}" minFractionDigits="2" maximumFractionDigits="2"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/modalidades?acao=editar&id=${m.idModalidade}">Editar</a>
                    <a href="${pageContext.request.contextPath}/modalidades?acao=excluir&id=${m.idModalidade}" onclick="return confirm('Excluir esta modalidade?');">Excluir</a>
                </td>
            </tr>
        </c:forEach>
    </table>
=======
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
>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)
</body>
</html>