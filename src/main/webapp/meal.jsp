<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <style>
        input{margin-left: 3%}

        form  input{
                   float: inherit;
                   margin-left: 10px;
                   margin-bottom: 5%;
                    position: relative;


            }
    </style>
    <title>Meal</title>
</head>
<body>
    <jsp:useBean id="meal" scope="request" type="ru.javawebinar.topjava.model.Meal"/>
    <form action="meals" method="post">
        <input type="hidden" name="id" value="<c:out value="${meal.id}"/>"><br/>
            <label>Description:
            <input type="text" name="description" value="<c:out value="${meal.description}"/>"/>
            </label><br/>
        <label>Calories:
            <input type="number" name="calories" value="<c:out value="${meal.calories}"/>"/>
        </label><br/>
        <label>Date time:<input type="datetime-local" name="dateTime" value="<c:out value="${meal.dateTime}"/> "></label>
          <br/>
        <input type="submit">
    </form>
</body>
</html>
