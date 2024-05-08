<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<c:import url="parts/header.jsp"/>
<html>
<body>
<div class="container">
    <form class="form-horizontal" action="login" method="post">
        <fieldset>

            <!-- Form Name -->
            <legend>Login form</legend>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="userLogin">Login</label>
                <div class="col-md-4">
                    <input id="userLogin"
                           name="login"
                           type="text"
                           placeholder="your login"
                           class="form-control input-md"
                           required=""
                           value="ZipL">
                </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="password">Password</label>
                <div class="col-md-4">
                    <input id="password"
                           name="password"
                           type="password"
                           placeholder="your password"
                           class="form-control input-md"
                           required=""
                           value="admin">
                </div>
            </div>

            <!-- Button -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="submit"></label>
                <div class="col-md-4">
                    <button id="submit" name="loginButton" class="btn btn-success">Войти</button>
                </div>
            </div>

        </fieldset>
    </form>
</div>
</body>
</html>
<%@include file="parts/footer.jsp" %>
