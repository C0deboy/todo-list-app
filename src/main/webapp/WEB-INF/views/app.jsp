<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html lang="en">
<head>
	<jsp:include page="/WEB-INF/templates/head.jsp"/>
</head>
<body>
	<jsp:include page="/WEB-INF/templates/navbar.jsp"/>

	<div class="app">
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

				<input name="task" placeholder="Add new task"/>

				<button class="add-btn" name="addTask">
					<i class="fa fa-plus-square fa-lg"></i>
				</button>
			</form:form>
		</c:forEach>

		<form:form class="addListForm" modelAttribute="todoList" method="post" action="addList">
			<form:input path="name" placeholder="Add new todolist"/>
			<form:hidden path="ownerID"/>
			<button class="add-btn">
				<i class="fa fa-plus-square fa-lg"></i>
			</button>
		</form:form>
	</div>
	
	<script src="<c:url value="/resources/js/todo-list.js" />"></script>
</body>
</html>