<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>Search</title>
</head>
<body id="searchPage">
<div class="loginDiv">
    <button onclick="window.location.href='index.jsp'">Home</button>
</div>
<%
    if (session.getAttribute("userID") != null) {
%>
<div class="pollMessage">
    <div class="innerDiv" id="searchInnerDiv">
        <p>Since you are already logged in, you can create a new Poll <br>
            using the button below!
        </p>
        <form action="numberOfOptionsPage.jsp">
            <button type="submit" id="adminButton">
                Create a new Poll
            </button>
        </form>
        <% } %>
        <div class="pollMessage" id="searchPollMessageDiv">
            <div class="innerDiv">
                <form method="post" action="SearchServlet">
                    <label for="pollIDInput" id="pollIDLoginLabel">
                        Poll ID:
                    </label></br>
                    <input type="text" name="pollIDInput" id="pollIDInput">
                    </br>
                    <button type="submit">Search</button>
                    <p name="errorLogIn" id="errorLogIn">
                        <%=request.getAttribute("error") == null ? "" : request.getAttribute("error")%>
                    </p>
                </form>
            </div>
        </div>
    </div>
</div>
</body>
</html>
