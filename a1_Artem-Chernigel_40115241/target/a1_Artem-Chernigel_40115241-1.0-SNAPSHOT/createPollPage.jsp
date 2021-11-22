<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <script type="text/javascript" src="scripts/script.js"></script>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>CreatePoll</title>
</head>
<body id="createPollBody">
<% int numOfOptions = Integer.parseInt(request.getParameter("optionNumber"));%>

<div class="pollMessage" id="createPollDiv">
    <div class="innerDiv" id="createPollInnerDiv">
        <button onclick="window.location.href='home.jsp'">Home</button>
        <form action="PollServlet" method="get">
            <div id="secondInnerDiv">
                <input type="text" value="" id="pollNameCreation" name="pollNameCreation" required="true">
                <label for="pollNameCreation">Please enter the poll name</label><br/>
                <input type="text" value="" id="pollQuestionCreation" name="pollQuestionCreation" required="true">
                <label for="pollQuestionCreation">Please enter the poll question</label><br/>
                <input type="hidden" id="options" name="options" value=<%=numOfOptions%>>
            </div>
            <button type="submit" id="submitCreation">
                Submit
            </button>
        </form>
        <script>
            console.log("<%= numOfOptions%>")
            let options = "<%=numOfOptions%>";
            for (let i = 1; i <= parseInt(options); i++) {
                createInputText(document.getElementById("options"),
                    "choice" + i + "Creation",
                    "choice" + i + "Creation",
                    i);
                document.getElementById("choice" + i + "Creation").setAttribute("required", "true");
            }
        </script>
    </div>
</div>
</body>
</html>