<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="horarios"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Horários - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-menu.jspf" %>
<div class="pagina">
  <h1>Gerenciar Horários</h1>
  <div class="migalha">QuadraDas / Gerenciar Horários</div>

  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>

    <main class="painel">
      <h2>${empty horario ? 'Gerenciar Horários' : 'Editar Horário'}</h2>
      <c:if test="${not empty param.erro}"><p class="msg-erro"><c:out value="${param.erro}"/></p></c:if>

      <div class="barra">
        <div class="campo"><label>Pesquisar...</label><input type="text" oninput="filtrar(this.value)"></div>
        <button type="button" class="btn" onclick="document.getElementById('formHorario').hidden=false">Adicionar</button>
      </div>

      <form id="formHorario" class="form" method="post" action="${ctx}/create-horario" ${empty horario && param.novo != '1' ? 'hidden' : ''}>
        <input type="hidden" name="id" value="${horario.idHorario}">
        <div class="campo">
          <label>Quadra</label>
          <select name="idQuadra" required>
            <option value="">Selecione</option>
            <c:forEach var="q" items="${quadras}">
              <option value="${q.idQuadra}" ${q.idQuadra == horario.idQuadra ? 'selected' : ''}><c:out value="${q.nome}"/></option>
            </c:forEach>
          </select>
        </div>
        <div class="campo">
          <label>Hora início</label>
          <input type="time" name="horaInicio" value="${horario.horaInicio}" required>
        </div>
        <div class="campo">
          <label>Hora fim</label>
          <input type="time" name="horaFim" value="${horario.horaFim}" required>
        </div>
        <div class="acoes">
          <button class="btn" type="submit">Salvar</button>
          <a class="btn branco" href="${ctx}/horarios">Cancelar</a>
        </div>
      </form>

      <table>
        <thead><tr><th>ID</th><th>Quadra</th><th>Hora início</th><th>Hora fim</th><th>Ações</th></tr></thead>
        <tbody id="linhas">
          <c:forEach var="h" items="${horarios}">
            <tr>
              <td>${h.idHorario}</td>
              <td><c:out value="${h.nomeQuadra}"/></td>
              <td>${h.horaInicio}</td>
              <td>${h.horaFim}</td>
              <td class="acoes">
                <a href="${ctx}/horarios?id=${h.idHorario}">Editar</a>
                <form method="post" action="${ctx}/delete-horario" style="display:inline">
                  <input type="hidden" name="id" value="${h.idHorario}">
                  <button class="link-perigo" type="submit" onclick="return confirm('Excluir este horário?');">Excluir</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty horarios}"><tr><td colspan="5" class="vazio">Nenhum horário cadastrado.</td></tr></c:if>
        </tbody>
      </table>
    </main>
  </div>
</div>
<div class="rodape"></div>
<script>
function filtrar(t) {
  t = t.toLowerCase();
  var linhas = document.querySelectorAll('#linhas tr');
  for (var i = 0; i < linhas.length; i++) {
    linhas[i].style.display = linhas[i].textContent.toLowerCase().indexOf(t) > -1 ? '' : 'none';
  }
}
</script>
</body>
</html>