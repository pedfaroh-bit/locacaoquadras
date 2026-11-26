<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="codigo" value="${requestScope['javax.servlet.error.status_code']}"/>
<c:set var="mensagemErro" value="${requestScope['javax.servlet.error.message']}"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>QuadraDas - Ops</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<header class="topo">
  <a class="logo" href="${ctx}/inicio"><b>Q</b>QuadraDas</a>
</header>
<div class="pagina">
  <div class="card auth">
    <h2>
      <c:choose>
        <c:when test="${codigo == 404}">Página não encontrada</c:when>
        <c:when test="${codigo == 403}">Acesso negado</c:when>
        <c:otherwise>Algo deu errado</c:otherwise>
      </c:choose>
    </h2>
    <p class="sub" style="text-align:center;margin-bottom:22px">
      <c:choose>
        <c:when test="${codigo == 403 and not empty mensagemErro}"><c:out value="${mensagemErro}"/></c:when>
        <c:when test="${codigo == 404}">O endereço que você tentou abrir não existe.</c:when>
        <c:otherwise>Tente novamente em alguns instantes.</c:otherwise>
      </c:choose>
    </p>
    <a class="btn largo" href="${ctx}/inicio">Voltar ao início</a>
  </div>
</div>
<div class="rodape"></div>
</body>
</html>
