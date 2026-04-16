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
  <title><fmt:message key="app.loginTitulo" /></title>
  <link rel="stylesheet" href="<c:url value='/assets/css/styles.css' />" />
</head>
<body>
  <div class="login-wrap">
    <div class="panel login-card">
      <div class="toolbar" style="margin-top:0;">
        <div>
          <h1><fmt:message key="app.loginTitulo" /></h1>
          <p class="muted"><fmt:message key="app.titulo" /></p>
        </div>
        <div class="toolbar-links">
          <a class="btn-link" href="<c:url value='/idioma?lang=es' />">Español</a>
          <a class="btn-link" href="<c:url value='/idioma?lang=en' />">English</a>
        </div>
      </div>

      <c:if test="${not empty errorLogin}">
        <div class="alert-error">${errorLogin}</div>
      </c:if>

      <form action="<c:url value='/login' />" method="post" class="stack">
        <label>
          <fmt:message key="app.usuario" />
          <input type="text" name="username" value="<c:out value='${username}' />" autocomplete="username" required />
        </label>

        <label>
          <fmt:message key="app.clave" />
          <input type="password" name="password" autocomplete="current-password" required />
        </label>

        <button type="submit" class="btn"><fmt:message key="app.iniciarSesion" /></button>
      </form>

      <p class="muted" style="margin-bottom:0; margin-top:14px;">admin / Admin123! | viewer / View456!</p>
    </div>
  </div>
</body>
</html>