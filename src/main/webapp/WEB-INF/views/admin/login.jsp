<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Área do Administrador - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<header class="topo topo-admin">
  <a class="logo" href="${ctx}/admin"><b>Q</b>QuadraDas</a>
  <nav></nav>
  <a class="btn branco" href="${ctx}/inicio">Voltar ao site</a>
</header>

<div class="pagina">
  <h1>Área do Administrador</h1>
  <div class="migalha">QuadraDas / Admin / Login</div>

  <form class="card auth auth-admin" method="post" action="${ctx}/admin/login">
    <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
    <div class="centro-txt"><span class="faixa-admin">ACESSO RESTRITO</span></div>
    <h2>Entrar no painel</h2>
    <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
    <div class="campo"><label for="email">E-mail do administrador</label>
      <input id="email" type="email" name="email" required autofocus value="<c:out value='${emailDigitado}'/>"></div>
    <div class="campo"><label for="senha">Senha</label>
      <input id="senha" type="password" name="senha" required></div>
    <button class="btn largo" type="submit" style="margin-top:10px">Entrar</button>
    <p class="ou" style="margin-top:18px"><a href="${ctx}/login">Sou cliente</a></p>
  </form>
</div>
<div class="rodape"></div>
</body>
</html>
