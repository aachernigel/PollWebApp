<%@ page import="PollManagerLib.*" %>
<%@ page import="PollManagerLib.DBPollGateway" %>
<%@ page import="java.sql.ResultSet" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>GetListOfPolls</title>
</head>
<%
    ResultSet polls = DBPollGateway.dbPoll.getListOfPolls((String) session.getAttribute("userID"));
    String pollsDesc = "";
    while (polls.next()) {
        pollsDesc += polls.getString("pollID") + "&!#@";
        pollsDesc += polls.getString("name") + "&!#@";
        pollsDesc += polls.getString("question") + "&!#@";
        pollsDesc += polls.getString("status") + "&!#@";
        pollsDesc += polls.getString("creatorID") + "&!#@";
        for (int i = 1; i <= 10; i++)
            pollsDesc += polls.getString("option" + i) == null ? "" : polls.getString("option" + i) + "&!#@";
        if (!polls.isLast())
            pollsDesc += "|";
    }
%>
<body id="listOfPollsPage">
<div class="loginDiv">
    <button onclick="window.location.href='home.jsp'">Poll</button>
</div>
<input type="hidden" id="temp" name="temp">
<script>
    let pollsDescription = "<%=pollsDesc%>";
    pollsDescription.split("|").forEach(poll => {
        let divElement = document.createElement("div");
        let innerDivElement = document.createElement("div");
        let paragraph = document.createElement("p");
        innerDivElement.className = "listOfPollsInnerDiv";
        divElement.className = "listOfPollsMessage";
        let fields = poll.split("&!#@");
        paragraph.innerHTML =  "The ID of the Poll is <b>" + fields[0] + "</b>" +
            ", while the name is <b>" + fields[1] + "</b>" +
            ". The question of the latter Poll was the following: <b>" + fields[2] + "</b>" +
            ". The current status of the Poll is  <b>" + fields[3] + "</b>" +
            ". Oh, and by the way, the ID of the creator (your ID) is <b>" + fields[4] + "</b>" +
            ". The options that were presented in the Poll were: ";
        for(let i = 5; i < fields.length - 1; i++){
            paragraph.innerHTML += "<b>" + fields[i];
            if(i == fields.length - 2)
                paragraph.innerHTML += ".</b>";
            else
                paragraph.innerHTML += ", </b>";
        }
        innerDivElement.innerHTML = paragraph.outerHTML;
        divElement.innerHTML = innerDivElement.outerHTML;
        document.getElementById("temp").parentNode.insertBefore(divElement, document.getElementById("temp").previousSibling);
    });
</script>
</body>
</html>