<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value=""/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Login - QuadraDas</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-menu.jspf" %>
<div class="pagina">
  <h1>Login</h1>
  <div class="migalha">QuadraDas / Login</div>

  <form class="card auth" method="post" action="${ctx}/login">
  <c:if test="${not empty erro}">
      <p style="color:red">${erro}</p>
  </c:if>

    <h2>Faça login para continuar</h2>
    <div class="campo"><label>E-mail ou telefone</label><input type="text" name="login" placeholder="seu@email.com" required></div>
    <div class="campo"><label>Senha</label><input type="password" name="senha" placeholder="Digite sua senha" required></div>
    <button class="btn largo" type="submit" style="margin-top:10px">Entrar</button>
    <a class="link" href="${ctx}/cadastro.jsp">Esqueceu sua senha?</a>
    <p class="ou">ou</p>
    <a class="btn branco largo" href="${ctx}/cadastro.jsp">Criar uma conta</a>
  </form>
</div>
<div class="rodape"></div>
</body>
</html>