<%@ page import="PollManagerLib.*" %>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.1//EN"
"http://www.w3.org/TR/xhtml11/DTD/xhtml11.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
    <title>UpdatePoll</title>
    <link rel="stylesheet" type="text/css" href="styles/style.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <script type="text/javascript" src="scripts/script.js"></script>
    <script>
        $(document).ready(function () {
            $("div.divUpdate").hide();
            $('input[type="radio"]').click(function () {
                var value = $(this).val();
                console.log(value);
                $("div.divUpdate").hide();
                $("#show" + value).show();
            });
        });
    </script>
</head>
<body id="updatePollBody">
<%int numOfOptions = PollWrapper.manager.getChoices().length;%>
<div class="pollMessage" id="updatePollDiv">
    <div class="innerDiv" id="updatePollInnerDiv">
        <button onclick="window.location.href='index.jsp'">Home</button>
        <p>Would you like to update the poll fields as well?</p>
        <label class="spanRadio">
    <span class="spanInput">
        <input type="radio" name="updateFields" id="updateFieldsYes" value="Form"/>
        <span class="spanControl"></span>
        <label for="updateFieldsYes">Yes</label>
    </span>
        </label>
        <label class="spanRadio">
    <span class="spanInput">
        <input type="radio" name="updateFields" id="updateFieldsNo" value="Message"/>
        <span class="spanControl"></span>
        <label for="updateFieldsNo">No</label>
    </span>
        </label>
        <div id="showForm" class="divUpdate">
            <form action="Servlet" method="get">
                <div id="secondInnerDiv">
                    <input type="text" value="" id="pollNameUpdate" name="pollNameUpdate" required="true">
                    <label for="pollNameUpdate">Please enter the poll name:</label><br/>
                    <input type="text" value="" id="pollQuestionUpdate" name="pollQuestionUpdate" required="true">
                    <label for="pollQuestionUpdate">Please enter the poll question:</label><br/><br/>
                    <input type="hidden" id="options" name="options" value=<%=numOfOptions%>>
                </div>
                <button type="submit" id="submitUpdate">
                    Update
                </button>
                <script>
                    console.log("<%= numOfOptions%>")
                    let options = "<%=numOfOptions%>";
                    for (let i = 1; i <= parseInt(options); i++) {
                        createInputText(document.getElementById("options"),
                            "choice" + i + "Update",
                            "choice" + i + "Update",
                            i);
                        document.getElementById("choice" + i + "Update").setAttribute("required", "true");
                    }
                </script>
            </form>
        </div>
        <div id="showMessage" class="divUpdate">
            <p>Then just click on the button below to update the Poll!</p>
            <form action="Servlet" method="get">
                <input type="hidden" name="doNotChangeValuesUpdate" id="doNotChangeValuesUpdate" value="true">
                <button type="submit">
                    Update
                </button>
            </form>
        </div>
    </div>
</div>
</body>
</html>
