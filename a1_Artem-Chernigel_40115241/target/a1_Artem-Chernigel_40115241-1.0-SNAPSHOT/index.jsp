<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <script type="text/javascript" src="scripts/script.js"></script>
    <title>Home</title>
</head>
<body onload="checkIfLoggedIn();" id="indexPage">
<script type="text/javascript">
    function checkIfLoggedIn() {
        let userID = "<%=session.getAttribute("userID")%>";
        if (!(userID === "null" || userID === "")) {
            document.getElementById("loginButton").onclick = null;
            document.getElementById("loginButtonForm").action = "LogInServlet";
            document.getElementById("loginButtonForm").method = "get";
            document.getElementById("loginButton").innerHTML = "Log Out"
        }
    }
</script>
<div class="functionsMenu" id="indexFunctionsMenu">
    <table>
        <tr>
            <td>
                <form id="loginButtonForm" method="get" action="login.jsp">
                    <button type="submit" id="loginButton" class="functionButton">
                        Log In
                    </button>
                </form>
            </td>
            <td>
                <button onclick="alert('The feature is not implemented yet!')" id="registerButton"
                        class="functionButton">
                    Register
                </button>
            </td>
        </tr>
        <tr>
            <td colspan="2">
                <button onclick="window.location.href='search.jsp';" id="searchButton"
                        class="functionButton">
                    Search
                </button>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
