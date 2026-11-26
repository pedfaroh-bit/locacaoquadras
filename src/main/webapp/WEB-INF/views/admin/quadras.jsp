<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="menuAtivo" value="quadras"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Quadras - QuadraDas Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-topo.jspf" %>
<div class="pagina">
  <h1>Gerenciar Quadras</h1>
  <div class="migalha">QuadraDas / Gerenciar Quadras</div>
  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>
    <section class="painel">
      <h2>Gerenciar Quadras</h2>
      <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
      <form class="barra" method="get" action="${ctx}/admin/quadras">
        <div class="campo"><label for="q">Pesquisar...</label>
          <input id="q" type="text" name="q" value="<c:out value='${busca}'/>"></div>
        <a class="btn" href="${ctx}/admin/quadras?novo=1">Adicionar</a>
      </form>
      <c:if test="${not empty item}">
        <form class="form" method="post" action="${ctx}/admin/quadras">
          <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
          <input type="hidden" name="id" value="${item.idQuadra}">
          <div class="campo"><label for="nome">Nome</label>
            <input id="nome" name="nome" maxlength="100" required value="<c:out value='${item.nome}'/>"></div>
          <div class="campo"><label for="idModalidade">Modalidade</label>
            <select id="idModalidade" name="idModalidade" required>
              <option value="">Selecione</option>
              <c:forEach var="m" items="${modalidades}">
                <option value="${m.idModalidade}" ${m.idModalidade == item.idModalidade ? 'selected' : ''}><c:out value="${m.nome}"/></option>
              </c:forEach>
            </select></div>
          <div class="campo largo"><label for="endereco">Localização (bairro, cidade - UF)</label>
            <input id="endereco" name="endereco" maxlength="255" required value="<c:out value='${item.endereco}'/>"></div>
          <div class="campo largo"><label for="descricao">Estrutura (separe por vírgula)</label>
            <input id="descricao" name="descricao" maxlength="255" placeholder="Grama sintética, Iluminação LED, Vestiário"
                   value="<c:out value='${item.descricao}'/>"></div>
          <div class="campo check"><label><input type="checkbox" name="ativa" ${item.ativa ? 'checked' : ''}> Quadra ativa (aparece no site)</label></div>
          <div class="acoes"><button class="btn pequeno" type="submit">Salvar</button>
            <a class="acao" href="${ctx}/admin/quadras">Cancelar</a></div>
        </form>
      </c:if>

      <table>
        <thead><tr><th>Nome</th><th>Localização</th><th>Esporte</th><th>Status</th><th>Ações</th></tr></thead>
        <tbody>
        <c:forEach var="q" items="${quadras}">
          <tr>
            <td><c:out value="${q.nome}"/></td>
            <td><c:out value="${q.endereco}"/></td>
            <td><c:out value="${q.nomeModalidade}"/></td>
            <td class="${q.ativa ? 'st-ok' : 'st-erro'}">${q.ativa ? 'Ativa' : 'Inativa'}</td>
            <td class="acoes"><a class="acao" href="${ctx}/admin/quadras?editar=${q.idQuadra}">Editar</a> &nbsp;
<form class="inline" method="post" action="${ctx}/admin/quadras" onsubmit="return confirm('Excluir esta quadra?')"><input type="hidden" name="_csrf" value="${sessionScope.csrf}"><input type="hidden" name="acao" value="excluir"><input type="hidden" name="id" value="${q.idQuadra}"><button class="link-perigo" type="submit">Excluir</button></form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty quadras}"><tr><td class="vazio" colspan="5">Nenhuma quadra encontrada.</td></tr></c:if>
        </tbody>
      </table>
    </section>
  </div>
</div>
<div class="rodape"></div>
</body>
</html>
