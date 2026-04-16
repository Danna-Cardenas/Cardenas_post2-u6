<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setLocale value="${sessionScope.locale != null ? sessionScope.locale : 'en'}" />
<fmt:setBundle basename="messages" />
<!DOCTYPE html>
<html lang="es">
<head>
  <meta charset="UTF-8" />
  <meta name="viewport" content="width=device-width, initial-scale=1.0" />
  <title><fmt:message key="app.formTitulo" /></title>
  <link rel="stylesheet" href="<c:url value='/assets/css/styles.css' />" />
</head>
<body>
  <div class="page-shell">
    <section class="brand-card">
      <div class="toolbar" style="margin-top:0;">
        <div>
          <h1><fmt:message key="app.formTitulo" /></h1>
          <p class="muted">${sessionScope.usuarioActual.username}</p>
        </div>
        <div class="toolbar-actions">
          <a class="btn-secondary" href="<c:url value='/productos' />"><fmt:message key="menu.lista" /></a>
          <a class="btn-link" href="<c:url value='/idioma?lang=es' />">Español</a>
          <a class="btn-link" href="<c:url value='/idioma?lang=en' />">English</a>
        </div>
      </div>
    </section>

    <section class="panel">
      <c:if test="${not empty errores}">
        <div class="alert-error">
          <ul style="margin:0; padding-left:18px;">
            <c:forEach var="e" items="${errores}">
              <li>${e.value}</li>
            </c:forEach>
          </ul>
        </div>
      </c:if>

      <form action="<c:url value='/productos' />" method="post" class="stack">
        <input type="hidden" name="accion" value="guardar" />
        <input type="hidden" name="id" value="${not empty id ? id : producto.id}" />

        <div class="grid-two">
          <label>
            <fmt:message key="tabla.nombre" />
            <input type="text" name="nombre" value="<c:out value='${not empty nombre ? nombre : producto.nombre}' />" class="${not empty errores.nombre ? 'input-error' : ''}" maxlength="100" />
            <c:if test="${not empty errores.nombre}">
              <span class="campo-error">${errores.nombre}</span>
            </c:if>
          </label>

          <label>
            <fmt:message key="tabla.categoria" />
            <input type="text" name="categoria" value="<c:out value='${not empty categoria ? categoria : producto.categoria}' />" class="${not empty errores.categoria ? 'input-error' : ''}" />
            <c:if test="${not empty errores.categoria}">
              <span class="campo-error">${errores.categoria}</span>
            </c:if>
          </label>
        </div>

        <div class="grid-two">
          <label>
            <fmt:message key="tabla.precio" />
            <input type="text" name="precio" value="<c:out value='${not empty precio ? precio : producto.precio}' />" class="${not empty errores.precio ? 'input-error' : ''}" />
            <c:if test="${not empty errores.precio}">
              <span class="campo-error">${errores.precio}</span>
            </c:if>
          </label>

          <label>
            <fmt:message key="tabla.stock" />
            <input type="text" name="stock" value="<c:out value='${not empty stock ? stock : producto.stock}' />" class="${not empty errores.stock ? 'input-error' : ''}" />
            <c:if test="${not empty errores.stock}">
              <span class="campo-error">${errores.stock}</span>
            </c:if>
          </label>
        </div>

        <div class="actions-row">
          <button type="submit" class="btn"><fmt:message key="btn.guardar" /></button>
          <a class="btn-secondary" href="<c:url value='/productos' />"><fmt:message key="btn.cancelar" /></a>
        </div>
      </form>
    </section>
  </div>
</body>
</html>