<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value=""/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Cadastro - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-menu.jspf" %>
<div class="pagina">
  <h1>Cadastro</h1>
  <div class="migalha">QuadraDas / Cadastro</div>

  <form class="card auth duplo" method="post" action="/create-usuario">
    <h2>Crie sua conta</h2>
    <div class="duas">
      <div class="campo"><label>Nome completo</label><input type="text" name="nome" placeholder="Digite seu nome" required></div>
      <div class="campo"><label>E-mail</label><input type="email" name="email" placeholder="seu@email.com" required></div>
      <div class="campo"><label>Telefone</label><input type="text" name="telefone" placeholder="(11) 98765-4321"></div>
      <div class="campo"><label>Senha</label><input type="password" name="senha" placeholder="Digite sua senha" required></div>
      <div class="campo"><label>Confirmar senha</label><input type="password" name="confirmarSenha" placeholder="Confirme sua senha" required></div>
    </div>
    <button class="btn largo" type="submit" style="margin-top:14px">Criar conta</button>
    <a class="link" style="margin-bottom:0" href="${ctx}/login.jsp">Já tem uma conta? Entrar</a>
  </form>
</div>
<div class="rodape"></div>
</body>
</html>