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
  <title><fmt:message key="app.titulo" /></title>
  <link rel="stylesheet" href="<c:url value='/assets/css/styles.css' />" />
</head>
<body>
  <div class="page-shell">
    <section class="brand-card">
      <div class="toolbar" style="margin-top:0;">
        <div>
          <h1><fmt:message key="app.titulo" /></h1>
          <p class="muted"><fmt:message key="app.bienvenida" />, ${sessionScope.usuarioActual.username}</p>
        </div>
        <div class="toolbar-actions">
          <a class="btn-secondary" href="<c:url value='/productos?accion=nuevo' />"><fmt:message key="menu.nuevo" /></a>
          <a class="btn-link" href="<c:url value='/idioma?lang=es' />">Español</a>
          <a class="btn-link" href="<c:url value='/idioma?lang=en' />">English</a>
          <a class="btn-link" href="<c:url value='/logout' />"><fmt:message key="app.cerrarSesion" /></a>
        </div>
      </div>
    </section>

    <section class="panel">
      <c:if test="${not empty mensaje}">
        <div class="alert-success">${mensaje}</div>
      </c:if>

      <div class="toolbar" style="margin-top:0;">
        <div>
          <h2><fmt:message key="app.productos" /></h2>
          <p class="muted">${sessionScope.usuarioActual.email} · ${sessionScope.usuarioActual.rol}</p>
        </div>
      </div>

      <div class="table-wrap">
        <table>
          <thead>
            <tr>
              <th><fmt:message key="tabla.id" /></th>
              <th><fmt:message key="tabla.nombre" /></th>
              <th><fmt:message key="tabla.categoria" /></th>
              <th><fmt:message key="tabla.precio" /></th>
              <th><fmt:message key="tabla.stock" /></th>
              <th><fmt:message key="tabla.acciones" /></th>
            </tr>
          </thead>
          <tbody>
            <c:forEach var="producto" items="${productos}">
              <tr>
                <td>${producto.id}</td>
                <td>${producto.nombre}</td>
                <td>${producto.categoria}</td>
                <td>$${producto.precio}</td>
                <td>${producto.stock}</td>
                <td>
                  <div class="toolbar-links">
                    <a class="btn-link" href="<c:url value='/productos?accion=editar&id=${producto.id}' />"><fmt:message key="btn.editar" /></a>
                    <form action="<c:url value='/productos' />" method="post" style="display:inline; margin:0;">
                      <input type="hidden" name="accion" value="eliminar" />
                      <input type="hidden" name="id" value="${producto.id}" />
                      <button type="submit" class="btn-danger" onclick="return confirm('¿Eliminar este producto?');"><fmt:message key="btn.eliminar" /></button>
                    </form>
                  </div>
                </td>
              </tr>
            </c:forEach>
          </tbody>
        </table>
      </div>
    </section>
  </div>
</body>
</html>