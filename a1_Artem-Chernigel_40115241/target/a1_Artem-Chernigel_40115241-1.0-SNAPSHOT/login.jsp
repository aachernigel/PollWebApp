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

    // check if request attribute "attempts" is less than or equal to three
    // deny log in if greater than three (disable the button)
    function checkInput() {

    }

</script>
<%
    if(session.getAttribute("userID") == null){
%>
<div class="loginDiv">
    <form method="post" action="LogInServlet">
        <label for="userIDLogIn" id="userIDLogInLabel">User ID:</label><br/>
        <input type="text" id="userIDLogIn" name="userIDLogIn"><br/>
        <label for="passwordLogIn" id="passwordLogInLabel">Password:</label><br/>
        <input type="password" id="passwordLogIn" name="passwordLogIn"><br/>
        <button type="submit" id="submitLogIn" onclick="checkInput()">
            Log In
        </button>
        <p name="errorLogIn" id="errorLogIn">
            <%=request.getAttribute("error") == null ? "" : request.getAttribute("error")%>
        </p>
    </form>
    <% }else{ %>
    <p>Hello!</p>
    <form action="numberOfOptionsPage.jsp">
        <button type="submit" id="adminButton">
            Create a new Poll
        </button>
    </form>
    <form action="LogInServlet" method="get">
        <button type="submit" id="logOutButton">
            Log Out
        </button>
    </form>
    <% } %>
    <form method="post" action="RegisterServlet">
        <button type="submit" id="register" onclick="alert('The feature is not implemented yet!')">
            Register
        </button>
    </form>
</div>
</body>
</html>
