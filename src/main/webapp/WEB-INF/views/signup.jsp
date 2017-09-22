<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
</head>
<body>

<a class="app-header" href="<c:url value="/"/>">Todo list app</a>

<div class="login-dialog">
    <form:form class="login-form" modelAttribute="user" action="signup" method="post">
        <form:label class="login-fields block" path="email">
            Email
            <form:input class="align-right" path="email"/>
            <form:errors class="errors" path="email"/>
        </form:label>
        <form:label class="login-fields block" path="username">
            Username
            <form:input class="align-right" path="username"/>
            <form:errors class="errors" path="username"/>
        </form:label>
        <form:label class="login-fields block" path="password">
            Password
            <form:password class="align-right" path="password"/>
            <form:errors class="errors" path="password"/>
        </form:label>
        <br>
        <form:button class="signup-btn">Create an account!</form:button>
    </form:form>
</div>

</body>
</html>