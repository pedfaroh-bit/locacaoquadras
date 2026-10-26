<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Modalidade</title>
</head>
<body>
    <h1>${empty modalidade ? 'Nova modalidade' : 'Editar modalidade'}</h1>
    <form method="post" action="${pageContext.request.contextPath}/modalidades">
        <input type="hidden" name="id" value="${modalidade.idModalidade}">
        <p>
            <label>Nome:</label>
            <input type="text" name="nome" value="${modalidade.nome}" required>
        </p>
        <p>
            <label>Preço semana:</label>
            <input type="text" name="precoSemana" value="${modalidade.precoSemana}" required>
        </p>
        <p>
            <label>Preço fim de semana:</label>
            <input type="text" name="precoFimdesemana" value="${modalidade.precoFimdesemana}" required>
        </p>
        <button type="submit">Salvar</button>
        <a href="${pageContext.request.contextPath}/modalidades">Voltar</a>
    </form>
</body>
</html>