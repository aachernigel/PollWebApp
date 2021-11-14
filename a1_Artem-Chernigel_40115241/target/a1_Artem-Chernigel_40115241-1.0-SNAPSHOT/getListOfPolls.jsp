<%@ page import="PollManagerLib.*" %>
<%@ page import="web.DBPollGateway" %>
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
    System.out.println((String) session.getAttribute("userID"));
    ResultSet polls = DBPollGateway.dbPoll.getListOfPolls((String) session.getAttribute("userID"));
    String pollsDesc = "";
    boolean finished = false;
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
    finished = true;
    System.out.println(pollsDesc);
%>
<body>
<input type="text" id="temp" name="temp">
<script>
    let pollsDescription = "<%=pollsDesc%>";
    pollsDescription.split("|").forEach(poll => {
        let divElement = document.createElement("div");
        divElement.className = "pollMessage";
        let fields = poll.split("&!#@");
        divElement.innerHTML = "pollID: " + fields[0] +
            " name: " + fields[1] +
            " question: " + fields[2] +
            " status: " + fields[3] +
            " creatorID: " + fields[4];
        for(let i = 5; i < fields.length - 1; i++){
            divElement.innerHTML += "option" + (i - 4) + ": " + fields[i] + " ";
        }
        console.log(divElement.innerHTML);
        document.getElementById("temp").parentNode.insertBefore(divElement, document.getElementById("temp").previousSibling);
    });
</script>
</body>
</html>