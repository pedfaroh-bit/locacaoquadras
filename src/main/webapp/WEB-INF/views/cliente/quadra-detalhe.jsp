<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="menuAtivo" value="quadras"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title><c:out value="${quadra.nome}"/> - QuadraDas</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-topo.jspf" %>

<div class="pagina">
  <h1>Detalhes da Quadra</h1>
  <div class="migalha">QuadraDas / <a href="${ctx}/quadras">Lista de Quadras</a> / Detalhes da Quadra</div>
  <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>

  <section class="card det">
    <div class="foto"></div>
    <div>
      <h2><c:out value="${quadra.nome}"/></h2>
      <p class="sub"><c:out value="${quadra.nomeModalidade}"/> &bull; <c:out value="${quadra.endereco}"/></p>
      <p class="valor">${preco}/h</p>
      <p class="sub" style="margin-top:6px">
        Seg a sex ${quadra.precoSemanaFormatado} &bull; Sáb e dom ${quadra.precoFimdesemanaFormatado}
      </p>
    </div>
    <a class="btn" href="${ctx}/reservar?id=${quadra.idQuadra}&amp;data=${data}">Reservar agora</a>
  </section>

  <h2 class="secao">Descrição e estrutura</h2>
  <div class="estrutura">
    <c:forEach var="item" items="${quadra.itensEstrutura}">
      <div class="item"><strong><c:out value="${item}"/></strong><span>Disponível para reservas</span></div>
    </c:forEach>
    <c:if test="${empty quadra.itensEstrutura}">
      <p class="vazio">Sem informações de estrutura cadastradas.</p>
    </c:if>
  </div>

  <h2 class="secao">Horários disponíveis</h2>
  <form class="dia-escolhida" method="get" action="${ctx}/quadra">
    <input type="hidden" name="id" value="${quadra.idQuadra}">
    <div class="campo">
      <label for="data">Data</label>
      <input id="data" type="date" name="data" min="${hoje}" value="${data}" onchange="this.form.submit()">
    </div>
    <noscript><button class="btn pequeno" type="submit">Ver</button></noscript>
  </form>

  <div class="horas">
    <c:forEach var="hd" items="${horarios}">
      <c:choose>
        <c:when test="${hd.disponivel}">
          <a class="hora" href="${ctx}/reservar?id=${quadra.idQuadra}&amp;data=${data}&amp;horario=${hd.horario.idHorario}">${hd.horario.horaInicio}</a>
        </c:when>
        <c:otherwise>
          <span class="hora off" title="${hd.motivo}">${hd.horario.horaInicio}</span>
        </c:otherwise>
      </c:choose>
    </c:forEach>
    <c:if test="${empty horarios}">
      <p class="vazio">Esta quadra ainda não tem horários cadastrados.</p>
    </c:if>
  </div>
</div>

<div class="rodape"></div>
</body>
</html>
