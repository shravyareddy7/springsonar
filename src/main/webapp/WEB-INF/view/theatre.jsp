<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Theatres</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <h1>Theatres</h1>
        </div>
    </div>
    <div id="container">
        <div id="content">
            <table>
                <tr>
                    <th>Theatre Name</th>
                    <th>Location</th>
                    <th>Action</th>
                    <th>Movies</th>
                </tr>

                <c:forEach var="theatre" items="${theatres}">
                    <c:url var="updateLink" value="theatres/update-theatre">
                        <c:param name="theatreId" value="${theatre.id}"/>
                    </c:url>
                    <c:url var="deleteLink" value="theatres/delete-theatre">
                        <c:param name="theatreId" value="${theatre.id}"/>
                    </c:url>
                    <c:url var="displayLink" value="theatres/${theatre.id}/movies"/>

                    <tr>
                        <td>${theatre.name}</td>
                        <td>${theatre.location}</td>
                        <td>
                            <a href="${updateLink}">Update</a> |
                            <a href="${deleteLink}"
                               onclick="return confirm('Are you sure you want to delete this theatre?');">
                               Delete
                            </a>
                        </td>
                        <td>
                            <a href="${displayLink}">Show movies</a>
                        </td>
                    </tr>
                </c:forEach>

            </table>

            <input type="button" value="Add Theatre"
                   onclick="window.location.href='${pageContext.request.contextPath}/theatres/add-theatre'; return false;"
                   class="submit-btn"/>
        </div>
    </div>
</body>
</html>
