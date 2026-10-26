<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
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
</body>
</html>