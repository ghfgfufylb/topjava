<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page session="false" %>
<html>
<head>
    <style>
        .red{
            color:red;
        }
        .green{
            color:green;
        }
    </style>
    <title>Title</title>
</head>
<body>
    <h3>Meal List</h3>
<table border="1px" cellspacing="0" cellpadding="5" >
    <th>Date Time</th>
    <th>Description</th>
    <th>Calories</th>

    <c:forEach items="${mealList}" var="meal">
        <jsp:useBean id="meal" type="ru.javawebinar.topjava.model.MealWithExceed" scope="page" />
        <tr class="${meal.exceed ? 'red' : 'green'}">

            <td><c:out value="${meal.dateTime.toString().replace('T',' ')}"/> </td>
            <td><c:out value="${meal.description}"/> </td>
            <td><c:out value="${meal.calories}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>
