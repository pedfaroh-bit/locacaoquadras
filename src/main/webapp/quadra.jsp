<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="quadras"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Quadras - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-menu.jspf" %>
<div class="pagina">
  <h1>Gerenciar Quadras</h1>
  <div class="migalha">QuadraDas / Gerenciar Quadras</div>

  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>

    <main class="painel">
      <h2>Gerenciar Quadras</h2>
      <c:if test="${not empty param.erro}"><p class="msg-erro"><c:out value="${param.erro}"/></p></c:if>

      <div class="barra">
        <div class="campo"><label>Pesquisar...</label><input type="text" oninput="filtrar(this.value)"></div>
        <button type="button" class="btn" onclick="document.getElementById('formQuadra').hidden=false">Adicionar</button>
      </div>

      <form id="formQuadra" class="form" method="post" action="${ctx}/create-quadra" ${empty quadra && param.novo != '1' ? 'hidden' : ''}>
        <input type="hidden" name="id" value="${quadra.idQuadra}">
        <div class="campo">
          <label>Nome</label>
          <input type="text" name="nome" value="<c:out value='${quadra.nome}'/>" required>
        </div>
        <div class="campo">
          <label>Endereço</label>
          <input type="text" name="endereco" value="<c:out value='${quadra.endereco}'/>" required>
        </div>
        <div class="campo">
          <label>Modalidade</label>
          <select name="idModalidade" required>
            <option value="">Selecione</option>
            <c:forEach var="m" items="${modalidades}">
              <option value="${m.idModalidade}" ${m.idModalidade == quadra.idModalidade ? 'selected' : ''}><c:out value="${m.nome}"/></option>
            </c:forEach>
          </select>
        </div>
        <div class="campo largo">
          <label>Descrição (separe os itens por vírgula)</label>
          <textarea name="descricao" rows="2"><c:out value="${quadra.descricao}"/></textarea>
        </div>
        <div class="acoes">
          <button class="btn" type="submit">Salvar</button>
          <a class="btn branco" href="${ctx}/quadras">Cancelar</a>
        </div>
      </form>

      <table>
        <thead><tr><th>Nome</th><th>Localização</th><th>Esporte</th><th>Ações</th></tr></thead>
        <tbody id="linhas">
          <c:forEach var="q" items="${quadras}">
            <tr>
              <td><c:out value="${q.nome}"/></td>
              <td><c:out value="${q.endereco}"/></td>
              <td><c:out value="${q.nomeModalidade}"/></td>
              <td class="acoes">
                <a href="${ctx}/quadras?id=${q.idQuadra}">Editar</a>
                <form method="post" action="${ctx}/delete-quadra" style="display:inline">
                  <input type="hidden" name="id" value="${q.idQuadra}">
                  <button class="link-perigo" type="submit" onclick="return confirm('Excluir esta quadra?');">Excluir</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty quadras}"><tr><td colspan="4" class="vazio">Nenhuma quadra cadastrada.</td></tr></c:if>
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