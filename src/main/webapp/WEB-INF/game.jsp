<%@ page contentType="text/html;charset=UTF-8" %>
<%@ include file="head.jsp" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>game</title>
</head>
<body>
<div class="container">
    <form class="form-horizontal">
        <fieldset>
            <legend>Ты потерял память. Принять вызов НЛО?</legend>

            <p>${sessionScope.userName} уже выиграл? - ${requestScope.win}</p>
            <c:forEach var="question" items="${sessionScope.spaceQuest.questions}">
                <p>${question.getText()}</p>
            </c:forEach>

            <!-- Multiple Options -->
            <%--        <div class="form-group">--%>
            <%--            <div class="col-md-4">--%>
            <%--                <div class="radio">--%>
            <%--                    <label for="option-0">--%>
            <%--                        <input type="radio" name="option" id="option-0" value="1" checked="checked">--%>
            <%--                        Принять вызов--%>
            <%--                    </label>--%>
            <%--                </div>--%>
            <%--                <div class="radio">--%>
            <%--                    <label for="option-1">--%>
            <%--                        <input type="radio" name="option" id="option-1" value="2">--%>
            <%--                        Отклонить вызов--%>
            <%--                    </label>--%>
            <%--                </div>--%>
            <%--            </div>--%>
            <%--        </div>--%>
            <%--        <br/>--%>

            <!-- Button -->
            <%--        <div class="form-group">--%>
            <%--            <label class="col-md-4 control-label" for="answerButton"></label>--%>
            <%--            <div class="col-md-4">--%>
            <%--                <button type="submit" name="answerButton" id="answerButton" class="btn btn-primary">Ответить</button>--%>
            <%--            </div>--%>
            <%--        </div>--%>

            <!-- Restart Button -->
            <%--        <button type="button" class="restart-button" onclick="restart()">Ответить</button>--%>

        </fieldset>
    </form>
</div>

<script>

    function restart() {
        $.ajax({
            url: '/restart',
            type: 'POST',
            async: false,
            success: function () {
                location.reload();
            }
        });
    }

    function answer(action) {
        $.ajax({
            type: 'POST',
            url: 'game',
            data: {action: action},
            success: function () {
                location.reload();
            }
        });
    }

</script>
</body>
</html>
