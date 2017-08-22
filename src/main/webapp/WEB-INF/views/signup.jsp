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
    <form:form class="login-form" modelAttribute="user" action="signUp" method="post">
        <form:label class="login-fields block" path="email">
            Email
            <form:input class="align-right" path="email"/>
            <form:errors class="errors" path="email"/>
        </form:label>
        <form:label class="login-fields block" path="login">
            Login
            <form:input class="align-right" path="login"/>
            <form:errors class="errors" path="login"/>
        </form:label>
        <form:label class="login-fields block" path="password">
            Password
            <form:password class="align-right" path="password"/>
            <form:errors class="errors" path="password"/>
        </form:label>
        <br>
        <form:button class="signUp-btn">Create an account!</form:button>
    </form:form>
</div>

</body>
</html>