<%@ page contentType="text/html;charset=UTF-8" %>
<%@include file="head.jsp" %>
<html>
<head>
    <title>Пролог и знакомство</title>
</head>

<body>

<%--<h1><%= "Hello Space!"%>--%>
<%--</h1>--%>
<%--<br/>--%>
<%--<a href="index-servlet">Index Servlet</a>--%>

<form class="form-horizontal">
    <fieldset>

        <!-- Form Name -->
        <legend>Квест</legend>

        <!-- Textarea -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="prologue">Пролог</label>
            <div class="col-md-4">
                <textarea class="form-control" id="prologue" name="prologue">текст пролога</textarea>
            </div>
        </div>

        <!-- Textarea -->
        <div class="form-group">
            <label class="col-md-4 control-label" for="welcome">Знакомство с экипажем</label>
            <div class="col-md-4">
                <textarea class="form-control" id="welcome" name="welcome">знакомство</textarea>
            </div>
        </div>

        <!-- Name input-->
        <div class="form-group">
            <label class="col-md-4 control-label" for="name">Представьтесь</label>
            <div class="col-md-4">
                <input id="name"
                       name="name"
                       type="text"
                       placeholder="имя"
                       class="form-control input-md"
                       required="">
                <span class="help-block">введите своё имя</span>
            </div>
        </div>

        <!-- Button -->
        <div class="form-group">
            <div class="col-md-4">
                <form action="game" method="get">
                    <button type="submit">Перейти на сервлет</button>
                </form>
<%--                <button id="introButton" name="introButton" class="btn btn-primary" href="game">Представиться</button>--%>
            </div>
        </div>
    </fieldset>
</form>

</body>
</html>
