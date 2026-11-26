<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="menuAtivo" value="perfil"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Meu Perfil - QuadraDas</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-topo.jspf" %>

<div class="pagina">
  <h1>Meu Perfil</h1>
  <div class="migalha">QuadraDas / Meu Perfil</div>

  <div class="perfil">
    <nav class="card menu-perfil">
      <a class="ativo" href="${ctx}/perfil">Meu Perfil</a>
      <a href="${ctx}/minhas-reservas">Minhas Reservas</a>
      <a href="${ctx}/logout">Sair</a>
    </nav>

    <div class="card painel-perfil">
      <h2>Meu Perfil</h2>
      <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
      <form method="post" action="${ctx}/perfil">
        <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
        <div class="campo"><label for="nome">Nome completo</label>
          <input id="nome" type="text" name="nome" maxlength="100" required value="<c:out value='${perfil.nome}'/>"></div>
        <div class="campo"><label for="email">E-mail</label>
          <input id="email" type="email" name="email" maxlength="150" required value="<c:out value='${perfil.email}'/>"></div>
        <div class="campo"><label for="telefone">Telefone</label>
          <input id="telefone" type="text" name="telefone" maxlength="20" placeholder="(11) 98765-4321" value="<c:out value='${perfil.telefone}'/>"></div>
        <div class="campo"><label for="novaSenha">Nova senha</label>
          <input id="novaSenha" type="password" name="novaSenha" autocomplete="new-password" placeholder="Deixe em branco para manter a atual">
          <small>Mínimo de 6 caracteres.</small></div>
        <div class="campo"><label for="confirmarSenha">Confirmar nova senha</label>
          <input id="confirmarSenha" type="password" name="confirmarSenha" autocomplete="new-password"></div>
        <button class="btn largo" type="submit">Salvar alterações</button>
      </form>
    </div>
  </div>
</div>

<div class="rodape"></div>
</body>
</html>
