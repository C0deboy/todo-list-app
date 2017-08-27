<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>

<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">

    <meta name="theme-color" content="#0376a5">
    <meta name="msapplication-navbutton-color" content="#0376a5">
    <meta name="apple-mobile-web-app-status-bar-style" content="#0376a5">

    <title>Todo-list-app</title>

    <link rel="stylesheet" href="${contextPath}/resources/css/main.css" />
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Ubuntu" />

</head>
<body>

<a class="app-header" href="${contextPath}">Todo list app</a>

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

    <a class="signup-btn" href="signup" >Sign up!</a><a class="forgotPassword-btn" href="${contextPath}/forgotPassword" >Forgot password?</a>
</div>

</body>
</html>