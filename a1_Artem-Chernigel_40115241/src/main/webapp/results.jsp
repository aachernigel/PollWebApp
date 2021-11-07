<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>Results</title>
    <%
        int numOfOptions = PollWrapper.manager.getChoices().length;
        String choiceDescriptions = "";
        String choiceCounters = "";
        String question = PollWrapper.manager.getQuestion();
        for(int i = 0; i < numOfOptions; i++){
            choiceDescriptions += PollWrapper.manager.getChoices()[i].getDescription() + "&!#@";
            choiceCounters += PollWrapper.manager.getChoices()[i].getChoiceCounter() + "&!#@";
        }
    %>
    <script type="text/javascript" src="https://www.gstatic.com/charts/loader.js"></script>
    <script type="text/javascript">
        google.charts.load("current", {packages: ["corechart"]});
        google.charts.setOnLoadCallback(drawChart);

        function drawChart() {
            let optionsNumber = "<%=numOfOptions%>";
            let descriptionsStr = "<%=choiceDescriptions%>";
            let choiceCountersStr = "<%=choiceCounters%>";
            var arrOfArr = new Array();
            arrOfArr.push(new Array('Choice', 'Number of Votes'));
            let maxChoice = 0;
            let maxChoiceDesc = "";
            for(let i = 1; i <= parseInt(optionsNumber); i++){
                if(parseInt(choiceCountersStr.split("&!#@")[i - 1]) > maxChoice){
                    maxChoice = parseInt(choiceCountersStr.split("&!#@")[i - 1]);
                    maxChoiceDesc = descriptionsStr.split("&!#@")[i - 1];
                } else if(parseInt(choiceCountersStr.split("&!#@")[i - 1]) === maxChoice){
                    maxChoiceDesc += ", and " + descriptionsStr.split("&!#@")[i - 1];
                }
                arrOfArr.push(new Array(descriptionsStr.split("&!#@")[i - 1], parseInt(choiceCountersStr.split("&!#@")[i - 1])));
            }
            var data = google.visualization.arrayToDataTable(arrOfArr);
            var options = {
                backgroundColor: 'transparent',
                title: '<%= question %>',
                is3D: true,
            };

            var chart = new google.visualization.PieChart(document.getElementById('piechart_3d'));
            chart.draw(data, options);
            document.getElementById("resultsMessage").innerHTML = "As we see, the majority of people voted for " + maxChoiceDesc + "!";
        }

    </script>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
</head>
<body id="resultsBody">
<div class="pollMessage" id="resultsDiv">
    <div class="innerDiv" id="resultsInnerDiv">
        <button onclick="window.location.href='home.jsp'">Home</button>
        <div id="piechart_3d"></div>
        <p id="resultsMessage"></p>
    </div>
</div>
</body>
</html>