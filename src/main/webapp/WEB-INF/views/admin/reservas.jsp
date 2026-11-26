<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="menuAtivo" value="reservas"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Reservas - QuadraDas Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-topo.jspf" %>
<div class="pagina">
  <h1>Gerenciar Reservas</h1>
  <div class="migalha">QuadraDas / Gerenciar Reservas</div>
  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>
    <section class="painel">
      <h2>Gerenciar Reservas</h2>
      <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
      <form class="barra" method="get" action="${ctx}/admin/reservas">
        <div class="campo"><label for="q">Pesquisar...</label>
          <input id="q" type="text" name="q" value="<c:out value='${busca}'/>"></div>
        <a class="btn" href="${ctx}/admin/reservas?novo=1">Adicionar</a>
      </form>
      <c:if test="${novo}">
        <form class="form" method="post" action="${ctx}/admin/reservas">
          <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
          <input type="hidden" name="acao" value="criar">
          <div class="campo"><label for="idUsuario">Cliente</label>
            <select id="idUsuario" name="idUsuario" required>
              <option value="">Selecione</option>
              <c:forEach var="u" items="${usuarios}">
                <option value="${u.idUsuario}"><c:out value="${u.nome}"/> (<c:out value="${u.email}"/>)</option>
              </c:forEach>
            </select></div>
          <div class="campo"><label for="idHorario">Quadra e horário</label>
            <select id="idHorario" name="idHorario" required>
              <option value="">Selecione</option>
              <c:forEach var="h" items="${horarios}">
                <option value="${h.idHorario}"><c:out value="${h.descricao}"/></option>
              </c:forEach>
            </select></div>
          <div class="campo"><label for="data">Data</label>
            <input id="data" type="date" name="data" min="${hoje}" required></div>
          <div class="acoes"><button class="btn pequeno" type="submit">Criar reserva</button>
            <a class="acao" href="${ctx}/admin/reservas">Cancelar</a></div>
        </form>
      </c:if>

      <c:if test="${not empty detalhe}">
        <div class="card detalhe">
          <h2>Reserva #${detalhe.idReserva}</h2>
          <dl>
            <dt>Usuário</dt><dd><c:out value="${detalhe.nomeUsuario}"/></dd>
            <dt>Quadra</dt><dd><c:out value="${detalhe.nomeQuadra}"/></dd>
            <dt>Data e horário</dt><dd>${detalhe.dataReservaFormatada} &bull; ${detalhe.horarioFormatado}</dd>
            <dt>Valor pago</dt><dd>${detalhe.valorPagoFormatado}</dd>
            <dt>Status</dt><dd class="${detalhe.ativa ? 'st-ok' : 'st-erro'}">${detalhe.statusExibicao}</dd>
            <c:if test="${not detalhe.ativa}">
              <dt>Valor devolvido</dt><dd>${detalhe.valorDevolvidoFormatado}</dd>
              <dt>Cancelada em</dt><dd>${detalhe.dataCancelamentoFormatada}</dd>
            </c:if>
          </dl>
          <c:if test="${detalhe.ativa}">
            <form class="acoes" method="post" action="${ctx}/admin/reservas"
                  onsubmit="return confirm('Cancelar esta reserva?')">
              <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
              <input type="hidden" name="acao" value="cancelar">
              <input type="hidden" name="id" value="${detalhe.idReserva}">
              <div class="campo" style="width:230px"><label for="valorDevolvido">Valor a devolver (R$) - vazio = tudo</label>
                <input id="valorDevolvido" name="valorDevolvido" inputmode="decimal" placeholder="${detalhe.valorPago}"></div>
              <button class="btn pequeno" type="submit">Cancelar reserva</button>
            </form>
          </c:if>
          <div style="margin-top:12px"><a class="acao" href="${ctx}/admin/reservas">Fechar</a> &nbsp;
            <form class="inline" method="post" action="${ctx}/admin/reservas" onsubmit="return confirm('Excluir definitivamente esta reserva?')">
              <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
              <input type="hidden" name="acao" value="excluir">
              <input type="hidden" name="id" value="${detalhe.idReserva}">
              <button class="link-perigo" type="submit">Excluir registro</button>
            </form></div>
        </div>
      </c:if>

      <table>
        <thead><tr><th>Usuário</th><th>Quadra</th><th>Data/Hora</th><th>Status</th><th>Ações</th></tr></thead>
        <tbody>
        <c:forEach var="r" items="${reservas}">
          <tr>
            <td><c:out value="${r.nomeUsuario}"/></td>
            <td><c:out value="${r.nomeQuadra}"/></td>
            <td>${r.dataHoraFormatada}</td>
            <td class="${r.ativa ? 'st-ok' : 'st-erro'}">${r.statusExibicao}</td>
            <td><a class="acao" href="${ctx}/admin/reservas?ver=${r.idReserva}">Ver</a></td>
          </tr>
        </c:forEach>
        <c:if test="${empty reservas}"><tr><td class="vazio" colspan="5">Nenhuma reserva encontrada.</td></tr></c:if>
        </tbody>
      </table>
    </section>
  </div>
</div>
<div class="rodape"></div>
</body>
</html>
