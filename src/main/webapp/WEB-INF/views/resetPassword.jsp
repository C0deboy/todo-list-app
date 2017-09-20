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

<a class="app-header" href="/">Todo list app</a>

<div class="login-dialog">

    <c:if test="${not empty message}">
        <p>Change password</p>
        <hr>
        <br><br>
        <span class="message">${message}</span>
    </c:if>
    <c:if test="${empty message}">
        <p>Change password</p>
        <hr>
        <form:form class="login-form" modelAttribute="user" action="resetPassword" method="post">
            <form:hidden path="resetPasswordToken"/>
            <form:hidden path="username"/>
            Username: <input value="${user.username}" disabled/>
            <br><br>
            <form:label class="login-fields block" path="password">
                Enter your new password
                <form:password class="align-right" path="password"/>
                <form:errors class="errors" path="password"/>
            </form:label>

            <label class="login-fields block">
                Retype your new password
                <input type="password" name="retypedPassword"/>
            </label>
            <br>
            <form:button class="signup-btn">Change passsword</form:button>
        </form:form>
    </c:if>
</div>

</body>
</html>