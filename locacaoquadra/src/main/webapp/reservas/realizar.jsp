<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuadraDas - Realizar Reserva</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        :root { --azul: #2563eb; --azul-hover: #1d4ed8; --texto: #0f172a; --cinza: #64748b; --borda: #e2e8f0; --fundo: #f8fafc; }
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', sans-serif; }
        body { background-color: var(--fundo); color: var(--texto); }
        .topo { display: flex; justify-content: space-between; align-items: center; padding: 20px 48px; background: #fff; border-bottom: 1px solid var(--borda); }
        .logo { display: flex; align-items: center; gap: 12px; font-weight: 700; font-size: 18px; }
        .logo-icone { width: 36px; height: 36px; background: var(--azul); color: #fff; display: flex; align-items: center; justify-content: center; border-radius: 8px; font-weight: 700; }
        .menu { display: flex; gap: 32px; font-weight: 600; font-size: 14px; }
        .menu a { text-decoration: none; color: var(--cinza); transition: color 0.2s; }
        .menu a:hover { color: var(--azul); }
        .btn { background: var(--azul); color: #fff; padding: 10px 24px; border-radius: 8px; text-decoration: none; font-weight: 600; font-size: 14px; border: none; cursor: pointer; transition: background 0.2s; display: inline-block; text-align: center; }
        .btn:hover { background: var(--azul-hover); }
        .container { max-width: 1200px; margin: 0 auto; padding: 40px 24px; }
        .lq-caminho { font-size: 12px; color: var(--cinza); margin-bottom: 8px; }
        .barra-acoes { margin-bottom: 32px; }
        .barra-acoes h1 { font-size: 28px; font-weight: 700; }
        .form-card { background: #fff; border: 1px solid var(--borda); border-radius: 12px; padding: 24px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .cal-grid { display: grid; grid-template-columns: repeat(7, 1fr); gap: 8px; margin-top: 16px; }
        .cal-btn { height: 40px; border: 1px solid var(--borda); border-radius: 8px; background: #fff; font-weight: 600; cursor: pointer; transition: all 0.2s; }
        .cal-btn:hover { border-color: var(--azul); }
        .cal-btn.ativo { background: var(--azul); color: #fff; border-color: var(--azul); }
        .horarios-grid { display: grid; grid-template-columns: 1fr 1fr; gap: 12px; margin-bottom: 24px; }
        .horario-btn { height: 44px; border: 1px solid var(--borda); border-radius: 8px; background: #fff; font-size: 14px; font-weight: 600; cursor: pointer; transition: all 0.2s; }
        .horario-btn.ativo { border-color: var(--azul); color: var(--azul); background: #f0f7ff; }
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
        <a href="${pageContext.request.contextPath}/reservas/reservas.jsp" style="color: var(--azul);">Reservas</a>
        <a href="${pageContext.request.contextPath}/perfil.jsp">Perfil</a>
    </nav>
    <a href="${pageContext.request.contextPath}/auth/login.jsp" class="btn">Entrar</a>
</header>

<main class="container">
    <div class="lq-caminho">QuadraDas / Realizar Reserva</div>
    <div class="barra-acoes">
        <h1>Realizar Reserva</h1>
    </div>

    <div class="form-card" style="margin-bottom: 32px;">
        <h2 id="nomeQuadraExibicao" style="font-size: 18px; font-weight: 700; margin-bottom: 4px;">Quadra do Centro</h2>
        <div style="font-size: 13px; color: var(--cinza);">Futebol Society • São Paulo, SP</div>
    </div>

    <h2 style="font-size: 20px; font-weight: 700; margin-bottom: 20px;">1. Escolha data e horário</h2>

    <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 32px; align-items: start; margin-bottom: 32px;">
        <div class="form-card">
            <h3 style="font-size: 16px; font-weight: 700; margin-bottom: 12px;">Abril 2026</h3>
            <div style="font-size: 12px; color: var(--cinza); font-weight: 700;">Calendário</div>
            <div class="cal-grid">
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">1</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">2</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">3</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">4</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">5</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">6</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">7</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">8</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">9</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">10</button>
                <button type="button" class="cal-btn ativo" onclick="selecionarDia(this)">11</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">12</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">13</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">14</button>
                <button type="button" class="cal-btn" onclick="selecionarDia(this)">15</button>
            </div>
        </div>

        <div>
            <h3 style="font-size: 16px; font-weight: 700; margin-bottom: 16px;">Horários</h3>
            <div class="horarios-grid">
                <button type="button" class="horario-btn" onclick="selecionarHorarioReserva(this)">18:00</button>
                <button type="button" class="horario-btn" onclick="selecionarHorarioReserva(this)">19:00</button>
                <button type="button" class="horario-btn ativo" onclick="selecionarHorarioReserva(this)">20:00</button>
                <button type="button" class="horario-btn" onclick="selecionarHorarioReserva(this)">21:00</button>
                <button type="button" class="horario-btn" onclick="selecionarHorarioReserva(this)">22:00</button>
            </div>

            <div class="form-card" style="display: flex; justify-content: space-between; align-items: center;">
                <span style="font-size: 13px; color: var(--cinza);">Total da reserva</span>
                <strong id="valorTotalReserva" style="font-size: 20px; color: var(--azul);">R$ 120,00</strong>
            </div>
        </div>
    </div>

    <button type="button" onclick="confirmarReserva()" class="btn" style="width: 100%; height: 48px; font-size: 15px;">Confirmar reserva</button>
</main>

<script>
    let nomeQuadra = "Quadra do Centro";
    let precoQuadra = "120";
    let diaEscolhido = "11";
    let horarioEscolhido = "20:00";

    window.onload = function() {
        const urlParams = new URLSearchParams(window.location.search);
        if (urlParams.has('nome')) {
            nomeQuadra = urlParams.get('nome');
            document.getElementById('nomeQuadraExibicao').innerText = nomeQuadra;
        }
        if (urlParams.has('preco')) {
            precoQuadra = urlParams.get('preco');
            document.getElementById('valorTotalReserva').innerText = 'R$ ' + precoQuadra + ',00';
        }
        if (urlParams.has('horario')) {
            horarioEscolhido = urlParams.get('horario');
            document.querySelectorAll('.horario-btn').forEach(b => {
                if (b.innerText === horarioEscolhido) b.classList.add('ativo');
                else b.classList.remove('ativo');
            });
        }
    };

    function selecionarDia(el) {
        document.querySelectorAll('.cal-btn').forEach(b => b.classList.remove('ativo'));
        el.classList.add('ativo');
        diaEscolhido = el.innerText;
    }

    function selecionarHorarioReserva(el) {
        document.querySelectorAll('.horario-btn').forEach(b => b.classList.remove('ativo'));
        el.classList.add('ativo');
        horarioEscolhido = el.innerText;
    }

    function confirmarReserva() {
        const dataCompleta = diaEscolhido.padStart(2, '0') + '/04/2026';
        const horaPartes = horarioEscolhido.split(':');
        const horaFim = (parseInt(horaPartes[0]) + 1).toString().padStart(2, '0') + ':' + horaPartes[1];
        const horarioCompleto = horarioEscolhido + '–' + horaFim;

        // Salva os dados no localStorage para a página de reservas ler
        const novaReserva = {
            nome: nomeQuadra,
            preco: 'R$ ' + precoQuadra + ',00',
            dataHora: dataCompleta + ' • ' + horarioCompleto
        };
        localStorage.setItem('ultimaReservaQuadraDas', JSON.stringify(novaReserva));

        // Redireciona para Minhas Reservas
        window.location.href = '${pageContext.request.contextPath}/reservas/reservas.jsp';
    }
</script>
</body>
</html>