<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html lang="en">
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
<nav class="navbar">
	<a class="app-header mini" href="<c:url value="/"/>">Todo list app</a>

	<div class="user-options-container">
		<button class="toggle-user-options-btn">${user.username}  <i class="fa fa-user" aria-hidden="true"></i></button>

		<ul class="user-options">
			<li><a href="settings">Settings <i class="fa fa-cog align-right" aria-hidden="true"></i></a></li>
			<li><a href="<c:url value="/logout"/>">Logout <i class="fa fa-sign-out align-right" aria-hidden="true"></i></a></li>
		</ul>
	</div>

</nav>
	<c:forEach var="list" items="${todoLists}">
		<form:form class="todo-list" modelAttribute="todoList" method="post" action="manageTodoList">
            <form:hidden path="id" value="${list.id}"/>
            <form:hidden path="ownerID"/>

			<div class="list-title">
                <form:input class="name" path="name" value="${list.name}" readonly="true"/>

				<div class="buttons">
					<button class="edit-btn" type="button" name="changeListName">
						<i class="fa fa-pencil-square-o fa-lg"></i>
					</button>
					<button class="delete-btn" name="deleteList">
						<i class="fa fa-trash-o fa-lg"></i>
					</button>
				</div>
			</div>
			
			<ul class="tasks">
				<c:forEach var="task" items="${list.tasks}" varStatus="loop">
                    <li class="task <c:if test="${task.done == 1}">done</c:if>">
						<c:set var="i" value="${loop.index}"/>
						<form:hidden path="tasks[${i}].id" value="${task.id}"/>
						<form:hidden path="tasks[${i}].done" value="${task.done}"/>

                        <button class="toggleDone-btn" name="toggleDone" value="${i}">
                            <i class="fa fa<c:if test="${task.done == 1}">-check</c:if>-square-o fa-lg"></i>
                        </button>

                        <form:input class="name" path="tasks[${i}].task" value="${task.task}" readonly="true"/>

                        <div class="buttons">
                            <button class="edit-btn" type="button" name="changeTaskName" value="${i}">
                                <i class="fa fa-pencil-square-o fa-lg"></i>
                            </button>

                            <button class="delete-btn" name="deleteTask" value="${task.id}">
                                <i class="fa fa-trash-o fa-lg"></i>
                            </button>
                        </div>
                    </li>
				</c:forEach>
			</ul>

            <input name="task" placeholder="Dodaj nowe zadanie"/>

            <button class="add-btn" name="addTask">
                <i class="fa fa-plus-square fa-lg"></i>
            </button>
		</form:form>
	</c:forEach>

	<form:form class="addListForm" modelAttribute="todoList" method="post" action="addList">
		<form:input path="name" placeholder="Dodaj nową listę"/>
        <form:hidden path="ownerID"/>
		<button class="add-btn">
			<i class="fa fa-plus-square fa-lg"></i>
		</button>
	</form:form>
	
	<script src="<c:url value="/resources/js/todo-list.js" />"></script>
</body>
</html>