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
			<c:if test="${not empty message}">
				<span class="message normal-position">${message}</span>
			</c:if>
            <div class="user-details">
                <p>Username : ${user.username}</p>
                <span>Your email : ${user.email}</span> <a class="signup-btn" href="settings/changeEmail">Change <i class="fa fa-pencil-square-o fa-lg align-right"></i></a>
                <a class="settings-option" href="settings/changePassword">
                    Change password
                    <i class="fa fa-pencil-square-o fa-lg align-right"></i>
                </a>
                <a class="settings-option red" href="settings/deleteUser">
                    Remove account
                    <i class="fa fa-trash-o fa-lg align-right"></i>
                </a>
            </div>
		</div>
	</div>
	
	<script src="<c:url value="/resources/js/todo-list.js" />"></script>

</body>
</html>