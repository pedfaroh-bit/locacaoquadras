<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="pt_BR"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="quadras"/>
<c:set var="busca" value="${fn:toLowerCase(param.busca)}"/>
<c:set var="total" value="0"/>
<c:set var="cartoes">
  <c:forEach var="q" items="${quadras}">
    <c:set var="idStr">${q.idModalidade}</c:set>
    <c:if test="${(empty param.modalidade or param.modalidade == idStr) and (empty busca or fn:contains(fn:toLowerCase(q.nome), busca) or fn:contains(fn:toLowerCase(q.endereco), busca) or fn:contains(fn:toLowerCase(q.nomeModalidade), busca))}">
      <c:set var="total" value="${total + 1}"/>
      <c:set var="preco" value=""/>
      <c:forEach var="m" items="${modalidades}">
        <c:if test="${m.idModalidade == q.idModalidade}"><c:set var="preco" value="${m.precoSemana}"/></c:if>
      </c:forEach>
      <a class="card-q" href="${ctx}/quadra-detalhe.jsp?id=${q.idQuadra}">
        <div class="thumb"></div>
        <div>
          <strong><c:out value="${q.nome}"/></strong>
          <span class="sub"><c:out value="${q.nomeModalidade}"/> • <c:out value="${q.endereco}"/></span>
          <c:if test="${not empty preco}"><span class="preco">R$ <fmt:formatNumber value="${preco}" minFractionDigits="0" maxFractionDigits="2"/>/h</span></c:if>
        </div>
      </a>
    </c:if>
  </c:forEach>
</c:set>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Lista de Quadras - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-menu.jspf" %>
<div class="pagina">
  <h1>Lista de Quadras</h1>
  <div class="migalha">QuadraDas / Lista de Quadras</div>

  <form class="card busca" method="get" action="${ctx}/lista-quadras.jsp">
    <div class="campo">
      <label>Buscar por nome, bairro ou cidade</label>
      <input type="text" name="busca" value="<c:out value='${param.busca}'/>">
    </div>
    <button type="button" class="btn branco" onclick="var f=document.getElementById('filtros');f.hidden=!f.hidden">Filtrar</button>
    <button type="submit" class="btn">Buscar</button>
    <div id="filtros" class="filtros campo" ${empty param.modalidade ? 'hidden' : ''}>
      <label>Modalidade</label>
      <select name="modalidade">
        <option value="">Todas as modalidades</option>
        <c:forEach var="m" items="${modalidades}">
          <option value="${m.idModalidade}" ${param.modalidade == m.idModalidade ? 'selected' : ''}><c:out value="${m.nome}"/></option>
        </c:forEach>
      </select>
    </div>
  </form>

  <p class="contagem"><c:out value="${total}"/> ${total == 1 ? 'quadra encontrada' : 'quadras encontradas'}</p>
  <div class="grade-q">${cartoes}</div>
  <c:if test="${total == 0}"><p class="vazio">Nenhuma quadra encontrada para essa busca.</p></c:if>
</div>
<div class="rodape"></div>
</body>
</html>