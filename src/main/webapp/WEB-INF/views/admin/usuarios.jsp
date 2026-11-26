<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<c:set var="menuAtivo" value="usuarios"/>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
  <meta charset="UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1">
  <title>Gerenciar Usuários - QuadraDas Admin</title>
  <link rel="stylesheet" href="${pageContext.request.contextPath}/css/style.css">
</head>
<body>
<%@ include file="/WEB-INF/jspf/admin-topo.jspf" %>
<div class="pagina">
  <h1>Gerenciar Usuários</h1>
  <div class="migalha">QuadraDas / Gerenciar Usuários</div>
  <div class="admin">
    <%@ include file="/WEB-INF/jspf/admin-lateral.jspf" %>
    <section class="painel">
      <h2>Gerenciar Usuários</h2>
      <%@ include file="/WEB-INF/jspf/mensagens.jspf" %>
      <form class="barra" method="get" action="${ctx}/admin/usuarios">
        <div class="campo"><label for="q">Pesquisar...</label>
          <input id="q" type="text" name="q" value="<c:out value='${busca}'/>"></div>
        <a class="btn" href="${ctx}/admin/usuarios?novo=1">Adicionar</a>
      </form>
      <c:if test="${not empty item}">
        <form class="form" method="post" action="${ctx}/admin/usuarios">
          <input type="hidden" name="_csrf" value="${sessionScope.csrf}">
          <input type="hidden" name="id" value="${item.idUsuario}">
          <div class="campo"><label for="nome">Nome completo</label>
            <input id="nome" name="nome" maxlength="100" required value="<c:out value='${item.nome}'/>"></div>
          <div class="campo"><label for="email">E-mail</label>
            <input id="email" type="email" name="email" maxlength="150" required value="<c:out value='${item.email}'/>"></div>
          <div class="campo"><label for="telefone">Telefone</label>
            <input id="telefone" name="telefone" maxlength="20" value="<c:out value='${item.telefone}'/>"></div>
          <div class="campo"><label for="senha">Senha</label>
            <input id="senha" type="password" name="senha" autocomplete="new-password"
                   placeholder="${item.idUsuario == 0 ? 'Mínimo de 6 caracteres' : 'Deixe em branco para manter'}"></div>
          <div class="campo check"><label><input type="checkbox" name="ativo" ${item.ativo ? 'checked' : ''}> Usuário ativo (pode entrar no site)</label></div>
          <div class="acoes"><button class="btn pequeno" type="submit">Salvar</button>
            <a class="acao" href="${ctx}/admin/usuarios">Cancelar</a></div>
        </form>
      </c:if>

      <table>
        <thead><tr><th>Nome</th><th>E-mail</th><th>Tipo</th><th>Status</th><th>Ações</th></tr></thead>
        <tbody>
        <c:forEach var="u" items="${usuarios}">
          <tr>
            <td><c:out value="${u.nome}"/></td>
            <td><c:out value="${u.email}"/></td>
            <td>Cliente</td>
            <td class="${u.ativo ? 'st-ok' : 'st-erro'}">${u.ativo ? 'Ativo' : 'Inativo'}</td>
            <td class="acoes"><a class="acao" href="${ctx}/admin/usuarios?editar=${u.idUsuario}">Editar</a> &nbsp;
<form class="inline" method="post" action="${ctx}/admin/usuarios" onsubmit="return confirm('Excluir este usuário?')"><input type="hidden" name="_csrf" value="${sessionScope.csrf}"><input type="hidden" name="acao" value="excluir"><input type="hidden" name="id" value="${u.idUsuario}"><button class="link-perigo" type="submit">Excluir</button></form>
            </td>
          </tr>
        </c:forEach>
        <c:if test="${empty usuarios}"><tr><td class="vazio" colspan="5">Nenhum usuário encontrado.</td></tr></c:if>
        </tbody>
      </table>
    </section>
  </div>
</div>
<div class="rodape"></div>
</body>
</html>
