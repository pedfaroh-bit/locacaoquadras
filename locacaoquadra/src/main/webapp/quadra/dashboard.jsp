<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quadras</title>
</head>
<body>
    <h1>Quadras</h1>

    <c:if test="${not empty param.erro}">
        <p style="color: red;"><c:out value="${param.erro}"/></p>
    </c:if>

    <h2>${empty quadra ? 'Nova quadra' : 'Editar quadra'}</h2>
    <form method="post" action="${pageContext.request.contextPath}/create-quadra">
        <input type="hidden" name="id" value="${quadra.idQuadra}">
        <p>
            <label>Nome:</label>
            <input type="text" name="nome" value="${quadra.nome}" required>
        </p>
        <p>
            <label>Endereço:</label>
            <input type="text" name="endereco" value="${quadra.endereco}" required>
        </p>
        <p>
            <label>Descrição:</label>
            <textarea name="descricao">${quadra.descricao}</textarea>
        </p>
        <p>
            <label>Modalidade:</label>
            <select name="idModalidade" required>
                <option value="">Selecione</option>
                <c:forEach var="m" items="${modalidades}">
                    <option value="${m.idModalidade}" ${m.idModalidade == quadra.idModalidade ? 'selected' : ''}>${m.nome}</option>
                </c:forEach>
            </select>
        </p>
        <button type="submit">Salvar</button>
        <c:if test="${not empty quadra}">
            <a href="${pageContext.request.contextPath}/quadras">Cancelar</a>
        </c:if>
    </form>

    <h2>Lista</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Endereço</th>
            <th>Descrição</th>
            <th>Modalidade</th>
            <th>Ações</th>
        </tr>
        <c:forEach var="q" items="${quadras}">
            <tr>
                <td>${q.idQuadra}</td>
                <td>${q.nome}</td>
                <td>${q.endereco}</td>
                <td>${q.descricao}</td>
                <td>${q.nomeModalidade}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/quadras?id=${q.idQuadra}">Editar</a>
                    <form method="post" action="${pageContext.request.contextPath}/delete-quadra" style="display: inline;">
                        <input type="hidden" name="id" value="${q.idQuadra}">
                        <button type="submit" onclick="return confirm('Excluir esta quadra?');">Excluir</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
