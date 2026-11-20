<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="inicio"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>QuadraDas - Início</title>
  <link rel="stylesheet" href="${ctx}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-menu.jspf" %>

<main class="pagina">
  <section class="hero">
    <h1>Encontre a quadra ideal para o seu jogo</h1>
    <p class="sub">Reserve seu horário de forma rápida, simples e segura.</p>
  </section>

  <form class="card busca-home" method="get" action="${ctx}/lista-quadras">
    <div class="campo">
      <label for="cidade">Cidade ou bairro</label>
      <input id="cidade" type="text" name="cidade" placeholder="São Paulo">
    </div>
    <div class="campo">
      <label for="data">Data</label>
      <input id="data" type="date" name="data">
    </div>
    <div class="campo">
      <label for="hora">Horário</label>
      <input id="hora" type="time" name="hora">
    </div>
    <button class="btn" type="submit">Buscar quadras</button>
  </form>

  <h2 class="centro">Categorias</h2>

  <section class="cats">
    <c:forEach var="m" items="${modalidades}">
      <a class="cat" href="${ctx}/lista-quadras?modalidade=${m.idModalidade}">
        <strong><c:out value="${m.nome}"/></strong>
        <span>Ver quadras</span>
      </a>
    </c:forEach>
    <c:if test="${empty modalidades}">
      <p class="vazio" style="grid-column:1/-1;text-align:center">Nenhuma modalidade cadastrada ainda.</p>
    </c:if>
  </section>
</main>

<div class="rodape"></div>
</body>
</html>