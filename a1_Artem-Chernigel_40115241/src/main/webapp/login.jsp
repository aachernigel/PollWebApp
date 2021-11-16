<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>Log In</title>
</head>
<body id="loginPage">
<div class="loginDiv">
    <button onclick="window.location.href='index.jsp'">Home</button>
</div>
<div class="pollMessage">
    <div class="innerDiv" id="loginInnerDiv">
        <form method="post" action="LogInServlet">
            <label for="userIDLogIn" id="userIDLogInLabel">User ID:</label><br/>
            <input type="text" id="userIDLogIn" name="userIDLogIn"><br/>
            <label for="passwordLogIn" id="passwordLogInLabel">Password:</label><br/>
            <input type="password" id="passwordLogIn" name="passwordLogIn"><br/>
            <button type="submit" id="submitLogIn">
                Log In
            </button>
            <p name="errorLogIn" id="errorLogIn">
                <%=request.getAttribute("error") == null ? "" : request.getAttribute("error")%>
            </p>
        </form>
    </div>
</div>
</body>
</html>
