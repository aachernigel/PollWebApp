<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>ClosePoll</title>
</head>
<body>
<%
    try {
        PollWrapper.manager.ClosePoll();
    } catch (PollException pe) {
        System.err.println(pe);
    }
    PollWrapper.manager.setStatus(null);
    response.sendRedirect("home.jsp");
%>
</body>
</html>
