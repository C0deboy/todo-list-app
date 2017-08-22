<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">

    <meta name="theme-color" content="#0376a5">
    <meta name="msapplication-navbutton-color" content="#0376a5">
    <meta name="apple-mobile-web-app-status-bar-style" content="#0376a5">

    <title>Todo-list-app</title>

    <link rel="stylesheet" href="<c:url value="/resources/css/main.css" />" />
    <link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Ubuntu" />

</head>
<body>

<h1 class="app-haeder">Todo list app</h1>

<div class="login-dialog">
    <c:if test="${not empty message}"><div class="center"><span class="message">${message}</span></div></c:if>

    <form:form class="login-form" modelAttribute="user" action="logIn" method="post">
        <form:label class="login-fields" path="login">
            Login
            <form:input path="login"/>
            <form:errors class="errors" path="login"/>
        </form:label>
        <form:label class="login-fields" path="password">
            Password
            <form:password path="password"/>
            <form:errors class="errors" path="password"/>
        </form:label>
        <form:button class="logIn-btn" href="logIn" >Log in!</form:button>
    </form:form>
    <a class="signUp-btn" href="signUp" >Sign up!</a><a class="forgotPassword-btn" href="forgotPassword" >Forgot password?</a>
</div>

</body>
</html>