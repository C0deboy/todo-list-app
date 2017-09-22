<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html lang="en">
<head>
	<jsp:include page="/WEB-INF/templates/head.jsp"/>
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