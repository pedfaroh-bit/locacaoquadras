<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>QuadraDas - Cadastro</title>
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

    <div class="lq-caminho">QuadraDas / Cadastro</div>

    <div class="barra-acoes">
        <h1>Cadastro</h1>
    </div>

    <div class="form-card" style="margin: 0 auto; text-align: center; max-width: 560px; padding: 40px;">
        <h2 style="font-size: 22px; font-weight: 700; margin-bottom: 24px;">Crie sua conta</h2>

        <!-- Mensagem de Erro Dinâmica -->
        <div id="mensagemErro" style="display: none; background: #fee2e2; border: 1px solid #f87171; color: #dc2626; padding: 12px; border-radius: 8px; font-size: 13px; font-weight: 600; margin-bottom: 20px; text-align: center;">
            As senhas não coincidem!
        </div>

        <form action="${pageContext.request.contextPath}/auth/login.jsp" method="GET" style="text-align: left;" onsubmit="return validarCadastro(event)">
            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px;">
                <div class="form-grupo">
                    <label for="nome">Nome completo</label>
                    <input type="text" id="nome" placeholder="Digite seu nome" required>
                </div>
                <div class="form-grupo">
                    <label for="email-cad">E-mail</label>
                    <input type="email" id="email-cad" placeholder="seu@email.com" required>
                </div>
            </div>

            <div style="display: grid; grid-template-columns: 1fr 1fr; gap: 16px;">
                <div class="form-grupo">
                    <label for="telefone">Telefone</label>
                    <input type="text" id="telefone" placeholder="(11) 98765-4321" required>
                </div>
                <div class="form-grupo">
                    <label for="senha-cad">Senha</label>
                    <input type="password" id="senha-cad" placeholder="Digite sua senha" required>
                </div>
            </div>

            <div class="form-grupo" style="margin-bottom: 24px;">
                <label for="confirma-senha">Confirmar senha</label>
                <input type="password" id="confirma-senha" placeholder="Confirme sua senha" required>
            </div>

            <button type="submit" class="btn" style="width: 100%; height: 44px; margin-bottom: 20px; cursor: pointer;">Criar conta</button>
        </form>

        <a href="${pageContext.request.contextPath}/auth/login.jsp" style="font-size: 13px; color: var(--azul); font-weight: 600; text-decoration: none;">Já tem uma conta? Entrar</a>
    </div>

</main>

<script>
    function validarCadastro(event) {
        const senha = document.getElementById('senha-cad').value;
        const confirmaSenha = document.getElementById('confirma-senha').value;
        const msgErro = document.getElementById('mensagemErro');

        if (senha !== confirmaSenha) {
            event.preventDefault(); // Impede o envio do formulário
            msgErro.innerText = "As senhas digitadas não coincidem!";
            msgErro.style.display = "block";
            return false;
        }

        if (senha.length < 6) {
            event.preventDefault();
            msgErro.innerText = "A senha deve ter pelo menos 6 caracteres!";
            msgErro.style.display = "block";
            return false;
        }

        msgErro.style.display = "none";
        return true;
    }
</script>

</body>
</html>