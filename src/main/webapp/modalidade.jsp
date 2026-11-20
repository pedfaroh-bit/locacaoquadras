<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="pt_BR"/>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="modalidades"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Modalidades - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-menu.jspf" %>
<div class="pagina">
  <h1>Gerenciar Modalidades</h1>
  <div class="migalha">QuadraDas / Gerenciar Modalidades</div>

  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>

    <main class="painel">
      <h2>${empty modalidade ? 'Gerenciar Modalidades' : 'Editar Modalidade'}</h2>
      <c:if test="${not empty param.erro}"><p class="msg-erro"><c:out value="${param.erro}"/></p></c:if>

      <div class="barra">
        <div class="campo"><label>Pesquisar...</label><input type="text" oninput="filtrar(this.value)"></div>
        <button type="button" class="btn" onclick="document.getElementById('formModalidade').hidden=false">Adicionar</button>
      </div>

      <form id="formModalidade" class="form" method="post" action="${ctx}/create-modalidade" ${empty modalidade ? 'hidden' : ''}>
        <input type="hidden" name="id" value="${modalidade.idModalidade}">
        <div class="campo"><label>Nome</label><input type="text" name="nome" value="<c:out value='${modalidade.nome}'/>" required></div>
        <div class="campo">
          <label>Preço semana</label>
          <input type="number" name="precoSemana" value="${modalidade.precoSemana}" min="0" step="0.01" required>
        </div>
        <div class="campo">
          <label>Preço fim de semana</label>
          <input type="number" name="precoFimdesemana" value="${modalidade.precoFimdesemana}" min="0" step="0.01" required>
        </div>
        <div class="acoes">
          <button class="btn" type="submit">Salvar</button>
          <a class="btn branco" href="${ctx}/modalidades">Cancelar</a>
        </div>
      </form>

      <table>
        <thead><tr><th>ID</th><th>Nome</th><th>Preço semana</th><th>Preço fim de semana</th><th>Ações</th></tr></thead>
        <tbody id="linhas">
          <c:forEach var="m" items="${modalidades}">
            <tr>
              <td>${m.idModalidade}</td>
              <td><c:out value="${m.nome}"/></td>
              <td><fmt:formatNumber value="${m.precoSemana}" minFractionDigits="2" maxFractionDigits="2"/></td>
              <td><fmt:formatNumber value="${m.precoFimdesemana}" minFractionDigits="2" maxFractionDigits="2"/></td>
              <td class="acoes">
                <a href="${ctx}/modalidades?id=${m.idModalidade}">Editar</a>
                <form method="post" action="${ctx}/delete-modalidade" style="display:inline">
                  <input type="hidden" name="id" value="${m.idModalidade}">
                  <button class="link-perigo" type="submit" onclick="return confirm('Excluir esta modalidade?');">Excluir</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty modalidades}"><tr><td colspan="5" class="vazio">Nenhuma modalidade cadastrada.</td></tr></c:if>
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