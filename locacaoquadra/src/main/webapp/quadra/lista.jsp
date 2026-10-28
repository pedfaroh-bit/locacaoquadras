<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Quadras - QuadraDas</title>
  <link href="https://fonts.googleapis.com/css2?family=Inter:wght@400;600;700&display=swap" rel="stylesheet">
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>

<%@ include file="/WEB-INF/topo.jspf" %>

<main class="container pagina">

  <div class="barra-acoes">
    <h1>Quadras</h1>
    <a href="${pageContext.request.contextPath}/quadras?acao=novo" class="btn">+ Nova quadra</a>
  </div>

  <c:if test="${not empty erro}">
    <div class="alerta"><c:out value="${erro}"/></div>
  </c:if>

  <c:choose>
    <c:when test="${empty quadras}">
      <div class="vazio">Nenhuma quadra cadastrada ainda.</div>
    </c:when>
    <c:otherwise>
      <div class="tabela-wrap">
        <table class="tabela">
          <thead>
            <tr>
              <th>ID</th>
              <th>Nome</th>
              <th>Endereço</th>
              <th>Descrição</th>
              <th>Modalidade</th>
              <th>Ações</th>
            </tr>
          </thead>
          <tbody>
            <c:forEach items="${quadras}" var="q">
              <tr>
                <td>${q.idQuadra}</td>
                <td><c:out value="${q.nome}"/></td>
                <td><c:out value="${q.endereco}"/></td>
                <td><c:out value="${q.descricao}"/></td>
                <td><c:out value="${q.nomeModalidade}"/></td>
                <td class="acoes">
                  <a class="link-editar"
                     href="${pageContext.request.contextPath}/quadras?acao=editar&id=${q.idQuadra}">Editar</a>
                  <a class="link-excluir"
                     href="${pageContext.request.contextPath}/quadras?acao=excluir&id=${q.idQuadra}"
                     onclick="return confirm('Excluir esta quadra?');">Excluir</a>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </c:otherwise>
  </c:choose>

</main>

</body>
</html>