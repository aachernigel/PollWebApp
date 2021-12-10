<%@ page import="PollManagerLib.*" %>
<%@ page import="PollManagerLib.DBPollGateway" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
  <link rel="stylesheet" type="text/css" href="styles/style.css">
  <title>DeletePoll</title>
</head>
<body>
<%
    if(PollWrapper.manager.getParticipants() == null) {
      DBPollGateway.dbPoll.deletePoll();
      response.sendRedirect("index.jsp");
    } else{
      System.err.println("The poll was already voted on! You cannot delete it!");
      response.sendRedirect("adminMenu.jsp");
    }
%>
</body>
</html>