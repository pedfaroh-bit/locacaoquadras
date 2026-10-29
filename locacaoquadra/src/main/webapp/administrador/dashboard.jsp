<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Administradores</title>
</head>
<body>
    <h1>Administradores</h1>

    <c:if test="${not empty param.erro}">
        <p style="color: red;"><c:out value="${param.erro}"/></p>
    </c:if>

    <h2>${empty administrador ? 'Novo administrador' : 'Editar administrador'}</h2>
    <form method="post" action="${pageContext.request.contextPath}/create-administrador">
        <input type="hidden" name="id" value="${administrador.idAdministrador}">
        <p>
            <label>Nome:</label>
            <input type="text" name="nome" value="${administrador.nome}" required>
        </p>
        <p>
            <label>E-mail:</label>
            <input type="email" name="email" value="${administrador.email}" required>
        </p>
        <p>
            <label>Senha:</label>
            <input type="password" name="senha" ${empty administrador ? 'required' : ''}>
            <c:if test="${not empty administrador}">(deixe em branco para manter a senha atual)</c:if>
        </p>
        <button type="submit">Salvar</button>
        <c:if test="${not empty administrador}">
            <a href="${pageContext.request.contextPath}/administradores">Cancelar</a>
        </c:if>
    </form>

    <h2>Lista</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>E-mail</th>
            <th>Ações</th>
        </tr>
        <c:forEach var="a" items="${administradores}">
            <tr>
                <td>${a.idAdministrador}</td>
                <td>${a.nome}</td>
                <td>${a.email}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/administradores?id=${a.idAdministrador}">Editar</a>
                    <form method="post" action="${pageContext.request.contextPath}/delete-administrador" style="display: inline;">
                        <input type="hidden" name="id" value="${a.idAdministrador}">
                        <button type="submit" onclick="return confirm('Excluir este administrador?');">Excluir</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
