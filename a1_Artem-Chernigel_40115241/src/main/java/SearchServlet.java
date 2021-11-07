import PollManagerLib.Choice;
import PollManagerLib.PollException;
import PollManagerLib.PollWrapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.Arrays;
import java.util.LinkedList;

@WebServlet(name = "SearchServlet", value = "/SearchServlet")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        DBConnection.getConnection();
        LinkedList<String> ids = new LinkedList<>();
        try {
            Statement statement = DBConnection.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT pollID FROM poll");
            while (resultSet.next()) {
                ids.add(resultSet.getString("pollID"));
            }
            DBConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        if (ids.contains(request.getParameter("pollIDInput"))) {
            if(PollWrapper.manager.getStatus() != null)
                try{
                    PollWrapper.manager.ReleasePoll();
                    PollWrapper.manager.ClosePoll();
                    PollWrapper.manager.setStatus(null);
                } catch (PollException pe){
                    System.err.println(pe);
                }
            initializePoll(request.getParameter("pollIDInput"));
            response.sendRedirect("home.jsp");
        } else {
            System.out.println("Such pollID does not exist");
            response.sendRedirect("index.jsp");
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    private void initializePoll(String pollID) {
        DBConnection.getConnection();
        try {
            Statement statement = DBConnection.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM poll WHERE pollID = " + "\"" + pollID + "\"");
            LinkedList<Choice> choicesTemp = new LinkedList<>();
            String tempName = "";
            String tempQuestion = "";
            while (resultSet.next()) {
                for (int counter = 1; counter <= 10; counter++)
                    choicesTemp.add(new Choice(resultSet.getString("option" + counter)));
                PollWrapper.manager.setPollID(resultSet.getString("pollID"));
                tempName = resultSet.getString("name");
                tempQuestion = resultSet.getString("question");
            }
            choicesTemp.removeIf(x -> (x.getDescription() == null|| x.getDescription().equals("")));
            choicesTemp.forEach(System.out::println);
            try {
                PollWrapper.manager.CreatePoll(
                        tempName,
                        tempQuestion,
                        choicesTemp.toArray(new Choice[choicesTemp.size()])
                );
            } catch (PollException pe) {
                System.err.println(pe);
            }
            DBConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
