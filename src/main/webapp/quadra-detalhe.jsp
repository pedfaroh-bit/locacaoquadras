<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<fmt:setLocale value="pt_BR"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="quadras"/>
<c:set var="preco" value=""/>
<c:forEach var="m" items="${modalidades}"><c:if test="${m.idModalidade == quadra.idModalidade}"><c:set var="preco" value="${m.precoSemana}"/></c:if></c:forEach>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Detalhes da Quadra - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-menu.jspf" %>
<div class="pagina">
  <h1>Detalhes da Quadra</h1>
  <div class="migalha">QuadraDas / Detalhes da Quadra</div>

  <div class="card det">
    <div class="foto"></div>
    <div class="info">
      <h2><c:out value="${quadra.nome}"/></h2>
      <p class="sub"><c:out value="${quadra.nomeModalidade}"/> • <c:out value="${quadra.endereco}"/></p>
      <c:if test="${not empty preco}"><p class="valor">R$ <fmt:formatNumber value="${preco}" minFractionDigits="0" maxFractionDigits="2"/>/h</p></c:if>
    </div>
    <a class="btn" href="${ctx}/reservar.jsp?id=${quadra.idQuadra}">Reservar agora</a>
  </div>

  <h2 class="secao">Descrição e estrutura</h2>
  <div class="estrutura">
    <c:if test="${not empty quadra.descricao}">
      <c:forEach var="item" items="${fn:split(quadra.descricao, ',')}">
        <div class="item"><strong><c:out value="${fn:trim(item)}"/></strong><span>Disponível para reservas</span></div>
      </c:forEach>
    </c:if>
  </div>
  <c:if test="${empty quadra.descricao}"><p class="vazio">Esta quadra ainda não tem descrição.</p></c:if>

  <h2 class="secao">Horários disponíveis</h2>
  <div class="horas">
    <a class="hora" href="${ctx}/reservar.jsp?id=${quadra.idQuadra}&hora=18:00">18:00</a>
    <a class="hora" href="${ctx}/reservar.jsp?id=${quadra.idQuadra}&hora=19:00">19:00</a>
    <a class="hora" href="${ctx}/reservar.jsp?id=${quadra.idQuadra}&hora=20:00">20:00</a>
    <a class="hora" href="${ctx}/reservar.jsp?id=${quadra.idQuadra}&hora=21:00">21:00</a>
  </div>
</div>
<div class="rodape"></div>
</body>
</html>