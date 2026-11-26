<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="menuAtivo" value=""/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Login - QuadraDas</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-topo.jspf" %>

<div class="pagina">
  <h1>Login</h1>
  <div class="migalha">QuadraDas / Login</div>

  <form class="card auth" method="post" action="${ctx}/login">
    <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
    <h2>Faça login para continuar</h2>
    <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
    <c:if test="${param.esqueci == '1'}">
      <p class="msg msg-info">A recuperação de senha por e-mail ainda não está disponível. Fale com a administração para redefinir a sua.</p>
    </c:if>

    <div class="campo"><label for="login">E-mail ou telefone</label>
      <input id="login" type="text" name="login" placeholder="seu@email.com" required autofocus
             value="<c:out value='${loginDigitado}'/>"></div>
    <div class="campo"><label for="senha">Senha</label>
      <input id="senha" type="password" name="senha" placeholder="Digite sua senha" required></div>
    <button class="btn largo" type="submit" style="margin-top:10px">Entrar</button>
    <a class="link" href="${ctx}/login?esqueci=1">Esqueceu sua senha?</a>
    <p class="ou">ou</p>
    <a class="btn branco largo" href="${ctx}/cadastro">Criar uma conta</a>
    <hr style="border:0;border-top:1px solid var(--borda);margin:20px 0 14px">
    <p class="ou">É administrador? Use a entrada própria:</p>
    <a class="btn branco largo" href="${ctx}/admin/login">Entrar como administrador</a>
  </form>
</div>

<div class="rodape"></div>
</body>
</html>
