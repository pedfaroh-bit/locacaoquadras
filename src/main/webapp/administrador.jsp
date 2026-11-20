<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="administradores"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Administradores - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-menu.jspf" %>
<div class="pagina">
  <h1>Gerenciar Administradores</h1>
  <div class="migalha">QuadraDas / Gerenciar Administradores</div>

  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>

    <main class="painel">
      <h2>${empty administrador ? 'Gerenciar Administradores' : 'Editar Administrador'}</h2>
      <c:if test="${not empty param.erro}"><p class="msg-erro"><c:out value="${param.erro}"/></p></c:if>

      <div class="barra">
        <div class="campo"><label>Pesquisar...</label><input type="text" oninput="filtrar(this.value)"></div>
        <button type="button" class="btn" onclick="document.getElementById('formAdmin').hidden=false">Adicionar</button>
      </div>

      <form id="formAdmin" class="form" method="post" action="${ctx}/create-administrador" ${empty administrador ? 'hidden' : ''}>
        <input type="hidden" name="id" value="${administrador.idAdministrador}">
        <div class="campo"><label>Nome</label><input type="text" name="nome" value="<c:out value='${administrador.nome}'/>" required></div>
        <div class="campo"><label>E-mail</label><input type="email" name="email" value="<c:out value='${administrador.email}'/>" required></div>
        <div class="campo">
          <label>Senha</label>
          <input type="password" name="senha" ${empty administrador ? 'required' : ''}>
        </div>
        <c:if test="${not empty administrador}">
          <p class="sub largo">(deixe em branco para manter a senha atual)</p>
        </c:if>
        <div class="acoes">
          <button class="btn" type="submit">Salvar</button>
          <a class="btn branco" href="${ctx}/administrador.jsp">Cancelar</a>
        </div>
      </form>

      <table>
        <thead><tr><th>ID</th><th>Nome</th><th>E-mail</th><th>Ações</th></tr></thead>
        <tbody id="linhas">
          <c:forEach var="a" items="${administradores}">
            <tr>
              <td>${a.idAdministrador}</td>
              <td><c:out value="${a.nome}"/></td>
              <td><c:out value="${a.email}"/></td>
              <td class="acoes">
                <a href="${ctx}/administrador.jsp?id=${a.idAdministrador}">Editar</a>
                <form method="post" action="${ctx}/delete-administrador" style="display:inline">
                  <input type="hidden" name="id" value="${a.idAdministrador}">
                  <button class="link-perigo" type="submit" onclick="return confirm('Excluir este administrador?');">Excluir</button>
                </form>
              </td>
            </tr>
          </c:forEach>
          <c:if test="${empty administradores}"><tr><td colspan="4" class="vazio">Nenhum administrador cadastrado.</td></tr></c:if>
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