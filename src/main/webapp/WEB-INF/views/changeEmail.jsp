<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

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
	<link href="https://maxcdn.bootstrapcdn.com/font-awesome/4.7.0/css/font-awesome.min.css" rel="stylesheet" integrity="sha384-wvfXpqpZZVQGK6TAh5PVlGOfQNHSoD2xbE+QkPxCAFlNEevoEH3Sl0sibVcOQVnN" crossorigin="anonymous">
    <link rel="stylesheet" type="text/css" href="//fonts.googleapis.com/css?family=Ubuntu" />

</head>
<body>

    <jsp:include page="/WEB-INF/templates/navbar.jsp" />



	<div class="app">
		<div class="settings">
            <h1>Manage your account</h1>
            <hr>

            <div class="user-details">
                <form:form class="login-form" modelAttribute="user" method="post" action="changeEmail">
					<form:label class="login-fields block" path="email">
						New email addres
						<form:input path="email"/>
						<form:errors class="errors" path="email"/>
					</form:label>

					<form:button class="settings-option">
						Change email address
					</form:button>
				</form:form>
            </div>
		</div>
	</div>
	
	<script src="<c:url value="/resources/js/todo-list.js" />"></script>

</body>
</html>