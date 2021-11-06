<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body>
<script type="text/javascript">
    let attempts = 3;

    function checkPassword() {
        if (attempts <= 1) {
            document.getElementById("submit").disabled = true;
            alert("You have no attempts left, sorry!");
        // } else if (document.getElementById("password").value === "secretPassword123" && attempts > 0) {
        } else if (document.getElementById("password").value === "123" && attempts > 0) {
            window.location.href = "adminMenu.jsp";
        } else {
            attempts--;
            alert("You have " + attempts + " attempts left!");
        }
    }
</script>
<div class="loginDiv">
    <form method="post">
        <label for="password" id="passwordLabel">Password:</label><br/>
        <input type="password" id="password" name="password"><br/>
        <input type="button" id="submit" onclick="checkPassword()" value="Log In">
    </form>
</div>
</body>
</html>
