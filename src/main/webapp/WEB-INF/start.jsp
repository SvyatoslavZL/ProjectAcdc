<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="head.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Пролог и знакомство</title>
</head>

<body>
<div class="container">
    <form class="form-horizontal" action="game" method="post">
        <%--        <form class="form-horizontal">--%>
        <fieldset>
            <h1>Добро пожаловать!</h1>

            <p>${sessionScope.spaceQuestStart}</p>

            <!-- Name input-->
            <div class="form-group">
                <label class="col-md-4 control-label" for="userName">Представьтесь</label>
                <div class="col-md-4">
                    <input id="userName" name="userName" type="text"
                           placeholder="введите своё имя"
                           class="form-control input-md"
                           required=""
                           value="Ratiost">
                </div>
            </div>
            <br/>

            <!-- Button -->
            <%--            <a href="game">Представиться</a>--%>
            <div class="form-group">
                <label class="col-md-4 control-label" for="submit"></label>
                <div class="col-md-4">
                    <button id="submit" name="submitButton" class="btn btn-primary">Представиться</button>
                </div>
            </div>

        </fieldset>
    </form>
</div>

</body>
</html>
