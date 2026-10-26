<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quadras</title>
</head>
<body>
    <h1>Quadras</h1>
    <a href="${pageContext.request.contextPath}/quadras?acao=novo">Nova quadra</a>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Endereço</th>
            <th>Descrição</th>
            <th>ID Modalidade</th>
            <th>Ações</th>
        </tr>
        <c:forEach items="${quadras}" var="q">
            <tr>
                <td>${q.idQuadra}</td>
                <td>${q.nome}</td>
                <td>${q.endereco}</td>
                <td>${q.descricao}</td>
                <td>${q.idModalidade}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/quadras?acao=editar&id=${q.idQuadra}">Editar</a>
                    <a href="${pageContext.request.contextPath}/quadras?acao=excluir&id=${q.idQuadra}"
                       onclick="return confirm('Excluir esta quadra?');">Excluir</a>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>