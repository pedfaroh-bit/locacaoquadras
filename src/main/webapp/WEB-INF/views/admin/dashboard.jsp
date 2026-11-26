<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="menuAtivo" value="dashboard"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Dashboard - QuadraDas Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-topo.jspf" %>
<div class="pagina">
  <h1>Dashboard</h1>
  <div class="migalha">QuadraDas / Dashboard</div>
  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>
    <section class="painel">
      <h2>Dashboard</h2>
      <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
      <div class="cards">
        <div class="stat destaque"><small>Reservas hoje</small><strong>${reservasHoje}</strong></div>
        <div class="stat"><small>Reservas futuras</small><strong>${reservasFuturas}</strong></div>
        <div class="stat"><small>Quadras ativas</small><strong>${quadrasAtivas}</strong></div>
        <div class="stat"><small>Clientes</small><strong>${totalUsuarios}</strong></div>
        <div class="stat"><small>Receita do mês</small><strong>${receitaMes}</strong></div>
      </div>

      <h2 style="margin:6px 0 4px">Próximas reservas</h2>
      <table>
        <thead><tr><th>Usuário</th><th>Quadra</th><th>Data/Hora</th><th>Valor</th><th>Ações</th></tr></thead>
        <tbody>
        <c:forEach var="r" items="${proximas}">
          <tr>
            <td><c:out value="${r.nomeUsuario}"/></td>
            <td><c:out value="${r.nomeQuadra}"/></td>
            <td>${r.dataHoraFormatada}</td>
            <td>${r.valorPagoFormatado}</td>
            <td><a class="acao" href="${ctx}/admin/reservas?ver=${r.idReserva}">Ver</a></td>
          </tr>
        </c:forEach>
        <c:if test="${empty proximas}"><tr><td class="vazio" colspan="5">Nenhuma reserva futura.</td></tr></c:if>
        </tbody>
      </table>
    </section>
  </div>
</div>
<div class="rodape"></div>
</body>
</html>
