<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>Email Verification</title>
</head>
<body id="registerPage">

<% if(request.getAttribute("accountIsVerified") == null) {%>
<div class="pollMessage">
    <div class="innerDiv">
        <p>
            Oops! Something went completely wrong... <br/>
            Please try to verify your account again!
        </p>
        <button type="submit" onclick="window.location.href='index.jsp'">
            Home
        </button>
    </div>
</div>
<% } else if((Boolean) request.getAttribute("accountIsVerified")) {%>
<div class="pollMessage">
    <div class="innerDiv">
        <p>
            Thank you so much for verifying your account! <br/>
            You can now go ahead and log in!
        </p>
        <button type="submit" onclick="window.location.href='index.jsp'">
            Home
        </button>
    </div>
</div>
<% } else {%>
<div class="pollMessage">
    <div class="innerDiv">
        <p>
            Oops! We could not verify your account! Seems like the URL is corrupted... <br/>
            Please try to verify your account again!
        </p>
        <button type="submit" onclick="window.location.href='index.jsp'">
            Home
        </button>
    </div>
</div>
<% } %>
</body>
</html>