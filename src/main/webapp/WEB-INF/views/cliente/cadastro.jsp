<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="menuAtivo" value=""/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Cadastro - QuadraDas</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-topo.jspf" %>

<div class="pagina">
  <h1>Cadastro</h1>
  <div class="migalha">QuadraDas / Cadastro</div>

  <form class="card auth duplo" method="post" action="${ctx}/cadastro">
    <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
    <h2>Crie sua conta</h2>
    <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
    <div class="duas">
      <div class="campo"><label for="nome">Nome completo</label>
        <input id="nome" type="text" name="nome" maxlength="100" placeholder="Digite seu nome" required
               value="<c:out value='${usuarioForm.nome}'/>"></div>
      <div class="campo"><label for="email">E-mail</label>
        <input id="email" type="email" name="email" maxlength="150" placeholder="seu@email.com" required
               value="<c:out value='${usuarioForm.email}'/>"></div>
      <div class="campo"><label for="telefone">Telefone</label>
        <input id="telefone" type="text" name="telefone" maxlength="20" placeholder="(11) 98765-4321"
               value="<c:out value='${usuarioForm.telefone}'/>"></div>
      <div class="campo"><label for="senha">Senha</label>
        <input id="senha" type="password" name="senha" minlength="6" autocomplete="new-password" placeholder="Mínimo de 6 caracteres" required></div>
      <div class="campo"><label for="confirmarSenha">Confirmar senha</label>
        <input id="confirmarSenha" type="password" name="confirmarSenha" autocomplete="new-password" placeholder="Confirme sua senha" required></div>
    </div>
    <button class="btn largo" type="submit" style="margin-top:14px">Criar conta</button>
    <a class="link" style="margin-bottom:0" href="${ctx}/login">Já tem uma conta? Entrar</a>
  </form>
</div>

<div class="rodape"></div>
</body>
</html>
