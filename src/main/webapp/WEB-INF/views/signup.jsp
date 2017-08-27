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

    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Ubuntu" />

</head>
<body>

<a class="app-header" href="${contextPath}">Todo list app</a>

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