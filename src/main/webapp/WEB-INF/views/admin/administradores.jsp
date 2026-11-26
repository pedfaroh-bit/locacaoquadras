<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="menuAtivo" value="administradores"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Administradores - QuadraDas Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-topo.jspf" %>
<div class="pagina">
  <h1>Gerenciar Administradores</h1>
  <div class="migalha">QuadraDas / Gerenciar Administradores</div>
  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>
    <section class="painel">
      <h2>Gerenciar Administradores</h2>
      <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
      <form class="barra" method="get" action="${ctx}/admin/administradores">
        <div class="campo"><label for="q">Pesquisar...</label>
          <input id="q" type="text" name="q" value="<c:out value='${busca}'/>"></div>
        <a class="btn" href="${ctx}/admin/administradores?novo=1">Adicionar</a>
      </form>
      <c:if test="${not empty item}">
        <form class="form" method="post" action="${ctx}/admin/administradores">
          <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
          <input type="hidden" name="id" value="${item.idAdministrador}">
          <div class="campo"><label for="nome">Nome</label>
            <input id="nome" name="nome" maxlength="100" required value="<c:out value='${item.nome}'/>"></div>
          <div class="campo"><label for="email">E-mail (usado no login)</label>
            <input id="email" type="email" name="email" maxlength="150" required value="<c:out value='${item.email}'/>"></div>
          <div class="campo largo"><label for="senha">Senha</label>
            <input id="senha" type="password" name="senha" autocomplete="new-password"
                   placeholder="${item.idAdministrador == 0 ? 'Mínimo de 6 caracteres' : 'Deixe em branco para manter'}"></div>
          <div class="acoes"><button class="btn pequeno" type="submit">Salvar</button>
            <a class="acao" href="${ctx}/admin/administradores">Cancelar</a></div>
        </form>
      </c:if>

      <table>
        <thead><tr><th>Nome</th><th>E-mail</th><th>Ações</th></tr></thead>
        <tbody>
        <c:forEach var="a" items="${administradores}">
          <tr>
            <td><c:out value="${a.nome}"/></td>
            <td><c:out value="${a.email}"/></td>
            <td class="acoes"><a class="acao" href="${ctx}/admin/administradores?editar=${a.idAdministrador}">Editar</a>
              <c:if test="${a.idAdministrador != sessionScope.adminLogado.idAdministrador}"> &nbsp;
<form class="inline" method="post" action="${ctx}/admin/administradores" onsubmit="return confirm('Excluir este administrador?')"><input type="hidden" name="_csrf" value="${sessionScope.csrf}"><input type="hidden" name="acao" value="excluir"><input type="hidden" name="id" value="${a.idAdministrador}"><button class="link-perigo" type="submit">Excluir</button></form>
              </c:if>
            </td>
          </tr>
        </c:forEach>
        </tbody>
      </table>
    </section>
  </div>
</div>
<div class="rodape"></div>
</body>
</html>
