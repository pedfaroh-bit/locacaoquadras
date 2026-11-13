<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bloqueios</title>
</head>
<body>
    <h1>Bloqueios</h1>

    <c:if test="${not empty param.erro}">
        <p style="color: red;"><c:out value="${param.erro}"/></p>
    </c:if>

    <h2>${empty bloqueio ? 'Novo bloqueio' : 'Editar bloqueio'}</h2>
    <form method="post" action="${pageContext.request.contextPath}/create-bloqueio">
        <input type="hidden" name="id" value="${bloqueio.idBloqueio}">
        <p>
            <label>Administrador:</label>
            <select name="idAdministrador" required>
                <option value="">Selecione</option>
                <c:forEach var="a" items="${administradores}">
                    <option value="${a.idAdministrador}" ${a.idAdministrador == bloqueio.idAdministrador ? 'selected' : ''}>${a.nome}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            <label>Horário:</label>
            <select name="idHorario" required>
                <option value="">Selecione</option>
                <c:forEach var="h" items="${horarios}">
                    <option value="${h.idHorario}" ${h.idHorario == bloqueio.idHorario ? 'selected' : ''}>${h.descricao}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            <label>Data:</label>
            <input type="date" name="dataBloqueio" value="${bloqueio.dataBloqueio}" required>
        </p>
        <p>
            <label>Motivo:</label>
            <input type="text" name="motivo" value="${bloqueio.motivo}">
        </p>
        <button type="submit">Salvar</button>
        <c:if test="${not empty bloqueio}">
            <a href="${pageContext.request.contextPath}/bloqueios">Cancelar</a>
        </c:if>
    </form>

    <h2>Lista</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Data</th>
            <th>Quadra</th>
            <th>Horário</th>
            <th>Motivo</th>
            <th>Administrador</th>
            <th>Ações</th>
        </tr>
        <c:forEach var="b" items="${bloqueios}">
            <tr>
                <td>${b.idBloqueio}</td>
                <td>${b.dataBloqueioFormatada}</td>
                <td>${b.nomeQuadra}</td>
                <td>${b.horaInicio} às ${b.horaFim}</td>
                <td>${b.motivo}</td>
                <td>${b.nomeAdministrador}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/bloqueios?id=${b.idBloqueio}">Editar</a>
                    <form method="post" action="${pageContext.request.contextPath}/delete-bloqueio" style="display: inline;">
                        <input type="hidden" name="id" value="${b.idBloqueio}">
                        <button type="submit" onclick="return confirm('Excluir este bloqueio?');">Excluir</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
