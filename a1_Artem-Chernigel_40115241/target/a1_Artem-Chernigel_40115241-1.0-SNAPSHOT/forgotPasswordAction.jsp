<%@ page import="PollManagerLib.*" %>
<%@ page import="web.DBPollGateway" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>Forgot Password</title>
</head>
<body>
<% if (request.getAttribute("tokenEmailed") == null) {%>
<div class="pollMessage">
    <div class="innerDiv">
        <form action="ForgotPasswordServlet" method="post">
            <label for="userIDForgotPassword" id="userIDForgotPasswordLabel">User ID:</label><br/>
            <input type="text" id="userIDForgotPassword" name="userIDForgotPassword"><br/>
            <button type="submit" id="submitForgotPassword">
                Send me the link!
            </button>
        </form>
    </div>
</div>
<% } else if ((Boolean) request.getAttribute("tokenEmailed")) {%>
<div class="pollMessage">
    <div class="innerDiv">
        <p>
            Done! We just sent you the email to restore your password! <br/>
            Meanwhile, if you would like return to the home page, please use the button below: <br/>
        </p>
        <button onclick="window.location.href = 'index.jsp'">
            Home
        </button>
    </div>
</div>
<% } else {%>
<div class="pollMessage">
    <div class="innerDiv">
        <p>
            Oops... Something went completely wrong... Please try to restore the password again! <br/>
            Meanwhile, if you would like return to the home page, please use the button below: <br/>
        </p>
        <button onclick="window.location.href = 'index.jsp'">
            Home
        </button>
    </div>
</div>
<% } %>

</body>
</html>
