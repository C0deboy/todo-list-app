<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
</head>
<body>

<a class="app-header" href="<c:url value="/"/>">Todo list app</a>

<div class="login-dialog">
    <span class="message absolute-off">${message}</span>
</div>

</body>
</html>