<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="menuAtivo" value="quadras"/>
<c:set var="total" value="${fn:length(quadras)}"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Lista de Quadras - QuadraDas</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-topo.jspf" %>

<div class="pagina">
  <h1>Lista de Quadras</h1>
  <div class="migalha">QuadraDas / Lista de Quadras</div>
  <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>

  <form class="card busca" method="get" action="${ctx}/quadras">
    <div class="campo">
      <label for="q">Buscar por nome, bairro ou cidade</label>
      <input id="q" type="text" name="q" value="<c:out value='${busca}'/>">
    </div>
    <button class="btn branco" type="button"
            onclick="var f=document.getElementById('filtros');f.hidden=!f.hidden">Filtrar</button>
    <button class="btn" type="submit">Buscar</button>

    <div class="filtros" id="filtros"
         <c:if test="${modalidadeSel == 0 and empty dataSel and empty horarioSel}">hidden</c:if>>
      <div class="campo">
        <label for="modalidade">Modalidade</label>
        <select id="modalidade" name="modalidade">
          <option value="0">Todas</option>
          <c:forEach var="m" items="${modalidades}">
            <option value="${m.idModalidade}" ${m.idModalidade == modalidadeSel ? 'selected' : ''}><c:out value="${m.nome}"/></option>
          </c:forEach>
        </select>
      </div>
      <div class="campo">
        <label for="data">Com horário livre em</label>
        <input id="data" type="date" name="data" min="${hoje}" value="${dataSel}">
      </div>
      <div class="campo">
        <label for="horario">A partir das</label>
        <input id="horario" type="time" name="horario" value="${horarioSel}">
      </div>
    </div>
  </form>

  <p class="contagem">
    <c:choose>
      <c:when test="${total == 1}">1 quadra encontrada</c:when>
      <c:otherwise>${total} quadras encontradas</c:otherwise>
    </c:choose>
  </p>

  <div class="grade-q">
    <c:forEach var="q" items="${quadras}">
      <a class="card-q" href="${ctx}/quadra?id=${q.idQuadra}">
        <div class="thumb"></div>
        <div>
          <strong><c:out value="${q.nome}"/></strong>
          <span class="sub"><c:out value="${q.nomeModalidade}"/> &bull; <c:out value="${q.endereco}"/></span>
          <span class="preco">${q.precoSemanaFormatado}/h <small>(seg a sex)</small></span>
        </div>
      </a>
    </c:forEach>
  </div>
  <c:if test="${total == 0}">
    <p class="vazio">Nenhuma quadra encontrada com esses filtros.</p>
  </c:if>
</div>

<div class="rodape"></div>
</body>
</html>
