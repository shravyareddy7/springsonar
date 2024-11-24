<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add Movie</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">

</head>
<body>
    <h1>Add Movie</h1>
    <form:form modelAttribute="movie" action="${pageContext.request.contextPath}/theatres/${theatreId}/save-movie" method="post">
        <form:hidden path="id"/>
        <form:hidden path="theatre.id" value="${theatreId}"/>
        <div class="form-group">
            <form:label path="name">Movie Name:</form:label>
            <form:input path="name" />
            <form:errors path="name" cssClass="error" />
        </div>

        <div class="form-group">
            <form:label path="genre">Genre:</form:label>
            <form:input path="genre" />
            <form:errors path="genre" cssClass="error" />
        </div>

        <div class="form-group">
            <form:label path="ticketPrice">Ticket Price:</form:label>
            <form:input path="ticketPrice" />
            <form:errors path="ticketPrice" cssClass="error" />
        </div>

        <div class="form-group">
            <input type="submit" value="Add Movie"/>
            <input type="button" value="Cancel" onclick="window.location.href='${pageContext.request.contextPath}/theatres/${theatreId}/add-movie';"/>
        </div>
    </form:form>
</body>
</html>
