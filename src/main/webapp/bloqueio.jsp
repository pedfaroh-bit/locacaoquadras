<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="bloqueios"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Bloqueios - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-menu.jspf" %>
<div class="pagina">
  <h1>Gerenciar Bloqueios</h1>
  <div class="migalha">QuadraDas / Gerenciar Bloqueios</div>

  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>

    <main class="painel">
      <h2>${empty bloqueio ? 'Gerenciar Bloqueios' : 'Editar Bloqueio'}</h2>
      <c:if test="${not empty param.erro}"><p class="msg-erro"><c:out value="${param.erro}"/></p></c:if>

      <div class="barra">
        <div class="campo"><label>Pesquisar...</label><input type="text" oninput="filtrar(this.value)"></div>
        <button type="button" class="btn" onclick="document.getElementById('formBloqueio').hidden=false">Adicionar</button>
      </div>

      <form id="formBloqueio" class="form" method="post" action="${ctx}/create-bloqueio" ${empty bloqueio ? 'hidden' : ''}>
        <input type="hidden" name="id" value="${bloqueio.idBloqueio}">
        <div class="campo">
          <label>Administrador</label>
          <select name="idAdministrador" required>
            <option value="">Selecione</option>
            <c:forEach var="a" items="${administradores}">
              <option value="${a.idAdministrador}" ${a.idAdministrador == bloqueio.idAdministrador ? 'selected' : ''}><c:out value="${a.nome}"/></option>
            </c:forEach>
          </select>
        </div>
        <div class="campo">
          <label>Horário</label>
          <select name="idHorario" required>
            <option value="">Selecione</option>
            <c:forEach var="h" items="${horarios}">
              <option value="${h.idHorario}" ${h.idHorario == bloqueio.idHorario ? 'selected' : ''}><c:out value="${h.descricao}"/></option>
            </c:forEach>
          </select>
        </div>
        <div class="campo">
          <label>Data</label>
          <input type="date" name="dataBloqueio" value="${bloqueio.dataBloqueio}" required>
        </div>
        <div class="campo">
          <label>Motivo</label>
          <input type="text" name="motivo" value="<c:out value='${bloqueio.motivo}'/>">
        </div>
        <div class="acoes">
          <button class="btn" type="submit">Salvar</button>
          <a class="btn branco" href="${ctx}/bloqueio.jsp">Cancelar</a>
        </div>
      </form>

      <table>
        <thead>
          <tr>
            <th>ID</th>
            <th>Data</th>
            <th>Quadra</th>
            <th>Horário</th>
            <th>Motivo</th>
            <th>Administrador</th>
            <th>Ações</th>
          </tr>
        </thead>
        <tbody id="linhas">
          <c:forEach var="b" items="${bloqueios}">
            <tr>
              <td>${b.idBloqueio}</td>
              <td>${b.dataBloqueioFormatada}</td>
              <td><c:out value="${b.nomeQuadra}"/></td>
              <td>${b.horaInicio} às ${b.horaFim}</td>
              <td><c:out value="${b.motivo}"/></td>
              <td><c:out value="${b.nomeAdministrador}"/></td>
              <td class="acoes">
                <a href="${ctx}/bloqueio.jsp?id=${b.idBloqueio}">Editar</a>
                <form method="post" action="${ctx}/delete-bloqueio" style="display:inline">
                  <input type="hidden" name="id" value="${b.idBloqueio}">
                  <button class="link-perigo" type="submit" onclick="return confirm('Excluir este bloqueio?');">Excluir</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty bloqueios}"><tr><td colspan="7" class="vazio">Nenhum bloqueio cadastrado.</td></tr></c:if>
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