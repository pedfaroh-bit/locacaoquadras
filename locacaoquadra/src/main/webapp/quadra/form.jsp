<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Formulário de Quadra</title>
</head>
<body>
    <h1>${empty quadra ? 'Nova quadra' : 'Editar quadra'}</h1>
    <form action="${pageContext.request.contextPath}/quadras" method="post">
        <input type="hidden" name="idQuadra" value="${quadra.idQuadra}">
        <p>Nome: <input type="text" name="nome" value="${quadra.nome}" required></p>
        <p>Endereço: <input type="text" name="endereco" value="${quadra.endereco}" required></p>
        <p>Descrição: <textarea name="descricao">${quadra.descricao}</textarea></p>
        <p>ID da modalidade: <input type="number" name="idModalidade" value="${quadra.idModalidade}" required></p>
        <button type="submit">Salvar</button>
        <a href="${pageContext.request.contextPath}/quadras">Cancelar</a>
    </form>
</body>
</html>