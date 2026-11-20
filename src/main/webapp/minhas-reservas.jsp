<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="admin-reservas"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Reservas - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-menu.jspf" %>
<div class="pagina">
  <h1>Gerenciar Reservas</h1>
  <div class="migalha">QuadraDas / Gerenciar Reservas</div>

  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>
    <main class="painel">
      <h2>Gerenciar Reservas</h2>
      <div class="barra">
        <div class="campo"><label>Pesquisar...</label><input type="text" oninput="filtrar(this.value)"></div>
        <a class="btn" href="${ctx}/lista-quadras.jsp">Adicionar</a>
      </div>
      <table>
        <thead><tr><th>Usuário</th><th>Quadra</th><th>Data/Hora</th><th>Status</th><th>Ações</th></tr></thead>
        <tbody id="linhas">
          <c:forEach begin="1" end="2">
            <tr><td>Pedro Faro</td><td>Quadra do Centro</td><td>15/04 • 19:00</td><td>Confirmada</td><td><a href="${ctx}/minhas-reservas.jsp">Ver</a></td></tr>
            <tr><td>Lucas Silva</td><td>Arena Sport</td><td>16/04 • 18:00</td><td>Pendente</td><td><a href="${ctx}/minhas-reservas.jsp">Ver</a></td></tr>
            <tr><td>João Lima</td><td>PlayQuadra</td><td>18/04 • 20:00</td><td>Cancelada</td><td><a href="${ctx}/minhas-reservas.jsp">Ver</a></td></tr>
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