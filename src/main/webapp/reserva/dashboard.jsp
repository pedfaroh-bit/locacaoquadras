<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Reservas</title>
</head>
<body>
    <h1>Reservas</h1>

    <c:if test="${not empty param.erro}">
        <p style="color: red;"><c:out value="${param.erro}"/></p>
    </c:if>

    <h2>${empty reserva ? 'Nova reserva' : 'Editar reserva'}</h2>
    <form method="post" action="${pageContext.request.contextPath}/create-reserva">
        <input type="hidden" name="id" value="${reserva.idReserva}">
        <p>
            <label>Usuário:</label>
            <select name="idUsuario" required>
                <option value="">Selecione</option>
                <c:forEach var="u" items="${usuarios}">
                    <option value="${u.idUsuario}" ${u.idUsuario == reserva.idUsuario ? 'selected' : ''}>${u.nome}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            <label>Horário:</label>
            <select name="idHorario" required>
                <option value="">Selecione</option>
                <c:forEach var="h" items="${horarios}">
                    <option value="${h.idHorario}" ${h.idHorario == reserva.idHorario ? 'selected' : ''}>${h.descricao}</option>
                </c:forEach>
            </select>
        </p>
        <p>
            <label>Data:</label>
            <input type="date" name="dataReserva" value="${reserva.dataReserva}" required>
        </p>
        <c:if test="${not empty reserva}">
            <p>
                <label>Status:</label>
                <select name="status">
                    <option value="Ativa" ${reserva.status == 'Ativa' ? 'selected' : ''}>Ativa</option>
                    <option value="Cancelada" ${reserva.status == 'Cancelada' ? 'selected' : ''}>Cancelada</option>
                </select>
            </p>
            <p>
                <label>Valor devolvido:</label>
                <input type="number" name="valorDevolvido" value="${reserva.valorDevolvido}" min="0" step="0.01">
            </p>
        </c:if>
        <button type="submit">Salvar</button>
        <c:if test="${not empty reserva}">
            <a href="${pageContext.request.contextPath}/reservas">Cancelar</a>
        </c:if>
    </form>

    <h2>Lista</h2>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Data</th>
            <th>Quadra</th>
            <th>Horário</th>
            <th>Usuário</th>
            <th>Valor pago</th>
            <th>Status</th>
            <th>Valor devolvido</th>
            <th>Data cancelamento</th>
            <th>Ações</th>
        </tr>
        <c:forEach var="r" items="${reservas}">
            <tr>
                <td>${r.idReserva}</td>
                <td>${r.dataReservaFormatada}</td>
                <td>${r.nomeQuadra}</td>
                <td>${r.horaInicio} às ${r.horaFim}</td>
                <td>${r.nomeUsuario}</td>
                <td><fmt:formatNumber value="${r.valorPago}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td>${r.status}</td>
                <td><fmt:formatNumber value="${r.valorDevolvido}" minFractionDigits="2" maxFractionDigits="2"/></td>
                <td>${r.dataCancelamentoFormatada}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/reservas?id=${r.idReserva}">Editar</a>
                    <form method="post" action="${pageContext.request.contextPath}/delete-reserva" style="display: inline;">
                        <input type="hidden" name="id" value="${r.idReserva}">
                        <button type="submit" onclick="return confirm('Excluir esta reserva?');">Excluir</button>
                    </form>
                </td>
            </tr>
        </c:forEach>
    </table>
</body>
</html>
