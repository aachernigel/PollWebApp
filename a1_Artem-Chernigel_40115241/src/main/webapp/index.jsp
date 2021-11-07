<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <script type="text/javascript" src="scripts/script.js"></script>
    <title>Search</title>
    <%@ include file="login.jsp" %>
</head>
<body>
<form method="post" action="SearchServlet">
    <label for="pollIDInput">
        PollID:
    </label></br>
    <input type="text" name="pollIDInput" id="pollIDInput">
    </br>
    <button type="submit">Search</button>
</form>
</br>
<label for="pinInput">
    PIN#:
</label></br>
<input type="text" name="pinInput" id="pinInput">
</body>
</html>
