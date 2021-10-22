<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE>
<html lang="ru">
<head>
    <title>Employees</title>
</head>
<body>

    <table>
        <th>Имя</th><th>Зарплата</th><th>Должность</th><th>Департамены</th>
        <c:forEach var="employee" items="${requestScope.employees}">
            <tr>
                <td><c:out value="${employee.name}"/></td>
                <td><c:out value="${employee.salary}"/></td>
                <td><c:out value="${empty employee.title ? '---' : employee.title.name}"/></td>
            </tr>
        </c:forEach>
    </table>

</body>
</html>
