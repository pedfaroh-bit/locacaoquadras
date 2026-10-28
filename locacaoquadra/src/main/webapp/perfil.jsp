<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuadraDas - Meu Perfil</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <style>
        :root {
            --azul: #2563eb;
            --azul-hover: #1d4ed8;
            --texto: #0f172a;
            --cinza: #64748b;
            --borda: #e2e8f0;
            --fundo: #f8fafc;
        }
        * { box-sizing: border-box; margin: 0; padding: 0; font-family: 'Inter', sans-serif; }
        body { background-color: var(--fundo); color: var(--texto); }

        .topo { display: flex; justify-content: space-between; align-items: center; padding: 20px 48px; background: #fff; border-bottom: 1px solid var(--borda); }
        .logo { display: flex; align-items: center; gap: 12px; font-weight: 700; font-size: 18px; }
        .logo-icone { width: 36px; height: 36px; background: var(--azul); color: #fff; display: flex; align-items: center; justify-content: center; border-radius: 8px; font-weight: 700; }
        .menu { display: flex; gap: 32px; font-weight: 600; font-size: 14px; }
        .menu a { text-decoration: none; color: var(--cinza); transition: color 0.2s; }
        .menu a:hover, .menu a.ativo { color: var(--azul); }
        .btn { background: var(--azul); color: #fff; padding: 10px 24px; border-radius: 8px; text-decoration: none; font-weight: 600; font-size: 14px; border: none; cursor: pointer; transition: background 0.2s; }
        .btn:hover { background: var(--azul-hover); }

        .container { max-width: 1200px; margin: 0 auto; padding: 40px 24px; }
        .lq-caminho { font-size: 12px; color: var(--cinza); margin-bottom: 8px; }
        .barra-acoes { display: flex; justify-content: space-between; align-items: center; margin-bottom: 32px; }
        .barra-acoes h1 { font-size: 28px; font-weight: 700; }

        .layout-perfil { display: grid; grid-template-columns: 260px 1fr; gap: 32px; align-items: start; }

        .menu-perfil { background: #fff; border: 1px solid var(--borda); border-radius: 12px; padding: 24px 16px; display: flex; flex-direction: column; gap: 12px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .item-menu-perfil { text-decoration: none; color: var(--texto); font-size: 14px; font-weight: 600; padding: 12px 16px; border-radius: 8px; transition: all 0.2s; }
        .item-menu-perfil:hover, .item-menu-perfil.ativo { background: #f0f7ff; color: var(--azul); }
        .item-menu-perfil.sair { color: #ef4444; margin-top: 12px; border-top: 1px solid var(--borda); padding-top: 16px; }
        .item-menu-perfil.sair:hover { background: #fef2f2; }

        .form-card { background: #fff; border: 1px solid var(--borda); border-radius: 12px; padding: 32px; box-shadow: 0 1px 3px rgba(0,0,0,0.05); }
        .form-grupo { margin-bottom: 20px; }
        .form-grupo label { display: block; font-size: 13px; font-weight: 600; color: var(--texto); margin-bottom: 8px; }
        .form-grupo input { width: 100%; height: 44px; padding: 0 16px; border: 1px solid var(--borda); border-radius: 8px; font-size: 14px; outline: none; transition: border-color 0.2s; }
        .form-grupo input:focus { border-color: var(--azul); }
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
        <a href="${pageContext.request.contextPath}/perfil.jsp" class="ativo">Perfil</a>
    </nav>
    <a href="${pageContext.request.contextPath}/auth/login.jsp" class="btn">Entrar</a>
</header>

<main class="container">

    <div class="lq-caminho">QuadraDas / Meu Perfil</div>

    <div class="barra-acoes">
        <h1>Meu Perfil</h1>
    </div>

    <div class="layout-perfil">

        <div class="menu-perfil">
            <a href="${pageContext.request.contextPath}/perfil.jsp" class="item-menu-perfil ativo">Meu Perfil</a>
            <a href="${pageContext.request.contextPath}/reservas/reservas.jsp" class="item-menu-perfil">Minhas Reservas</a>
            <a href="#" class="item-menu-perfil">Favoritos</a>
            <a href="${pageContext.request.contextPath}/auth/login.jsp" class="item-menu-perfil sair">Sair</a>
        </div>

        <div class="form-card">
            <h2 style="font-size: 20px; font-weight: 700; margin-bottom: 24px;">Meu Perfil</h2>

            <form onsubmit="salvarPerfil(event)">
                <div class="form-grupo">
                    <label for="nome">Nome completo</label>
                    <input type="text" id="nome" value="Pedro Faro" required>
                </div>

                <div class="form-grupo">
                    <label for="email">E-mail</label>
                    <input type="email" id="email" value="seu@email.com" required>
                </div>

                <div class="form-grupo" style="margin-bottom: 28px;">
                    <label for="telefone">Telefone</label>
                    <input type="text" id="telefone" value="(11) 98765-4321" required>
                </div>

                <button type="submit" class="btn" style="width: 100%; height: 46px; font-size: 15px; cursor: pointer;">Salvar alterações</button>
            </form>
        </div>

    </div>

</main>

<script>
    function salvarPerfil(e) {
        e.preventDefault();
        alert("Alterações salvas com sucesso!");
    }
</script>

</body>
</html>