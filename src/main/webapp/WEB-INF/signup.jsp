<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="parts/header.jsp" %>
<html>
<body>
<div class="container">
    <form class="form-horizontal" action="signup" method="post">
        <fieldset>

            <!-- Form Name -->
            <legend>Create user</legend>

            <!-- Text input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="login">Login</label>
                <div class="col-md-4">
                    <input id="login"
                           name="login"
                           type="text"
                           placeholder=""
                           class="form-control input-md"
                           required="">
                    <span class="help-block">min 4 symbols</span>
                </div>
            </div>

            <!-- Password input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="password">Password</label>
                <div class="col-md-4">
                    <input id="password"
                           name="password"
                           type="password"
                           placeholder=""
                           class="form-control input-md"
                           required="">
                    <span class="help-block">min 8 symbols</span>
                </div>
            </div>

            <!-- Select Basic -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="role">Role</label>
                <div class="col-md-4">
                    <select id="role" name="role" class="form-control">
                        <c:forEach var="role" items="${applicationScope.roles}">
                            <option value="${role}" ${role == requestScope.user.role ? "selected" : ""}>${role}</option>
                        </c:forEach>
                    </select>
                </div>
            </div>

            <!-- Button -->
            <div class="form-group">
                <label class="col-md-4 control-label" for="createButton"></label>
                <div class="col-md-8">
                        <button id="createButton" name="create" class="btn btn-primary">Sign-up</button>
                </div>
            </div>

        </fieldset>
    </form>
</div>
</body>
</html>
<%@include file="parts/footer.jsp" %>