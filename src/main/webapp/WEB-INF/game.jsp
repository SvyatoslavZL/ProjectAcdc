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
    <form class="form-horizontal" action="game" method="post">

        <fieldset>
            <legend>${requestScope.questionText}</legend>

            <div class="form-group">
                <div class="col-md-4">
                    <c:forEach var="answer" items="${sessionScope.answers}">
                        <div class="radio">
                            <label for="option-${answer.id}">
                                <input type="radio" name="option" id="option-${answer.id}" value="${answer.id}">
                                    ${answer.text}
                            </label>
                        </div>
                    </c:forEach>
                </div>
            </div>
            <br/>


            <div class="form-group">
                <label class="col-md-4 control-label" for="answerButton"></label>
                <div class="col-md-4">
                    <button type="submit" name="answerButton" id="answerButton" class="btn btn-primary">
                        <c:if test="${sessionScope.showRestartButton}">
                            Рестарт
                        </c:if>
                        <c:if test="${!sessionScope.showRestartButton}">
                            Ответить
                        </c:if>
                    </button>
                </div>
            </div>
        </fieldset>
    </form>
</div>
</body>
</html>
