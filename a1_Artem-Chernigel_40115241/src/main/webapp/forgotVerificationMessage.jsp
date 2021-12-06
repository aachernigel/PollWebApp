<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>Forgot Password Verification</title>
</head>
<body>
<% if(request.getAttribute("password") == null) {%>
<div class="pollMessage">
    <div class="innerDiv">
        <p>
            Oops, seems like the link was corrupted and the token was invalid! <br/>
            Please try again and verify the link.
        </p>
    </div>
</div>
<% } else {%>
<div class="pollMessage">
    <div class="innerDiv">
        <p>
            Thank you for following the security procedure! <br/>
            We created a temporary password just for you:
            <%= request.getAttribute("password") %> <br/>
            If you would like to change your password, please click on the button below! <br/>
            Otherwise, there is a home button to return to the home page.
        </p>
        <button onclick="window.location.href = 'changePassword.jsp'">
            Change Password
        </button> <br/><br/>
        <button onclick="window.location.href = 'index.jsp'">
            Home
        </button>
    </div>
</div>
<% } %>
</body>
</html>
