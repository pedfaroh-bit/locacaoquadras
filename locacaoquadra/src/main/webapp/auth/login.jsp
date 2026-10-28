<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuadraDas - Login</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
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

    <div class="lq-caminho">QuadraDas / Login</div>

    <div class="barra-acoes">
        <h1>Login</h1>
    </div>

    <div class="form-card" style="margin: 0 auto; text-align: center; max-width: 500px; padding: 40px;">
        <h2 style="font-size: 22px; font-weight: 700; margin-bottom: 24px;">Faça login para continuar</h2>

        <form action="${pageContext.request.contextPath}/reservas/reservas.jsp" method="GET" style="text-align: left;">
            <div class="form-grupo">
                <label for="email">E-mail ou telefone</label>
                <input type="text" id="email" placeholder="seu@email.com">
            </div>

            <div class="form-grupo" style="margin-bottom: 24px;">
                <label for="senha">Senha</label>
                <input type="password" id="senha" placeholder="Digite sua senha">
            </div>

            <button type="submit" class="btn" style="width: 100%; height: 44px; margin-bottom: 16px;">Entrar</button>
        </form>

        <a href="#" style="font-size: 13px; color: var(--azul); font-weight: 600; display: block; margin-bottom: 24px;">Esqueceu sua senha?</a>

        <div style="font-size: 12px; color: var(--cinza); margin-bottom: 16px;">ou</div>

        <a href="${pageContext.request.contextPath}/auth/cadastro.jsp" class="lq-btn lq-btn-branco" style="display: flex; align-items: center; justify-content: center; width: 100%; text-decoration: none;">Criar uma conta</a>
    </div>

</main>

</body>
</html>