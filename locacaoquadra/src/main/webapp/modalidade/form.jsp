<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${empty modalidade ? 'Nova modalidade' : 'Editar modalidade'} - QuadraDas</title>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="/WEB-INF/topo.jspf" %>

<main class="container pagina">

  <div class="form-card">
    <h1>${empty modalidade ? 'Nova modalidade' : 'Editar modalidade'}</h1>

    <form action="${pageContext.request.contextPath}/modalidades" method="post">
      <input type="hidden" name="id" value="${modalidade.idModalidade}">

      <div class="form-grupo">
        <label for="nome">Nome</label>
        <input type="text" id="nome" name="nome" maxlength="100" required
               value="<c:out value='${modalidade.nome}'/>">
      </div>

      <div class="form-grupo">
        <label for="precoSemana">Preço (semana) em R$</label>
        <input type="number" id="precoSemana" name="precoSemana" step="0.01" min="0" required
               value="${modalidade.precoSemana}">
      </div>

      <div class="form-grupo">
        <label for="precoFimdesemana">Preço (fim de semana) em R$</label>
        <input type="number" id="precoFimdesemana" name="precoFimdesemana" step="0.01" min="0" required
               value="${modalidade.precoFimdesemana}">
      </div>

      <div class="form-acoes">
        <button type="submit" class="btn">Salvar</button>
        <a href="${pageContext.request.contextPath}/modalidades" class="btn btn-secundario">Cancelar</a>
      </div>
    </form>
  </div>

</main>

</body>
</html>