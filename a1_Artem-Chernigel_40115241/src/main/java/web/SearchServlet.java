package web;

import PollManagerLib.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.sql.*;
import java.util.LinkedList;

@WebServlet(name = "SearchServlet", value = "/SearchServlet")
public class SearchServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setMaxInactiveInterval(60 * 60 * 24 * 365);
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
            if (PollWrapper.manager.getStatus() != null)
                try {
                    PollWrapper.manager.ReleasePoll();
                    PollWrapper.manager.ClosePoll();
                    PollWrapper.manager.setStatus(null);
                } catch (PollException pe) {
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
        if (PollWrapper.manager.getPIN() == null) {
            PollWrapper.manager.generatePIN();
            DBConnection.getConnection();
            try {
                Statement statement = DBConnection.conn.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT * FROM vote WHERE " +
                        "pin = " + "\"" + PollWrapper.manager.getPIN() + "\"" +
                        "AND pollID = " + "\"" + PollWrapper.manager.getPollID() + "\""
                );
                while (resultSet.first()) {
                    PollWrapper.manager.generatePIN();
                    resultSet = statement.executeQuery("SELECT * FROM vote WHERE " +
                            "pin = " + "\"" + PollWrapper.manager.getPIN() + "\"" +
                            "AND pollID = " + "\"" + PollWrapper.manager.getPollID() + "\""
                    );
                }
                statement.executeUpdate(
                        "INSERT INTO vote (pollID, sessionID, pin) " +
                                "VALUES (" +
                                "\"" + PollWrapper.manager.getPollID() + "\", " +
                                "\"" + request.getSession().getId() + "\", " +
                                "\"" + PollWrapper.manager.getPIN() + "\"" +
                                ")"
                );
                DBConnection.closeConnection();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        response.sendRedirect("home.jsp");
    }

    private void initializePoll(String pollID) {
        PollWrapper.manager = new PollManager();
        DBConnection.getConnection();
        try {
            Statement statement = DBConnection.conn.createStatement();
            ResultSet resultSet = statement.executeQuery("SELECT * FROM poll WHERE pollID = " + "\"" + pollID + "\"");
            LinkedList<Choice> choicesTemp = new LinkedList<>();
            String tempName = "";
            String tempQuestion = "";
            String tempPollID = "";
            String tempStatus = "";
            while (resultSet.next()) {
                for (int counter = 1; counter <= 10; counter++)
                    choicesTemp.add(new Choice(resultSet.getString("option" + counter)));
                tempPollID = resultSet.getString("pollID");
                tempName = resultSet.getString("name");
                tempQuestion = resultSet.getString("question");
                tempStatus = resultSet.getString("status");
            }
            choicesTemp.removeIf(x -> (x.getDescription() == null || x.getDescription().equals("")));
            choicesTemp.forEach(System.out::println);
            try {
                PollWrapper.manager.CreatePoll(
                        tempName,
                        tempQuestion,
                        choicesTemp.toArray(new Choice[choicesTemp.size()])
                );
                PollWrapper.manager.setPollID(tempPollID);
                if(tempStatus != null){
                    switch (tempStatus){
                        case "CREATED":
                            PollWrapper.manager.setStatus(PollStatus.CREATED);
                            break;
                        case "RUNNING":
                            PollWrapper.manager.setStatus(PollStatus.RUNNING);
                            break;
                        case "RELEASED":
                            PollWrapper.manager.setStatus(PollStatus.RELEASED);
                            break;
                        case "CLOSED":
                            // ???
                            break;
                    }
                }
                PreparedStatement preparedStatement = DBConnection.conn.prepareStatement("SELECT * FROM vote WHERE pollID = ?");
                preparedStatement.setString(1, PollWrapper.manager.getPollID());
                resultSet = preparedStatement.executeQuery();
                while(resultSet.next()){
                    for(int i = 0; i < PollWrapper.manager.getChoices().length; i++){
                        if(resultSet.getString("choice").equals(PollWrapper.manager.getChoices()[i].getDescription()))
                            PollWrapper.manager.Vote(resultSet.getString("sessionID"), PollWrapper.manager.getChoices()[i]);
                    }
                }
            } catch (PollException pe) {
                System.err.println(pe);
            }
            DBConnection.closeConnection();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
