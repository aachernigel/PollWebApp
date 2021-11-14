<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>Menu</title>
</head>
<body id="functionsPage">

<%
    String statusJsp = PollWrapper.manager.getStatus() == null ? "null" : PollWrapper.manager.getStatus().toString();
%>

<script type="text/javascript">

    let status = "<%=statusJsp%>";

    window.onload = function () {
        if (status === "null") {
            document.getElementById("createPollButton").disabled = false;
            document.getElementById("updatePollButton").disabled = true;
            document.getElementById("clearPollButton").disabled = true;
            document.getElementById("closePollButton").disabled = true;
            document.getElementById("runPollButton").disabled = true;
            document.getElementById("releasePollButton").disabled = true;
            document.getElementById("unreleasePollButton").disabled = true;
        } else if (status === "CREATED") {
            document.getElementById("createPollButton").disabled = true;
            document.getElementById("updatePollButton").disabled = false;
            document.getElementById("clearPollButton").disabled = true;
            document.getElementById("closePollButton").disabled = true;
            document.getElementById("runPollButton").disabled = false;
            document.getElementById("releasePollButton").disabled = true;
            document.getElementById("unreleasePollButton").disabled = true;
        } else if (status === "RUNNING") {
            document.getElementById("createPollButton").disabled = true;
            document.getElementById("updatePollButton").disabled = false;
            document.getElementById("clearPollButton").disabled = false;
            document.getElementById("closePollButton").disabled = true;
            document.getElementById("runPollButton").disabled = true;
            document.getElementById("releasePollButton").disabled = false;
            document.getElementById("unreleasePollButton").disabled = true;
        } else if (status === "RELEASED") {
            document.getElementById("createPollButton").disabled = false;
            document.getElementById("updatePollButton").disabled = true;
            document.getElementById("clearPollButton").disabled = false
            document.getElementById("closePollButton").disabled = false;
            document.getElementById("runPollButton").disabled = true;
            document.getElementById("releasePollButton").disabled = true;
            document.getElementById("unreleasePollButton").disabled = false;
        }
    }
</script>

<!-- FUNCTIONS: DELETE A POLL, GET LIST OF POLLS, -->
<div class="functionsMenu">
    <table>
        <tr>
            <td>
                <button onclick="window.location.href='home.jsp';" id="homeButton" class="functionButton">
                    Home
                </button>
            </td>
            <td>
                <button onclick="window.location.href='numberOfOptionsPage.jsp';" id="createPollButton"
                        class="functionButton">
                    Create a new Poll
                </button>
            </td>
        </tr>
        <tr>
            <td>
                <button onclick="window.location.href='updatePollPage.jsp';" id="updatePollButton"
                        class="functionButton">
                    Update the Poll
                </button>
            </td>
            <td>
                <form action="clearPollPage.jsp">
                    <button type="submit" id="clearPollButton" class="functionButton">
                        Clear the Poll
                    </button>
                </form>
            </td>
        </tr>
        <tr>
            <td>
                <form action="closePollPage.jsp">
                    <button type="submit" id="closePollButton" class="functionButton">
                        Close the Poll
                    </button>
                </form>
            </td>
            <td>
                <form action="runPollPage.jsp">
                    <button type="submit" id="runPollButton" class="functionButton">
                        Run the Poll
                    </button>
                </form>
            </td>
        </tr>
        <tr>
            <td>
                <form action="releasePollPage.jsp">
                    <button type="submit" id="releasePollButton" class="functionButton">
                        Release the Poll
                    </button>
                </form>
            </td>
            <td>
                <form action="unreleasePollPage.jsp">
                    <button type="submit" id="unreleasePollButton" class="functionButton">
                        Unrelease the Poll
                    </button>
                </form>
            </td>
        </tr>
        <tr>
            <td>
                <form action="deletePoll.jsp">
                    <button type="submit" id="deletePollButton" class="functionButton">
                        Delete the Poll
                    </button>
                </form>
            </td>
            <td>
                <form action="getListOfPolls.jsp">
                    <button type="submit" id="getListOfPollsButton" class="functionButton">
                        Get List of Polls
                    </button>
                </form>
            </td>
        </tr>
    </table>
</div>
</body>
</html>
