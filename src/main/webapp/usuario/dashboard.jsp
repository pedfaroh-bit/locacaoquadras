<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Usuários</title>
</head>
<body>
    <h1>Usuários</h1>

    <c:if test="${not empty param.erro}">
        <p style="color: red;"><c:out value="${param.erro}"/></p>
    </c:if>

    <h2>${empty usuario ? 'Novo usuário' : 'Editar usuário'}</h2>
    <form method="post" action="${pageContext.request.contextPath}/create-usuario">
        <input type="hidden" name="id" value="${usuario.idUsuario}">
        <p>
            <label>Nome:</label>
            <input type="text" name="nome" value="${usuario.nome}" required>
        </p>
        <p>
            <label>E-mail:</label>
            <input type="email" name="email" value="${usuario.email}" required>
        </p>
        <p>
            <label>Telefone:</label>
            <input type="text" name="telefone" value="${usuario.telefone}">
        </p>
        <p>
            <label>Senha:</label>
            <input type="password" name="senha" ${empty usuario ? 'required' : ''}>
            <c:if test="${not empty usuario}">(deixe em branco para manter a senha atual)</c:if>
        </p>
        <button type="submit">Salvar</button>
        <c:if test="${not empty usuario}">
            <a href="${pageContext.request.contextPath}/usuarios">Cancelar</a>
        </c:if>
    </form>

    <h2>Lista</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>E-mail</th>
            <th>Telefone</th>
            <th>Ações</th>
        </tr>
        <c:forEach var="u" items="${usuarios}">
            <tr>
                <td>${u.idUsuario}</td>
                <td>${u.nome}</td>
                <td>${u.email}</td>
                <td>${u.telefone}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/usuarios?id=${u.idUsuario}">Editar</a>
                    <form method="post" action="${pageContext.request.contextPath}/delete-usuario" style="display: inline;">
                        <input type="hidden" name="id" value="${u.idUsuario}">
                        <button type="submit" onclick="return confirm('Excluir este usuário?');">Excluir</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
