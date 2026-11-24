<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<c:set var="menuAtivo" value="inicio"/>
<%-- Ajuste se o @WebServlet da listagem de quadras for outro --%>
<c:set var="quadrasUrl" value="${ctx}/list-quadra"/>
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

<div class="pagina">
  <section class="hero">
    <h1>Encontre a quadra ideal para o seu jogo</h1>
    <p class="sub">Reserve seu horário de forma rápida, simples e segura.</p>
  </section>

  <form class="card busca-home" method="get" action="${quadrasUrl}">
    <div class="campo">
      <label for="cidade">Cidade ou bairro</label>
      <input id="cidade" type="text" name="cidade" placeholder="São Paulo">
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
    <a class="cat" href="${quadrasUrl}?modalidade=Futebol"><strong>Futebol</strong><span>Ver quadras</span></a>
    <a class="cat" href="${quadrasUrl}?modalidade=Futsal"><strong>Futsal</strong><span>Ver quadras</span></a>
    <a class="cat" href="${quadrasUrl}?modalidade=FutV%C3%B4lei"><strong>FutVôlei</strong><span>Ver quadras</span></a>
  </div>
</div>

<div class="rodape"></div>
</body>
</html>