<<<<<<< HEAD
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
=======
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)
<!DOCTYPE html>
<html>
<head>
<<<<<<< HEAD
    <meta charset="UTF-8">
    <title>Quadras</title>
</head>
<body>
    <h1>Quadras</h1>
    <a href="${pageContext.request.contextPath}/quadras?acao=novo">Nova quadra</a>
    <table border="1">
        <tr>
            <th>ID</th>
            <th>Nome</th>
            <th>Endereço</th>
            <th>Descrição</th>
            <th>ID Modalidade</th>
            <th>Ações</th>
        </tr>
        <c:forEach items="${quadras}" var="q">
            <tr>
                <td>${q.idQuadra}</td>
                <td>${q.nome}</td>
                <td>${q.endereco}</td>
                <td>${q.descricao}</td>
                <td>${q.idModalidade}</td>
                <td>
                    <a href="${pageContext.request.contextPath}/quadras?acao=editar&id=${q.idQuadra}">Editar</a>
                    <a href="${pageContext.request.contextPath}/quadras?acao=excluir&id=${q.idQuadra}"
                       onclick="return confirm('Excluir esta quadra?');">Excluir</a>
                </td>
            </tr>
        </c:forEach>
    </table>
=======
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <title>QuadraDas - Lista de Quadras</title>
  <link rel="preconnect" href="https://fonts.googleapis.com">
  <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
  <style>
    .lq-card { cursor: pointer; }
  </style>
</head>
<body>

<header class="topo">
  <div class="logo">
    <div class="logo-icone">Q</div>
    <span>QuadraDas</span>
  </div>
  <nav class="menu">
    <a href="${pageContext.request.contextPath}/index.jsp">Início</a>
    <a href="${pageContext.request.contextPath}/quadra/lista.jsp" style="color: var(--azul);">Quadras</a>
    <a href="${pageContext.request.contextPath}/reservas/reservas.jsp">Reservas</a>
    <a href="#">Perfil</a>
  </nav>
  <a href="${pageContext.request.contextPath}/auth/login.jsp" class="btn">Entrar</a>
</header>

<main class="container pagina">

  <div class="lq-caminho">QuadraDas / Lista de Quadras</div>

  <div class="barra-acoes">
    <h1>Lista de Quadras</h1>
  </div>

  <form class="lq-busca" id="formBusca" onsubmit="filtrarQuadras(event);">
    <div class="lq-campo" style="flex: 1;">
      <label for="busca-geral">Buscar por nome, bairro ou cidade</label>
      <input type="text" id="busca-geral" placeholder="Ex: Centro, Arena, São Paulo..." onkeyup="filtrarQuadras(event)">
    </div>
    <div class="lq-acoes-busca">
      <button type="button" class="lq-btn lq-btn-branco" onclick="limparBusca()">Filtrar</button>
      <button type="submit" class="lq-btn lq-btn-azul">Buscar</button>
    </div>
  </form>

  <div class="lq-total" id="contadorQuadras">6 quadras encontradas</div>

  <div class="lq-grade" id="gradeQuadras">

    <div class="lq-card" onclick="irParaDetalhes('Quadra do Centro', '90')">
      <div class="lq-foto"></div>
      <div class="lq-info">
        <h2>Quadra do Centro</h2>
        <div class="lq-sub">Futebol Society • São Paulo</div>
        <div class="lq-desc">⭐ 4,7 R$ 90/h</div>
      </div>
    </div>

    <div class="lq-card" onclick="irParaDetalhes('Arena Sport', '100')">
      <div class="lq-foto"></div>
      <div class="lq-info">
        <h2>Arena Sport</h2>
        <div class="lq-sub">Futebol Society • São Paulo</div>
        <div class="lq-desc">⭐ 4,8 R$ 100/h</div>
      </div>
    </div>

    <div class="lq-card" onclick="irParaDetalhes('PlayQuadra', '110')">
      <div class="lq-foto"></div>
      <div class="lq-info">
        <h2>PlayQuadra</h2>
        <div class="lq-sub">Futebol Society • São Paulo</div>
        <div class="lq-desc">⭐ 4,9 R$ 110/h</div>
      </div>
    </div>

    <div class="lq-card" onclick="irParaDetalhes('Esporte Clube', '120')">
      <div class="lq-foto"></div>
      <div class="lq-info">
        <h2>Esporte Clube</h2>
        <div class="lq-sub">Futebol Society • São Paulo</div>
        <div class="lq-desc">⭐ 4,7 R$ 120/h</div>
      </div>
    </div>

    <div class="lq-card" onclick="irParaDetalhes('Arena Norte', '130')">
      <div class="lq-foto"></div>
      <div class="lq-info">
        <h2>Arena Norte</h2>
        <div class="lq-sub">Futebol Society • São Paulo</div>
        <div class="lq-desc">⭐ 4,8 R$ 130/h</div>
      </div>
    </div>

    <div class="lq-card" onclick="irParaDetalhes('Sport Center', '140')">
      <div class="lq-foto"></div>
      <div class="lq-info">
        <h2>Sport Center</h2>
        <div class="lq-sub">Futebol Society • São Paulo</div>
        <div class="lq-desc">⭐ 4,9 R$ 140/h</div>
      </div>
    </div>

  </div>

</main>

<script>
  function irParaDetalhes(nome, preco) {
    window.location.href = '${pageContext.request.contextPath}/quadra/detalhes.jsp?nome=' + encodeURIComponent(nome) + '&preco=' + preco;
  }

  function filtrarQuadras(event) {
    if (event) event.preventDefault();
    const termo = document.getElementById('busca-geral').value.toLowerCase().trim();
    const cards = document.querySelectorAll('.lq-card');
    let visiveis = 0;

    cards.forEach(card => {
      const textoCard = card.innerText.toLowerCase();
      if (textoCard.includes(termo)) {
        card.style.display = 'flex';
        visiveis++;
      } else {
        card.style.display = 'none';
      }
    });

    document.getElementById('contadorQuadras').innerText = visiveis + ' quadras encontradas';
  }

  function limparBusca() {
    document.getElementById('busca-geral').value = '';
    filtrarQuadras();
  }
</script>

>>>>>>> 87d0487 (Ajustes no fluxo de reservas, navegacao dinamica, tela de perfil e estilizacao)
</body>
</html>