<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>${empty quadra ? 'Nova quadra' : 'Editar quadra'} - QuadraDas</title>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="/WEB-INF/topo.jspf" %>

<main class="container pagina">

  <div class="form-card">
    <h1>${empty quadra ? 'Nova quadra' : 'Editar quadra'}</h1>

    <c:if test="${empty modalidades}">
      <div class="alerta">
        Cadastre pelo menos uma modalidade antes de criar quadras.
        <a href="${pageContext.request.contextPath}/modalidades?acao=novo"><strong>Criar modalidade</strong></a>
      </div>
    </c:if>

    <form action="${pageContext.request.contextPath}/quadras" method="post">
      <input type="hidden" name="idQuadra" value="${quadra.idQuadra}">

      <div class="form-grupo">
        <label for="nome">Nome</label>
        <input type="text" id="nome" name="nome" maxlength="100" required
               value="<c:out value='${quadra.nome}'/>">
      </div>

      <div class="form-grupo">
        <label for="endereco">Endereço</label>
        <input type="text" id="endereco" name="endereco" maxlength="255" required
               value="<c:out value='${quadra.endereco}'/>">
      </div>

      <div class="form-grupo">
        <label for="descricao">Descrição</label>
        <textarea id="descricao" name="descricao" rows="3" maxlength="255"><c:out value="${quadra.descricao}"/></textarea>
      </div>

      <div class="form-grupo">
        <label for="idModalidade">Modalidade</label>
        <select id="idModalidade" name="idModalidade" required>
          <option value="">Selecione...</option>
          <c:forEach items="${modalidades}" var="m">
            <option value="${m.idModalidade}"
              <c:if test="${quadra.idModalidade == m.idModalidade}">selected</c:if>>
              <c:out value="${m.nome}"/>
            </option>
          </c:forEach>
        </select>
      </div>

      <div class="form-acoes">
        <button type="submit" class="btn">Salvar</button>
        <a href="${pageContext.request.contextPath}/quadras" class="btn btn-secundario">Cancelar</a>
      </div>
    </form>
  </div>

</main>

</body>
</html>