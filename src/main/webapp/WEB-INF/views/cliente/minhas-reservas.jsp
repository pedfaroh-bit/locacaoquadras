<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="menuAtivo" value="reservas"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Minhas Reservas - QuadraDas</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-topo.jspf" %>

<div class="pagina">
  <h1>Minhas Reservas</h1>
  <div class="migalha">QuadraDas / Minhas Reservas</div>
  <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>

  <div class="card abas">
    <a class="${aba == 'futuras' ? 'ativo' : ''}" href="${ctx}/minhas-reservas?aba=futuras">Futuras (${qtdFuturas})</a>
    <a class="${aba == 'passadas' ? 'ativo' : ''}" href="${ctx}/minhas-reservas?aba=passadas">Passadas (${qtdPassadas})</a>
  </div>

  <c:forEach var="r" items="${reservas}">
    <div class="card res-linha">
      <div class="thumb"></div>
      <div class="dados">
        <strong><c:out value="${r.nomeQuadra}"/></strong>
        <span>${r.dataReservaFormatada} &bull; ${r.horarioFormatado}</span>
      </div>
      <div class="dir">
        <span class="valor">${r.valorPagoFormatado}</span>
        <span></span>
        <a class="btn branco pequeno" href="${ctx}/quadra?id=${r.idQuadra}">Ver detalhes</a>
        <span class="status ${r.ativa ? 'st-ok' : 'st-erro'}">${r.statusExibicao}</span>

        <c:if test="${not r.ativa}">
          <span class="devolvido">Devolvido: ${r.valorDevolvidoFormatado}</span>
        </c:if>
        <c:if test="${r.ativa and not r.passada}">
          <form method="post" action="${ctx}/cancelar-reserva"
                onsubmit="return confirm('Cancelar esta reserva? Cancelando com menos de 24h de antecedência, só 50% do valor é devolvido.')">
            <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
            <input type="hidden" name="id" value="${r.idReserva}">
            <button class="link-perigo" type="submit">Cancelar reserva</button>
          </form>
        </c:if>
      </div>
    </div>
  </c:forEach>

  <c:if test="${empty reservas}">
    <p class="vazio">
      <c:choose>
        <c:when test="${aba == 'futuras'}">Você não tem reservas futuras. <a class="acao" href="${ctx}/quadras">Encontrar uma quadra</a></c:when>
        <c:otherwise>Você ainda não tem reservas passadas.</c:otherwise>
      </c:choose>
    </p>
  </c:if>
</div>

<div class="rodape"></div>
</body>
</html>
