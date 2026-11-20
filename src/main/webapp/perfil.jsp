<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="perfil"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Meu Perfil - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-menu.jspf" %>
<div class="pagina">
  <h1>Meu Perfil</h1>
  <div class="migalha">QuadraDas / Meu Perfil</div>

  <div class="perfil">
    <aside class="card menu-perfil">
      <a href="${ctx}/perfil.jsp">Meu Perfil</a>
      <a href="${ctx}/minhas-reservas.jsp">Minhas Reservas</a>
      <a href="${ctx}/lista-quadras.jsp">Favoritos</a>
      <a href="${ctx}/login.jsp">Sair</a>
    </aside>
    <main class="card painel-perfil">
      <h2>Meu Perfil</h2>
      <form method="post" action="${ctx}/create-usuario">
        <div class="campo"><label>Nome completo</label><input type="text" name="nome" placeholder="Pedro Faro"></div>
        <div class="campo"><label>E-mail</label><input type="email" name="email" placeholder="seu@email.com"></div>
        <div class="campo"><label>Telefone</label><input type="text" name="telefone" placeholder="(11) 98765-4321"></div>
        <button class="btn largo" type="submit" style="margin-top:20px">Salvar alterações</button>
      </form>
    </main>
  </div>
</div>
<div class="rodape"></div>
</body>
</html>