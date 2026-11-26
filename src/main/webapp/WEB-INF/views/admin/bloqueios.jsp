<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="menuAtivo" value="bloqueios"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Bloqueios - QuadraDas Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-topo.jspf" %>
<div class="pagina">
  <h1>Gerenciar Bloqueios</h1>
  <div class="migalha">QuadraDas / Gerenciar Bloqueios</div>
  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>
    <section class="painel">
      <h2>Gerenciar Bloqueios</h2>
      <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
      <form class="barra" method="get" action="${ctx}/admin/bloqueios">
        <div class="campo"><label for="q">Pesquisar...</label>
          <input id="q" type="text" name="q" value="<c:out value='${busca}'/>"></div>
        <a class="btn" href="${ctx}/admin/bloqueios?novo=1">Adicionar</a>
      </form>
      <c:if test="${novo}">
        <form class="form" method="post" action="${ctx}/admin/bloqueios">
          <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
          <div class="campo"><label for="idHorario">Quadra e horário</label>
            <select id="idHorario" name="idHorario" required>
              <option value="">Selecione</option>
              <c:forEach var="h" items="${horarios}">
                <option value="${h.idHorario}"><c:out value="${h.descricao}"/></option>
              </c:forEach>
            </select></div>
          <div class="campo"><label for="data">Data</label>
            <input id="data" type="date" name="data" min="${hoje}" required></div>
          <div class="campo largo"><label for="motivo">Motivo</label>
            <input id="motivo" name="motivo" maxlength="255" placeholder="Manutenção, evento, feriado..."></div>
          <div class="acoes"><button class="btn pequeno" type="submit">Bloquear</button>
            <a class="acao" href="${ctx}/admin/bloqueios">Cancelar</a></div>
        </form>
      </c:if>

      <table>
        <thead><tr><th>Quadra</th><th>Data</th><th>Horário</th><th>Motivo</th><th>Ações</th></tr></thead>
        <tbody>
        <c:forEach var="b" items="${bloqueios}">
          <tr>
            <td><c:out value="${b.nomeQuadra}"/></td>
            <td>${b.dataBloqueioFormatada}</td>
            <td>${b.horaInicio}&ndash;${b.horaFim}</td>
            <td><c:out value="${b.motivo}"/></td>
            <td class="acoes">
<form class="inline" method="post" action="${ctx}/admin/bloqueios" onsubmit="return confirm('Remover este bloqueio?')"><input type="hidden" name="_csrf" value="${sessionScope.csrf}"><input type="hidden" name="acao" value="excluir"><input type="hidden" name="id" value="${b.idBloqueio}"><button class="link-perigo" type="submit">Remover</button></form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty bloqueios}"><tr><td class="vazio" colspan="5">Nenhum bloqueio cadastrado.</td></tr></c:if>
        </tbody>
      </table>
    </section>
  </div>
</div>
<div class="rodape"></div>
</body>
</html>
