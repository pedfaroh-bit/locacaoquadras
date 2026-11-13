<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modalidades</title>
</head>
<body>
    <h1>Modalidades</h1>

    <c:if test="${not empty param.erro}">
        <p style="color: red;"><c:out value="${param.erro}"/></p>
    </c:if>

    <h2>${empty modalidade ? 'Nova modalidade' : 'Editar modalidade'}</h2>
    <form method="post" action="${pageContext.request.contextPath}/create-modalidade">
        <input type="hidden" name="id" value="${modalidade.idModalidade}">
        <p>
            <label>Nome:</label>
            <input type="text" name="nome" value="${modalidade.nome}" required>
        </p>
        <p>
            <label>Preço semana:</label>
            <input type="number" name="precoSemana" value="${modalidade.precoSemana}" min="0" step="0.01" required>
        </p>
        <p>
            <label>Preço fim de semana:</label>
            <input type="number" name="precoFimdesemana" value="${modalidade.precoFimdesemana}" min="0" step="0.01" required>
        </p>
        <button type="submit">Salvar</button>
        <c:if test="${not empty modalidade}">
            <a href="${pageContext.request.contextPath}/modalidades">Cancelar</a>
        </c:if>
    </form>

    <h2>Lista</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Preço semana</th>
            <th>Preço fim de semana</th>
            <th>Ações</th>
        </tr>
        <c:forEach var="m" items="${modalidades}">
            <tr>
                <td>${m.idModalidade}</td>
                <td>${m.nome}</td>
                <td><fmt:formatNumber value="${m.precoSemana}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td><fmt:formatNumber value="${m.precoFimdesemana}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td>
                    <a href="${pageContext.request.contextPath}/modalidades?id=${m.idModalidade}">Editar</a>
                    <form method="post" action="${pageContext.request.contextPath}/delete-modalidade" style="display: inline;">
                        <input type="hidden" name="id" value="${m.idModalidade}">
                        <button type="submit" onclick="return confirm('Excluir esta modalidade?');">Excluir</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
