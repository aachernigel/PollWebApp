<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>ReleasePoll</title>
</head>
<body>
<%
    try {
        PollWrapper.manager.ReleasePoll();
    } catch (PollException pe) {
        System.err.println(pe);
    }
    response.sendRedirect("index.jsp");
%>
</body>
</html>

