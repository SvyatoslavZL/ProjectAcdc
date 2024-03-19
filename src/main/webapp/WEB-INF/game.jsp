<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="head.jsp" %>
<html>
<head>
    <title>page1</title>
</head>
<body>
<form class="form-horizontal">
    <fieldset>
        <legend>Ты потерял память. Принять вызов НЛО?</legend>

        <!-- Multiple Options -->
        <div class="form-group">
            <div class="col-md-4">
                <div class="radio">
                    <label for="options-0">
                        <input type="radio" name="options" id="options-0" value="1" checked="checked">
                        Принять вызов
                    </label>
                </div>
                <div class="radio">
                    <label for="options-1">
                        <input type="radio" name="options" id="options-1" value="2">
                        Отклонить вызов
                    </label>
                </div>
            </div>
        </div>
        <br/>



        <!-- Button -->
        <div class="form-group">
            <div class="col-md-4">
                <c:if test="${win == true}">
                    <button type="button" class="restart-button" onclick="restart()">Ответить</button>
                </c:if>
<%--                если первый вариант, то ответить с первым параметром, если второй, то со вторым--%>
<%--                <c:if test=""> --%>
<%--                    <button type="button" class="restart-button" onclick="restart()">Ответить</button>--%>
<%--                </c:if>--%>
                <button type="button" class="confirm-button" onclick="">Ответить</button>
            </div>
        </div>

    </fieldset>
</form>
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
