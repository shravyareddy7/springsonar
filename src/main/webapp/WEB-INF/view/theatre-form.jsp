<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Add/Update Theatre</title>
    <link rel="stylesheet" type="text/css" href="${pageContext.request.contextPath}/resources/css/style.css">
</head>
<body>
    <div id="wrapper">
        <div id="header">
            <h1>Add/Update Theatre</h1>
        </div>
    </div>
    <div id="container">
        <div id="content">
            <form:form modelAttribute="theatre" action="save-theatre" method="post">

                <c:if test="${not empty errorMessage}">
                    <div class="error-message" style="color: red;">${errorMessage}</div>
                </c:if>
                <br/>
                <form:hidden path="id"/>
                <div class="form-group">
                    <form:label path="name">Theatre Name:</form:label>
                    <form:input path="name" />
                    <form:errors path="name" cssClass="error" />
                </div>

                <div class="form-group">
                    <form:label path="location">Location:</form:label>
                    <form:input path="location" />
                    <form:errors path="location" cssClass="error" />
                </div>

                <div class="form-group">
                    <input type="submit" value="Save" class="submit-btn"/>
                    <input type="button" value="Cancel" onclick="window.location.href='${pageContext.request.contextPath}/theatres';"/>
                </div>

            </form:form>
        </div>
    </div>
</body>
</html>
