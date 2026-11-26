<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="menuAtivo" value="inicio"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>QuadraDas - Início</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/cliente-topo.jspf" %>

<div class="pagina">
  <section class="hero">
    <h1>Encontre a quadra ideal para o seu jogo</h1>
    <p class="sub">Reserve seu horário de forma rápida, simples e segura.</p>
  </section>

  <form class="card busca-home" method="get" action="${ctx}/quadras">
    <div class="campo">
      <label for="q">Cidade ou bairro</label>
      <input id="q" type="text" name="q" placeholder="São Paulo">
    </div>
    <div class="campo">
      <label for="data">Data</label>
      <input id="data" type="text" name="data" placeholder="Escolha a data"
             onfocus="this.type='date'" onblur="if(!this.value)this.type='text'">
    </div>
    <div class="campo">
      <label for="horario">Horário</label>
      <input id="horario" type="text" name="horario" placeholder="Escolha o horário"
             onfocus="this.type='time'" onblur="if(!this.value)this.type='text'">
    </div>
    <button class="btn" type="submit">Buscar quadras</button>
  </form>

  <h2 class="centro">Categorias</h2>
  <div class="cats">
    <c:forEach var="m" items="${modalidades}">
      <a class="cat" href="${ctx}/quadras?modalidade=${m.idModalidade}">
        <strong><c:out value="${m.nome}"/></strong><span>Ver quadras</span>
      </a>
    </c:forEach>
    <c:if test="${empty modalidades}">
      <p class="vazio">Nenhuma categoria cadastrada ainda.</p>
    </c:if>
  </div>
</div>

<div class="rodape"></div>
</body>
</html>
