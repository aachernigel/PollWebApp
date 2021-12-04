<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>Register</title>
</head>
<body>
<form action="RegisterServlet" method="post">
    <div class="pollMessage">
        <div class="innerDiv" id="loginInnerDiv">
            <label for="userIDRegister" id="userIDRegisterLabel">User ID:</label><br/>
            <input type="text" id="userIDRegister" name="userIDRegister"><br/>
            <label for="firstNameRegister" id="firstNameRegisterLabel">First Name:</label><br/>
            <input type="text" id="firstNameRegister" name="firstNameRegister"><br/>
            <label for="lastNameRegister" id="lastNameRegisterLabel">Last Name:</label><br/>
            <input type="text" id="lastNameRegister" name="lastNameRegister"><br/>
            <label for="emailAddressRegister" id="emailAddressRegisterLabel">Email address:</label><br/>
            <input type="text" id="emailAddressRegister" name="emailAddressRegister"><br/>
            <label for="passwordRegister" id="passwordRegisterLabel">Password:</label><br/>
            <input type="password" id="passwordRegister" name="passwordRegister"><br/>
            <button type="submit" id="submitRegister">
                Register
            </button>
        </div>
    </div>
</form>
</body>
</html>
