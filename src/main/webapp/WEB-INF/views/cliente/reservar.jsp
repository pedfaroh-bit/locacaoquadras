<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="menuAtivo" value="quadras"/>
<c:set var="temLivre" value="false"/>
<c:forEach var="hd" items="${horarios}"><c:if test="${hd.disponivel}"><c:set var="temLivre" value="true"/></c:if></c:forEach>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Realizar Reserva - QuadraDas</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-topo.jspf" %>

<div class="pagina">
  <h1>Realizar Reserva</h1>
  <div class="migalha">QuadraDas / Realizar Reserva</div>
  <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>

  <section class="card res-info">
    <strong><c:out value="${quadra.nome}"/></strong>
    <span class="sub"><c:out value="${quadra.nomeModalidade}"/> &bull; <c:out value="${quadra.endereco}"/></span>
  </section>

  <h2 class="secao">1. Escolha data e horário</h2>

  <form method="post" action="${ctx}/reservar">
    <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
    <input type="hidden" name="id" value="${quadra.idQuadra}">
    <input type="hidden" name="data" value="${data}">

    <div class="res-grid">
      <div class="card calendario">
        <div class="cal-topo">
          <c:choose>
            <c:when test="${not empty mesAnterior}"><a href="${ctx}/reservar?id=${quadra.idQuadra}&amp;data=${data}&amp;mes=${mesAnterior}" title="Mês anterior">&lsaquo;</a></c:when>
            <c:otherwise><a class="desligado" href="#">&lsaquo;</a></c:otherwise>
          </c:choose>
          <h2><c:out value="${mesNome}"/></h2>
          <c:choose>
            <c:when test="${not empty mesProximo}"><a href="${ctx}/reservar?id=${quadra.idQuadra}&amp;data=${data}&amp;mes=${mesProximo}" title="Próximo mês">&rsaquo;</a></c:when>
            <c:otherwise><a class="desligado" href="#">&rsaquo;</a></c:otherwise>
          </c:choose>
        </div>
        <p class="sub" style="margin-top:10px">Calendário</p>

        <div class="dias">
          <span class="dia-sem">D</span><span class="dia-sem">S</span><span class="dia-sem">T</span>
          <span class="dia-sem">Q</span><span class="dia-sem">Q</span><span class="dia-sem">S</span><span class="dia-sem">S</span>
          <c:forEach var="d" items="${dias}">
            <c:choose>
              <c:when test="${d.vazio}"><span class="dia vazio-dia"></span></c:when>
              <c:when test="${d.passado}"><span class="dia off">${d.numero}</span></c:when>
              <c:otherwise>
                <a class="dia ${d.selecionado ? 'sel' : ''}" href="${ctx}/reservar?id=${quadra.idQuadra}&amp;data=${d.iso}">${d.numero}</a>
              </c:otherwise>
            </c:choose>
          </c:forEach>
        </div>
      </div>

      <div>
        <h2>Horários <small class="sub">&mdash; ${dataFormatada}</small></h2>
        <div class="slots">
          <c:forEach var="hd" items="${horarios}">
            <c:choose>
              <c:when test="${hd.disponivel}">
                <label class="slot-op">
                  <input type="radio" name="idHorario" value="${hd.horario.idHorario}"
                         ${horarioSel == hd.horario.idHorario ? 'checked' : ''} required>
                  <span class="slot">${hd.horario.horaInicio}</span>
                </label>
              </c:when>
              <c:otherwise>
                <span class="slot off" title="${hd.motivo}">${hd.horario.horaInicio}</span>
              </c:otherwise>
            </c:choose>
          </c:forEach>
          <c:if test="${empty horarios}"><p class="sem-horario">Esta quadra ainda não tem horários cadastrados.</p></c:if>
          <c:if test="${not empty horarios and not temLivre}"><p class="sem-horario">Sem horários livres nesta data. Escolha outro dia.</p></c:if>
        </div>

        <div class="total">
          <small>Total da reserva</small>
          <strong>${total}</strong>
        </div>
      </div>
    </div>

    <button class="btn largo" type="submit" ${temLivre ? '' : 'disabled'}>Confirmar reserva</button>
  </form>
</div>

<div class="rodape"></div>
</body>
</html>
