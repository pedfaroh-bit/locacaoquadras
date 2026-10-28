<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuadraDas - Detalhes da Quadra</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
    <style>
        .horario-btn {
            border: 1px solid var(--azul);
            color: var(--azul);
            background: #fff;
            padding: 10px 20px;
            border-radius: 8px;
            font-weight: 600;
            font-size: 14px;
            cursor: pointer;
            transition: all 0.2s;
        }
        .horario-btn.selecionado {
            background: var(--azul) !important;
            color: #fff !important;
        }
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
        <a href="${pageContext.request.contextPath}/quadra/lista.jsp">Quadras</a>
        <a href="${pageContext.request.contextPath}/reservas/reservas.jsp">Reservas</a>
        <a href="#">Perfil</a>
    </nav>
    <a href="${pageContext.request.contextPath}/auth/login.jsp" class="btn">Entrar</a>
</header>

<main class="container pagina">

    <div class="lq-caminho">QuadraDas / Detalhes da Quadra</div>

    <div class="barra-acoes">
        <h1>Detalhes da Quadra</h1>
    </div>

    <div class="form-card" style="max-width: 100%; display: flex; gap: 24px; align-items: center; margin-bottom: 32px;">
        <div class="lq-foto" style="flex: 0 0 280px; height: 180px; border-radius: 12px;"></div>
        <div style="flex: 1;">
            <h2 id="tituloQuadra" style="font-size: 24px; font-weight: 700; margin-bottom: 6px;">Quadra do Centro</h2>
            <div class="lq-sub" style="margin-bottom: 12px;">Futebol Society • São Paulo, SP</div>
            <div style="font-size: 14px; font-weight: 600; color: #d97706; margin-bottom: 16px;">⭐ 4,8 (124 avaliações)</div>
            <div id="precoQuadra" style="font-size: 28px; font-weight: 700; color: var(--texto); margin-bottom: 20px;">R$ 120/h</div>
            <button type="button" onclick="irParaReserva()" class="btn">Reservar agora</button>
        </div>
    </div>

    <h2 style="font-size: 20px; font-weight: 700; margin-bottom: 16px;">Descrição e estrutura</h2>
    <div class="grade" style="margin-bottom: 40px;">
        <div class="card" style="height: 100px; align-items: flex-start; padding: 16px;">
            <strong>Grama sintética</strong>
            <span style="font-size: 12px;">Disponível para reservas</span>
        </div>
        <div class="card" style="height: 100px; align-items: flex-start; padding: 16px;">
            <strong>Iluminação LED</strong>
            <span style="font-size: 12px;">Disponível para reservas</span>
        </div>
        <div class="card" style="height: 100px; align-items: flex-start; padding: 16px;">
            <strong>Vestiário</strong>
            <span style="font-size: 12px;">Disponível para reservas</span>
        </div>
        <div class="card" style="height: 100px; align-items: flex-start; padding: 16px;">
            <strong>Estacionamento</strong>
            <span style="font-size: 12px;">Disponível para reservas</span>
        </div>
    </div>

    <h2 style="font-size: 20px; font-weight: 700; margin-bottom: 16px;">Horários disponíveis</h2>
    <div style="display: flex; gap: 12px;">
        <button type="button" class="horario-btn" onclick="selecionarHorario(this)">18:00</button>
        <button type="button" class="horario-btn" onclick="selecionarHorario(this)">19:00</button>
        <button type="button" class="horario-btn" onclick="selecionarHorario(this)">20:00</button>
        <button type="button" class="horario-btn" onclick="selecionarHorario(this)">21:00</button>
    </div>

</main>

<script>
    let horarioSelecionado = "18:00"; // Padrão
    let nomeQuadra = "Quadra do Centro";
    let precoQuadra = "120";

    // Lê os parâmetros transmitidos pela URL
    window.onload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('nome')) {
            nomeQuadra = urlParams.get('nome');
            document.getElementById('tituloQuadra').innerText = nomeQuadra;
        }
        if (urlParams.has('preco')) {
            precoQuadra = urlParams.get('preco');
            document.getElementById('precoQuadra').innerText = 'R$ ' + precoQuadra + '/h';
        }
    };

    // Permite ao utilizador clicar e selecionar um horário
    function selecionarHorario(elemento) {
        const botoes = document.querySelectorAll('.horario-btn');
        botoes.forEach(btn => btn.classList.remove('selecionado'));

        elemento.classList.add('selecionado');
        horarioSelecionado = elemento.innerText;
    }

    // Redireciona para a página de finalização/reserva
    function irParaReserva() {
        window.location.href = '${pageContext.request.contextPath}/reservas/realizar.jsp?nome=' + encodeURIComponent(nomeQuadra) + '&preco=' + precoQuadra + '&horario=' + horarioSelecionado;
    }
</script>

</body>
</html>