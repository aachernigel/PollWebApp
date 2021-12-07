<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>Change Password</title>
</head>
<body id="forgotPasswordPage">
<% if(request.getAttribute("passwordChanged") == null) {%>
<div class="pollMessage">
    <div class="forgotPasswordInnerDiv">
        <form action="ChangePasswordServlet" method="post">
            <label for="userIDChangePassword" id="userIDChangePasswordLabel">User ID:</label><br/>
            <input type="text" id="userIDChangePassword" name="userIDChangePassword"><br/>
            <label for="prevPasswordChangePassword" id="prevPasswordChangePasswordLabel">Previous password:</label><br/>
            <input type="password" id="prevPasswordChangePassword" name="prevPasswordChangePassword"><br/>
            <label for="newPasswordChangePassword" id="newPasswordChangePasswordLabel">New password:</label><br/>
            <input type="password" id="newPasswordChangePassword" name="newPasswordChangePassword"><br/>
            <button type="submit" id="submitChangePassword">
                Change
            </button>
            <p>
                <%= request.getAttribute("error") == null ? "" : request.getAttribute("error")%>
            </p>
        </form>
    </div>
</div>
<% } else if ((Boolean) request.getAttribute("passwordChanged")) {%>
<div class="pollMessage">
    <div class="forgotPasswordInnerDiv">
        <p>
            Done! You successfully changed your password :) <br/>
            You can go to the home page using the button below!
        </p>
        <button type="submit" onclick="window.location.href='index.jsp'">
            Home
        </button>
        <br/><br/><br/>
    </div>
</div>
<% } else {%>
<div class="pollMessage">
    <div class="forgotPasswordInnerDiv">
        <p>
            Oops! Something went completely wrong... <br/>
            Please try to change your password one more time!
        </p>
        <button type="submit" onclick="window.location.href='index.jsp'">
            Home
        </button>
        <br/><br/><br/>
    </div>
</div>
<% }%>
</body>
</html>
