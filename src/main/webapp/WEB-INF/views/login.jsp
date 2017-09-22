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
    <c:if test="${not empty message}"><span class="message">${message}</span></c:if>

    <form:form class="login-form" modelAttribute="user" action="login" method="post">
        <form:label class="login-fields" path="username">
            Username
            <form:input path="username"/>
            <form:errors class="errors" path="username"/>
        </form:label>
        <form:label class="login-fields" path="password">
            Password
            <form:password path="password"/>
            <form:errors class="errors" path="password"/>
        </form:label>
        <label class="remember-me">Remember me
            <input type="checkbox" name="remember-me"/>
        </label>
        <form:button class="login-btn" href="login" >Log in!</form:button>
    </form:form>

    <a class="signup-btn" href="<c:url value="/signup"/>" >Sign up!</a><a class="forgotPassword-btn" href="<c:url value="/forgotPassword"/>" >Forgot password?</a>
</div>

</body>
</html>