<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="menuAtivo" value="horarios"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Horários - QuadraDas Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-topo.jspf" %>
<div class="pagina">
  <h1>Gerenciar Horários</h1>
  <div class="migalha">QuadraDas / Gerenciar Horários</div>
  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>
    <section class="painel">
      <h2>Gerenciar Horários</h2>
      <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
      <div class="barra">
        <form class="sel-semana" method="get" action="${ctx}/admin/horarios">
          <input type="hidden" name="semana" value="${semana}">
          <div class="campo"><label for="quadra">Quadra</label>
            <select id="quadra" name="quadra" onchange="this.form.submit()">
              <c:forEach var="q" items="${quadras}">
                <option value="${q.idQuadra}" ${q.idQuadra == idQuadra ? 'selected' : ''}><c:out value="${q.nome}"/></option>
              </c:forEach>
            </select></div>
          <noscript><button class="btn pequeno" type="submit">Ver</button></noscript>
        </form>
        <a class="btn" href="${ctx}/admin/horarios?novo=1&amp;quadra=${idQuadra}">Adicionar</a>
      </div>

      <c:if test="${empty quadras}"><p class="vazio">Cadastre uma quadra primeiro para criar horários.</p></c:if>

      <c:if test="${not empty item}">
        <form class="form" method="post" action="${ctx}/admin/horarios">
          <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
          <input type="hidden" name="id" value="${item.idHorario}">
          <div class="campo largo"><label for="fq">Quadra</label>
            <select id="fq" name="idQuadra" required>
              <c:forEach var="q" items="${quadras}">
                <option value="${q.idQuadra}" ${q.idQuadra == item.idQuadra ? 'selected' : ''}><c:out value="${q.nome}"/></option>
              </c:forEach>
            </select></div>
          <div class="campo"><label for="horaInicio">Hora de início</label>
            <input id="horaInicio" type="time" name="horaInicio" required value="${item.horaInicio}"></div>
          <div class="campo"><label for="horaFim">Hora de fim</label>
            <input id="horaFim" type="time" name="horaFim" required value="${item.horaFim}"></div>
          <div class="acoes"><button class="btn pequeno" type="submit">Salvar</button>
            <a class="acao" href="${ctx}/admin/horarios?quadra=${idQuadra}&amp;semana=${semana}">Cancelar</a></div>
        </form>
      </c:if>

      <c:if test="${idQuadra != 0}">
        <div class="semana-nav">
          <a href="${ctx}/admin/horarios?quadra=${idQuadra}&amp;semana=${semanaAnterior}">&lsaquo; Semana anterior</a>
          <span>Semana de ${cabecalho[0]}</span>
          <a href="${ctx}/admin/horarios?quadra=${idQuadra}&amp;semana=${semanaProxima}">Próxima semana &rsaquo;</a>
        </div>
        <table class="grade">
          <thead>
            <tr><th>Horário</th>
              <c:forEach var="d" items="${cabecalho}"><th>${d}</th></c:forEach>
            </tr>
          </thead>
          <tbody>
          <c:forEach var="l" items="${linhas}">
            <tr>
              <td class="hora-col">${l.horario.horaInicio}&ndash;${l.horario.horaFim}<br>
                <a class="acao" href="${ctx}/admin/horarios?editar=${l.horario.idHorario}&amp;quadra=${idQuadra}&amp;semana=${semana}">Editar</a> &nbsp;
                <form class="inline" method="post" action="${ctx}/admin/horarios" onsubmit="return confirm('Excluir este horário?')">
                  <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
                  <input type="hidden" name="acao" value="excluir">
                  <input type="hidden" name="id" value="${l.horario.idHorario}">
                  <input type="hidden" name="idQuadra" value="${idQuadra}">
                  <button class="link-perigo" type="submit">Excluir</button>
                </form></td>
              <c:forEach var="c" items="${l.celulas}">
                <td class="${c == 'Livre' ? 'livre' : (c == 'Reservado' ? 'reservado' : (c == 'Bloqueado' ? 'bloqueado' : 'passou'))}">${c}</td>
              </c:forEach>
            </tr>
          </c:forEach>
          <c:if test="${empty linhas}"><tr><td class="vazio" colspan="8">Esta quadra ainda não tem horários. Use "Adicionar".</td></tr></c:if>
          </tbody>
        </table>
      </c:if>
    </section>
  </div>
</div>
<div class="rodape"></div>
</body>
</html>
