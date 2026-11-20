<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="quadras"/>
<c:set var="preco" value="0"/>
<c:forEach var="m" items="${modalidades}"><c:if test="${m.idModalidade == quadra.idModalidade}"><c:set var="preco" value="${m.precoSemana}"/></c:if></c:forEach>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Realizar Reserva - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-menu.jspf" %>
<div class="pagina">
  <h1>Realizar Reserva</h1>
  <div class="migalha">QuadraDas / <a href="${ctx}/lista-quadras.jsp">Quadras</a> / <a href="${ctx}/quadra-detalhe.jsp?id=${quadra.idQuadra}">Detalhes</a> / Realizar Reserva</div>

  <div class="card res-info">
    <strong><c:out value="${quadra.nome}"/></strong>
    <span class="sub"><c:out value="${quadra.nomeModalidade}"/> • <c:out value="${quadra.endereco}"/></span>
  </div>

  <h2 class="secao" style="margin-top:0">1. Escolha data e horário</h2>
  <form id="formReserva" method="post" action="${ctx}/create-reserva">
    <input type="hidden" name="idQuadra" value="${quadra.idQuadra}">
    <input type="hidden" name="data" id="campoData">
    <input type="hidden" name="horario" id="campoHora" value="<c:out value='${param.hora}'/>">

    <div class="res-grid">
      <div class="card calendario">
        <h2 id="mesAno"></h2>
        <p class="sub" style="margin-top:14px;font-weight:700">Calendário</p>
        <div class="dias" id="dias"></div>
      </div>
      <div>
        <h2>Horários</h2>
        <div class="slots" id="slots">
          <button type="button" class="slot">18:00</button>
          <button type="button" class="slot">19:00</button>
          <button type="button" class="slot">20:00</button>
          <button type="button" class="slot">21:00</button>
          <button type="button" class="slot">22:00</button>
        </div>
        <div class="total">
          <small>Total da reserva</small>
          <strong>R$ <fmt:formatNumber value="${preco}" minFractionDigits="2" maxFractionDigits="2"/></strong>
        </div>
      </div>
    </div>

    <button class="btn largo" type="submit">Confirmar reserva</button>
  </form>
</div>
<div class="rodape"></div>

<script>
(function () {
  var meses = ['Janeiro','Fevereiro','Março','Abril','Maio','Junho','Julho','Agosto','Setembro','Outubro','Novembro','Dezembro'];
  var hoje = new Date(), ano = hoje.getFullYear(), mes = hoje.getMonth();
  var box = document.getElementById('dias');
  document.getElementById('mesAno').textContent = meses[mes] + ' ' + ano;

  function dois(n) { return n < 10 ? '0' + n : '' + n; }

  var ultimo = new Date(ano, mes + 1, 0).getDate();
  for (var d = 1; d <= ultimo; d++) {
    var b = document.createElement('button');
    b.type = 'button';
    b.className = 'dia';
    b.textContent = d;
    if (d < hoje.getDate()) { b.disabled = true; }
    b.onclick = (function (dia, btn) {
      return function () {
        var antigo = box.querySelector('.sel');
        if (antigo) { antigo.classList.remove('sel'); }
        btn.classList.add('sel');
        document.getElementById('campoData').value = ano + '-' + dois(mes + 1) + '-' + dois(dia);
      };
    })(d, b);
    box.appendChild(b);
  }

  var horaInicial = document.getElementById('campoHora').value;
  var slots = document.querySelectorAll('#slots .slot');
  for (var i = 0; i < slots.length; i++) {
    if (horaInicial && slots[i].textContent === horaInicial) {
      slots[i].classList.add('sel');
    }
    slots[i].onclick = function () {
      for (var j = 0; j < slots.length; j++) { slots[j].classList.remove('sel'); }
      this.classList.add('sel');
      document.getElementById('campoHora').value = this.textContent;
    };
  }

  document.getElementById('formReserva').onsubmit = function (e) {
    if (!document.getElementById('campoData').value || !document.getElementById('campoHora').value) {
      alert('Escolha a data e o horário.');
      e.preventDefault();
    }
  };
})();
</script>
</body>
</html>