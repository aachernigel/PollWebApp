<%@ page import="PollManagerLib.*" %>
<%@ page import="java.util.Arrays" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <script type="text/javascript" src="scripts/script.js"></script>
    <title>Home</title>
    <%@ include file="login.jsp" %>
</head>
<body id="homePage">
<form action="SearchServlet" method="get">
    </br>
    <label for="pinInput">
        PIN#:
    </label></br>
    <input type="text" name="pinInput" id="pinInput" value="<%=PollWrapper.manager.getPIN() == null ? "" : PollWrapper.manager.getPIN()%>">
    <button type="submit" name="requestPIN#" id="requestPIN#">
        Request PIN#
    </button>
</form>
<%
    if (PollWrapper.manager.getStatus() == null) {
%>
<div class="pollMessage">
    <div class="innerDiv">
        <p> The Poll has not been created yet!</p>
    </div>
</div>
<%
} else if (PollWrapper.manager.getStatus() == PollStatus.CREATED) {
%>
<div class="pollMessage">
    <div class="innerDiv">
        <p> The Poll has been created but has not been started yet!</p>
    </div>
</div>
<%
} else if (PollWrapper.manager.getStatus() == PollStatus.RUNNING) {
%>
<div class="pollMessage">
    <div class="innerDiv">
        <form method="post" action="Servlet">
            <h1><%= PollWrapper.manager.getName()%>
            </h1>
            <h2><%= PollWrapper.manager.getQuestion()%>
            </h2>
            <%
                int numOfOptions = PollWrapper.manager.getChoices().length;
                String choiceDescriptions = "";
                for (int i = 0; i < numOfOptions; i++)
                    choiceDescriptions += PollWrapper.manager.getChoices()[i].getDescription() + "&!#@";
            %>
            </br></br>
            <button type="submit" id="submitHome">
                Vote
            </button>
            <label for="pinInputVote">
                PIN# (Optional):
            </label></br>
            <input type="text" name="pinInputVote" id="pinInputVote">
        </form>
        <script>
            console.log("<%=numOfOptions%>")
            let options = "<%=numOfOptions%>";
            let descriptionsStr = "<%=choiceDescriptions%>";
            for (let i = 1; i <= parseInt(options); i++) {
                createRadioButton(document.getElementById("submitHome"),
                    "choice" + i + "Home",
                    "choice",
                    descriptionsStr.split("&!#@")[i - 1]
                );
            }
            document.getElementById("choice1Home").setAttribute("required", "true");
        </script>
    </div>
</div>
<%
} else if (PollWrapper.manager.getStatus() == PollStatus.RELEASED) {
%>
<div class="pollMessage">
    <div class="innerDiv">
        <p>The Poll is now released! <br/>
        </p>
        <button type="submit" onclick="window.location.href='results.jsp'">
            Results
        </button>
        <p> Want to download the results of the Poll?
        </p>
        <form action="Servlet" method="get">
            <input type="hidden" name="pollNameDownload" value="<%=PollWrapper.manager.getName()%>">
            <input type="hidden" name="pollFormatDownload" value="text">
            <button type="submit">
                Download
            </button>
        </form>
    </div>
</div>
<% }%>
</body>
</html>