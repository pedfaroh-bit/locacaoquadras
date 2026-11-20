<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="admin-usuarios"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Usuários - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-menu.jspf" %>
<div class="pagina">
  <h1>Gerenciar Usuários</h1>
  <div class="migalha">QuadraDas / Gerenciar Usuários</div>

  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>
    <main class="painel">
      <h2>Gerenciar Usuários</h2>
      <div class="barra">
        <div class="campo"><label>Pesquisar...</label><input type="text" oninput="filtrar(this.value)"></div>
        <a class="btn" href="${ctx}/cadastro.jsp">Adicionar</a>
      </div>
      <table>
        <thead><tr><th>Nome</th><th>E-mail</th><th>Tipo</th><th>Status</th><th>Ações</th></tr></thead>
        <tbody id="linhas">
          <c:forEach begin="1" end="2">
            <tr><td>Pedro Faro</td><td>pedro@email.com</td><td>Cliente</td><td>Ativo</td><td><a href="${ctx}/perfil.jsp">Editar</a></td></tr>
            <tr><td>Mariana Costa</td><td>mariana@email.com</td><td>Cliente</td><td>Ativo</td><td><a href="${ctx}/perfil.jsp">Editar</a></td></tr>
            <tr><td>João Lima</td><td>joao@email.com</td><td>Cliente</td><td>Inativo</td><td><a href="${ctx}/perfil.jsp">Editar</a></td></tr>
          </c:forEach>
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