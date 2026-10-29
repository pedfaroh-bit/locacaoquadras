<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Horários</title>
</head>
<body>
    <h1>Horários</h1>

    <c:if test="${not empty param.erro}">
        <p style="color: red;"><c:out value="${param.erro}"/></p>
    </c:if>

    <h2>${empty horario ? 'Novo horário' : 'Editar horário'}</h2>
    <form method="post" action="${pageContext.request.contextPath}/create-horario">
        <input type="hidden" name="id" value="${horario.idHorario}">
        <p>
            <label>Quadra:</label>
            <select name="idQuadra" required>
                <option value="">Selecione</option>
                <c:forEach var="q" items="${quadras}">
                    <option value="${q.idQuadra}" ${q.idQuadra == horario.idQuadra ? 'selected' : ''}>${q.nome}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            <label>Hora início:</label>
            <input type="time" name="horaInicio" value="${horario.horaInicio}" required>
        </p>
        <p>
            <label>Hora fim:</label>
            <input type="time" name="horaFim" value="${horario.horaFim}" required>
        </p>
        <button type="submit">Salvar</button>
        <c:if test="${not empty horario}">
            <a href="${pageContext.request.contextPath}/horarios">Cancelar</a>
        </c:if>
    </form>

    <h2>Lista</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Quadra</th>
            <th>Hora início</th>
            <th>Hora fim</th>
            <th>Ações</th>
        </tr>
        <c:forEach var="h" items="${horarios}">
            <tr>
                <td>${h.idHorario}</td>
                <td>${h.nomeQuadra}</td>
                <td>${h.horaInicio}</td>
                <td>${h.horaFim}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/horarios?id=${h.idHorario}">Editar</a>
                    <form method="post" action="${pageContext.request.contextPath}/delete-horario" style="display: inline;">
                        <input type="hidden" name="id" value="${h.idHorario}">
                        <button type="submit" onclick="return confirm('Excluir este horário?');">Excluir</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
