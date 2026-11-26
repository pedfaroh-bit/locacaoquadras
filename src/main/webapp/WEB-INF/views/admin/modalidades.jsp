<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="menuAtivo" value="modalidades"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Modalidades - QuadraDas Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-topo.jspf" %>
<div class="pagina">
  <h1>Gerenciar Modalidades</h1>
  <div class="migalha">QuadraDas / Gerenciar Modalidades</div>
  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>
    <section class="painel">
      <h2>Gerenciar Modalidades</h2>
      <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
      <form class="barra" method="get" action="${ctx}/admin/modalidades">
        <div class="campo"><label for="q">Pesquisar...</label>
          <input id="q" type="text" name="q" value="<c:out value='${busca}'/>"></div>
        <a class="btn" href="${ctx}/admin/modalidades?novo=1">Adicionar</a>
      </form>
      <c:if test="${not empty item}">
        <form class="form" method="post" action="${ctx}/admin/modalidades">
          <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
          <input type="hidden" name="id" value="${item.idModalidade}">
          <div class="campo largo"><label for="nome">Nome</label>
            <input id="nome" name="nome" maxlength="100" required value="<c:out value='${item.nome}'/>"></div>
          <div class="campo"><label for="precoSemana">Preço por hora - segunda a sexta (R$)</label>
            <input id="precoSemana" name="precoSemana" required inputmode="decimal" placeholder="120,00" value="<c:out value='${precoSemanaTxt}'/>"></div>
          <div class="campo"><label for="precoFimdesemana">Preço por hora - sábado e domingo (R$)</label>
            <input id="precoFimdesemana" name="precoFimdesemana" required inputmode="decimal" placeholder="150,00" value="<c:out value='${precoFimTxt}'/>"></div>
          <div class="acoes"><button class="btn pequeno" type="submit">Salvar</button>
            <a class="acao" href="${ctx}/admin/modalidades">Cancelar</a></div>
        </form>
      </c:if>

      <table>
        <thead><tr><th>Nome</th><th>Seg a sex</th><th>Sáb e dom</th><th>Ações</th></tr></thead>
        <tbody>
        <c:forEach var="m" items="${modalidades}">
          <tr>
            <td><c:out value="${m.nome}"/></td>
            <td>${m.precoSemanaFormatado}</td>
            <td>${m.precoFimdesemanaFormatado}</td>
            <td class="acoes"><a class="acao" href="${ctx}/admin/modalidades?editar=${m.idModalidade}">Editar</a> &nbsp;
<form class="inline" method="post" action="${ctx}/admin/modalidades" onsubmit="return confirm('Excluir esta modalidade?')"><input type="hidden" name="_csrf" value="${sessionScope.csrf}"><input type="hidden" name="acao" value="excluir"><input type="hidden" name="id" value="${m.idModalidade}"><button class="link-perigo" type="submit">Excluir</button></form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty modalidades}"><tr><td class="vazio" colspan="4">Nenhuma modalidade encontrada.</td></tr></c:if>
        </tbody>
      </table>
    </section>
  </div>
</div>
<div class="rodape"></div>
</body>
</html>
