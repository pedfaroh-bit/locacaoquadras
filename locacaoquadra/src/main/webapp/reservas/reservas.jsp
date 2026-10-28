<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuadraDas - Minhas Reservas</title>
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
        .btn { background: var(--azul); color: #fff; padding: 10px 24px; border-radius: 8px; text-decoration: none; font-weight: 600; font-size: 14px; border: none; cursor: pointer; transition: background 0.2s; display: inline-block; }
        .btn:hover { background: var(--azul-hover); }
        .container { max-width: 1200px; margin: 0 auto; padding: 40px 24px; }
        .lq-caminho { font-size: 12px; color: var(--cinza); margin-bottom: 8px; }
        .barra-acoes { margin-bottom: 32px; }
        .barra-acoes h1 { font-size: 28px; font-weight: 700; }
        .tabs { display: flex; gap: 24px; background: #fff; border: 1px solid var(--borda); border-radius: 12px; padding: 16px 24px; margin-bottom: 24px; font-weight: 600; font-size: 14px; }
        .tab-item { color: var(--cinza); cursor: pointer; }
        .tab-item.ativo { color: var(--azul); }
        .reserva-item { background: #fff; border: 1px solid var(--borda); border-radius: 12px; padding: 16px 24px; display: flex; align-items: center; justify-content: space-between; margin-bottom: 16px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .reserva-info { display: flex; gap: 16px; align-items: center; }
        .reserva-thumb { width: 120px; height: 72px; background: #e7eef8; border-radius: 8px; }
        .reserva-detalhes h3 { font-size: 16px; font-weight: 700; margin-bottom: 4px; }
        .reserva-detalhes span { font-size: 13px; color: var(--cinza); }
        .reserva-preco-status { display: flex; align-items: center; gap: 24px; }
        .status-confirmada { color: #10b981; font-weight: 700; font-size: 13px; }
        .lq-btn-branco { background: #fff; color: var(--texto); border: 1px solid var(--borda); border-radius: 8px; font-weight: 600; font-size: 13px; cursor: pointer; transition: all 0.2s; display: inline-block; text-align: center; }
        .lq-btn-branco:hover { border-color: var(--azul); color: var(--azul); }
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
    <div class="lq-caminho">QuadraDas / Minhas Reservas</div>
    <div class="barra-acoes">
        <h1>Minhas Reservas</h1>
    </div>

    <div class="tabs">
        <span class="tab-item ativo">Futuras (1)</span>
        <span class="tab-item">Passadas (6)</span>
    </div>

    <div>
        <div class="reserva-item">
            <div class="reserva-info">
                <div class="reserva-thumb"></div>
                <div class="reserva-detalhes">
                    <h3 id="reservaNome">PlayQuadra</h3>
                    <span id="reservaDetalhesData">14/04/2026 • 22:00–23:00</span>
                </div>
            </div>
            <div class="reserva-preco-status">
                <strong id="reservaPreco" style="font-size: 15px; color: var(--azul);">R$ 110,00</strong>
                <a href="#" id="linkVerDetalhes" class="lq-btn-branco" style="height: 38px; line-height: 36px; padding: 0 16px; text-decoration: none;">Ver detalhes</a>
                <span class="status-confirmada">Confirmada</span>
            </div>
        </div>
    </div>
</main>

<script>
    window.onload = function() {
        // Lê do localStorage a última reserva salva
        const dadosReserva = localStorage.getItem('ultimaReservaQuadraDas');

        let nome = "PlayQuadra";
        let preco = "R$ 110,00";
        let dataHora = "14/04/2026 • 22:00–23:00";

        if (dadosReserva) {
            const reservaObj = JSON.parse(dadosReserva);
            nome = reservaObj.nome;
            preco = reservaObj.preco;
            dataHora = reservaObj.dataHora;
        }

        document.getElementById('reservaNome').innerText = nome;
        document.getElementById('reservaPreco').innerText = preco;
        document.getElementById('reservaDetalhesData').innerText = dataHora;

        // Extrai o valor numérico do preço para passar no link
        const precoLimpo = preco.replace('R$ ', '').replace(',00', '').trim();
        const linkDetalhes = '${pageContext.request.contextPath}/quadra/detalhes.jsp?nome=' + encodeURIComponent(nome) + '&preco=' + precoLimpo;
        document.getElementById('linkVerDetalhes').setAttribute('href', linkDetalhes);
    };
</script>
</body>
</html>