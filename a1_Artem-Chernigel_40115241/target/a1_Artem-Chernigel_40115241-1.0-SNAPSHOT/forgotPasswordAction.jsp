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
<body id="forgotPasswordPage">
<% if (request.getAttribute("tokenEmailed") == null) {%>
<div class="pollMessage">
    <div class="forgotPasswordInnerDiv">
        <form action="ForgotPasswordServlet" method="post">
            <label for="userIDForgotPassword" id="userIDForgotPasswordLabel">User ID:</label><br/>
            <input type="text" id="userIDForgotPassword" name="userIDForgotPassword"><br/>
            <button type="submit" id="submitForgotPassword">
                Send me the link!
            </button>
            <p name="errorForgotPassword" id="errorForgotPassword">
                <%=request.getAttribute("error") == null ? "" : request.getAttribute("error")%>
            </p>
        </form>
    </div>
</div>
<% } else if ((Boolean) request.getAttribute("tokenEmailed")) {%>
<div class="pollMessage">
    <div class="forgotPasswordInnerDiv">
        <p>
            Done! We just sent you the email to restore your password! <br/>
        </p>
        <button onclick="window.location.href = 'index.jsp'">
            Home
        </button>
        <br/><br/><br/>
    </div>
</div>
<% } else {%>
<div class="pollMessage">
    <div class="forgotPasswordInnerDiv">
        <p>
            Oops... Something went completely wrong... Please try to restore the password again! <br/>
        </p>
        <button onclick="window.location.href = 'index.jsp'">
            Home
        </button>
        <br/><br/><br/>
    </div>
</div>
<% } %>

</body>
</html>
