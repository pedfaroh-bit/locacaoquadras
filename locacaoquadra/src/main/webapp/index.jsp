<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>QuadraDas</title>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

  <header class="topo">
    <a href="${pageContext.request.contextPath}/" class="logo">
      <span class="logo-icone">Q</span>
      QuadraDas
    </a>

    <nav class="menu">
      <a href="${pageContext.request.contextPath}/">Início</a>
      <a href="${pageContext.request.contextPath}/quadras">Quadras</a>
      <a href="#">Reservas</a>
      <a href="#">Perfil</a>
    </nav>

    <a href="#" class="btn">Entrar</a>
  </header>

  <main class="container">

    <section class="hero">
      <h1>Encontre a quadra ideal para o seu jogo</h1>
      <p>Reserve seu horário de forma rápida, simples e segura.</p>
    </section>

    <form class="busca" action="#" method="get">
      <div class="campo">
        <label for="local">Cidade ou bairro</label>
        <input type="text" id="local" name="local" placeholder="São Paulo">
      </div>
      <div class="campo">
        <label for="data">Data</label>
        <input type="date" id="data" name="data">
      </div>
      <div class="campo">
        <label for="horario">Horário</label>
        <input type="time" id="horario" name="horario">
      </div>
      <button type="submit" class="btn">Buscar quadras</button>
    </form>

    <section class="categorias">
      <h2>Categorias</h2>
      <div class="grade">
        <a href="${pageContext.request.contextPath}/quadras" class="card"><strong>Futebol</strong><span>Ver quadras</span></a>
        <a href="${pageContext.request.contextPath}/quadras" class="card"><strong>Futsal</strong><span>Ver quadras</span></a>
        <a href="${pageContext.request.contextPath}/quadras" class="card"><strong>FutVôlei</strong><span>Ver quadras</span></a>
      </div>
    </section>

  </main>

</body>
</html>