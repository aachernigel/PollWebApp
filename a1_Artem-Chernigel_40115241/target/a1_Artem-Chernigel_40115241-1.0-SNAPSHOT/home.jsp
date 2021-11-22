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
    <title>Poll</title>
</head>
<body id="homePage">
<div class="loginDiv">
    <form action="SearchServlet" method="get">
        <input type="text" name="pinInput" id="pinInput" placeholder="Your PIN# will be here" readonly
               value="<%=PollWrapper.manager.getPin() == null ? "" : PollWrapper.manager.getPin()%>">
        <button type="submit" name="requestPIN#" id="requestPIN#">
            Request PIN#
        </button>
    </form>
    <%
        if (session.getAttribute("userID") != null) {
    %>
    <form action="AccessAdminServlet" method="post">
        <button type="submit" id="adminButton">
            AdminFunctions
        </button>
    </form>
    <script>
        if(<%=request.getAttribute("disabledAdmin") != null && request.getAttribute("disabledAdmin").equals("true")%>)
            alert("Sorry, you did not create this poll! You cannot access admin functions");
    </script>
    <% } %>
    <button onclick="window.location.href='index.jsp'" id="homePageButton">
        Home
    </button>
</div>
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
        <form method="post" action="PollServlet">
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
            </br>
            <input type="text" name="pinInputVote" id="pinInputVote" placeholder="Enter the PIN#">
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
} else if (PollWrapper.manager.getStatus() == PollStatus.RELEASED || PollWrapper.manager.getStatus() == PollStatus.CLOSED) {
%>
<div class="pollMessage">
    <div class="innerDiv">
        <% if (PollWrapper.manager.getStatus() == PollStatus.RELEASED) {%>
        <p>The Poll is now released! <br/>
        </p>
        <% } else {%>
        <p>The Poll is permanently closed! <br/>
        </p>
        <script>
            document.getElementById("adminButton").disabled = true;
        </script>
        <% } %>
        <button type="submit" onclick="window.location.href='results.jsp'">
            Results
        </button>
        <p> Want to download the results of the Poll?
        </p>
        <form action="PollServlet" method="get">
            <input type="hidden" name="pollNameDownload" value="<%=PollWrapper.manager.getName()%>">
            <label>In which format would you like to download the results?</label>
            <br/><br/>
            <select id="pollFormatDownload" name="pollFormatDownload">
                <option value=".txt">.txt</option>
                <option value=".json">.json</option>
                <option value=".xml">.xml</option>
            </select>
            <br/><br/>
            <button type="submit">
                Download
            </button>
        </form>
    </div>
</div>
<% } %>
</body>
</html>