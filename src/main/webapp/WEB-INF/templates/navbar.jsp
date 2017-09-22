<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav class="navbar">
    <a class="app-header mini" href="<c:url value="/"/>">Todo list app</a>
    
    <c:set var="username" value="${user.username}"/>

    <div class="user-options-container">
        <button class="toggle-user-options-btn">${username} <i class="fa fa-user" aria-hidden="true"></i></button>

        <ul class="user-options">
            <li><a href="<c:url value="/${username}/your-todo-lists"/>">Your todolists <i class="fa fa-list" aria-hidden="true"></i></a></li>
            <li><a href="<c:url value="/${username}/settings"/>">Settings <i class="fa fa-cog align-right" aria-hidden="true"></i></a></li>
            <li><a href="<c:url value="/logout"/>">Logout <i class="fa fa-sign-out align-right" aria-hidden="true"></i></a></li>
        </ul>
    </div>
</nav>