<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <title>NumberOfOptions</title>
</head>
<body id="numberOfOptionsBody">
<div class="pollMessage">
    <div class="innerDiv">
        <form action="createPollPage.jsp" method="post">
            <label>How many options would you like to have?</label>
            <br/><br/>
            <select id="optionNumber" name="optionNumber">
                <option value="2">2</option>
                <option value="3">3</option>
                <option value="4">4</option>
                <option value="5">5</option>
                <option value="6">6</option>
                <option value="7">7</option>
                <option value="8">8</option>
                <option value="9">9</option>
                <option value="10">10</option>
            </select>
            <br/><br/>
            <button type="submit">
                Select
            </button>
        </form>
    </div>
</div>
</body>
</html>
