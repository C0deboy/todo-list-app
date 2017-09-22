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
    <p>Forgot password</p>
    <hr><br><br>
    <c:if test="${not empty message}"><span class="message">${message}</span></c:if>

    <form:form class="login-form" modelAttribute="user" action="forgotPassword" method="post">
        <form:label class="login-fields block" path="email">
            Enter your email
            <form:input class="align-right" path="email"/>
            <form:errors class="errors" path="email"/>
        </form:label>
        <br>
        <form:button class="signup-btn">Send reset passsword link</form:button>
    </form:form>
</div>

</body>
</html>