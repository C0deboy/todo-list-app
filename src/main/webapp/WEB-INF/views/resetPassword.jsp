<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page contentType="text/html; charset=UTF-8" %>

<!doctype html>
<html lang="en">
<head>
    <jsp:include page="/WEB-INF/templates/head.jsp"/>
</head>
<body>

<a class="app-header" href="<c:url value="/"/>">Todo list app</a>

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