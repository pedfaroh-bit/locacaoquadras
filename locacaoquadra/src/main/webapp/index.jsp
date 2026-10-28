<<<<<<< HEAD
<html>
<body>
<h2>Hello World!</h2>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>QuadraDas</title>
  <!-- Importa a fonte Inter -->
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
  <!-- Arquivo CSS -->
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<!-- Cabeçalho / Topo -->
<header class="topo">
  <div class="logo">
    <div class="logo-icone">Q</div>
    <span>QuadraDas</span>
  </div>

  <nav class="menu">
    <a href="${pageContext.request.contextPath}/index.jsp" style="color: var(--azul);">Início</a>
    <a href="${pageContext.request.contextPath}/quadra/lista.jsp">Quadras</a>
    <a href="${pageContext.request.contextPath}/reservas/reservas.jsp">Reservas</a>
    <a href="#">Perfil</a>
  </nav>

  <a href="${pageContext.request.contextPath}/auth/login.jsp" class="btn">Entrar</a>
</header>

<!-- Conteúdo Principal -->
<main class="container">

  <!-- Hero (Busca) -->
  <section class="hero">
    <h1>Encontre a quadra ideal para o seu jogo</h1>
    <p>Reserve seu horário de forma rápida, simples e segura.</p>
  </section>

  <!-- Formulário de Busca com Cidade Fixa em São Paulo -->
  <form class="busca" action="${pageContext.request.contextPath}/quadra/lista.jsp" method="GET">
    <div class="campo">
      <label for="cidade">Cidade</label>
      <!-- Usando readonly para fixar o valor em São Paulo -->
      <input type="text" id="cidade" name="cidade" value="São Paulo" readonly style="background-color: #f1f5f9; cursor: not-allowed; color: var(--cinza);">
    </div>

    <div class="campo">
      <label for="data">Data</label>
      <input type="date" id="data" name="data">
    </div>

    <div class="campo">
      <label for="horario">Horário</label>
      <select id="horario" name="horario" style="width: 100%; height: 44px; padding: 0 12px; border: 1px solid var(--borda); border-radius: 8px; font-family: inherit; font-size: 13px; background: #fff;">
        <option value="">Selecione o horário</option>
        <option value="08:00">08:00</option>
        <option value="09:00">09:00</option>
        <option value="10:00">10:00</option>
        <option value="14:00">14:00</option>
        <option value="15:00">15:00</option>
        <option value="16:00">16:00</option>
        <option value="18:00">18:00</option>
        <option value="19:00">19:00</option>
        <option value="20:00">20:00</option>
        <option value="21:00">21:00</option>
        <option value="22:00">22:00</option>
      </select>
    </div>

    <button type="submit" class="btn">Buscar quadras</button>
  </form>

  <!-- Categorias -->
  <section class="categorias">
    <h2>Categorias</h2>

    <div class="grade">
      <a href="${pageContext.request.contextPath}/quadra/lista.jsp" class="card">
        <strong>Futebol</strong>
        <span>Ver quadras</span>
      </a>

      <a href="${pageContext.request.contextPath}/quadra/lista.jsp" class="card">
        <strong>Futsal</strong>
        <span>Ver quadras</span>
      </a>

      <a href="${pageContext.request.contextPath}/quadra/lista.jsp" class="card">
        <strong>FutVôlei</strong>
        <span>Ver quadras</span>
      </a>
    </div>
  </section>

</main>

>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)
</body>
</html>
