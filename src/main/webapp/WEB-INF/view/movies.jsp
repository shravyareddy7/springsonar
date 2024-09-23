<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Movies</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <h1>Movies for Theatre: ${theatre.name}</h1>
        </div>
    </div>
    <div id="container">
        <div id="content">

        <c:choose>
            <c:when test="${not empty movies}">
                <table>
                    <tr>
                        <th>Movie Name</th>
                        <th>Genre</th>
                        <th>Ticket Price</th>
                        <th>Action</th>
                    </tr>
                    <c:forEach var="movie" items="${movies}">
                        <tr>
                            <td>${movie.name}</td>
                            <td>${movie.genre}</td>
                            <td>${movie.ticketPrice}</td>
                            <td>
                                <a href="${pageContext.request.contextPath}/theatres/${theatre.id}/update-movie?movieId=${movie.id}">Update</a> |
                                <a href="${pageContext.request.contextPath}/theatres/${theatre.id}/delete-movie?movieId=${movie.id}"
                                   onclick="return confirm('Are you sure you want to delete this movie?');">Delete</a>
                            </td>
                        </tr>
                    </c:forEach>
                </table>
            </c:when>
            <c:otherwise>
                <p>No movies available.</p>
            </c:otherwise>
        </c:choose>


        <input type="button" value="Add Movie"
               onclick="window.location.href='${pageContext.request.contextPath}/theatres/${theatre.id}/add-movie'; return false;"
               class="submit-btn"/>
        <input type="button" value="Back"
               onclick="window.location.href='${pageContext.request.contextPath}/theatres';"
               class="submit-btn"/>

        </div>
    </div>
</body>
</html>
