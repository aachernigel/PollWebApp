import PollManagerLib.Choice;
import PollManagerLib.PollException;
import PollManagerLib.PollWrapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.*;

@WebServlet(name = "Servlet", value = "/Servlet")
public class Servlet extends HttpServlet {
    private final String FORMAT = "text";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("pollNameDownload") != null && request.getParameter("pollFormatDownload") != null) {
            if (!request.getParameter("pollNameDownload").equals(PollWrapper.manager.getName())) {
                System.err.println("ERROR: Poll name does not match in doGet");
                response.sendRedirect("home.jsp");
            } else if (!request.getParameter("pollFormatDownload").equals(FORMAT)) {
                System.err.println("ERROR: Download format does not match in doGet");
                response.sendRedirect("home.jsp");
            } else {
                String filename = PollWrapper.manager.getName() + "-" + PollWrapper.manager.getTime() + ".txt";
                ServletOutputStream out = response.getOutputStream();
                PrintWriter pw;
                response.addHeader("Content-Disposition",
                        "attachment; filename=" + filename);
                response.setHeader("Cache-Control", "no-cache");
                response.setDateHeader("Expires", 0);

                try {
                    pw = new PrintWriter(out);
                    try {
                        PollWrapper.manager.DownloadPollDetails(pw, filename);
                    } catch (PollException pe) {
                        System.err.println(pe);
                    }
                } catch (Exception e) {
                    System.err.println(e);
                }
            }
        } else {
            boolean verifiedCreation = true;
            if (request.getParameter("pollNameCreation") == null || request.getParameter("pollQuestionCreation") == null)
                verifiedCreation = false;
            if (verifiedCreation) {
                for (int i = 1; i <= Integer.parseInt(request.getParameter("options")); i++) {
                    if (request.getParameter("choice" + i + "Creation") == null) {
                        verifiedCreation = false;
                        break;
                    }
                }
            }
            if (verifiedCreation) {
                System.out.println("--- CREATING THE POLL ---");
                System.out.println("Name of the Poll: " + request.getParameter("pollNameCreation"));
                System.out.println("Question of the Poll: " + request.getParameter("pollQuestionCreation"));
                Choice[] arr = new Choice[Integer.parseInt(request.getParameter("options"))];
                String choicesStr = "";
                String columnsStr = "";
                for (int i = 1; i <= Integer.parseInt(request.getParameter("options")); i++) {
                    System.out.println("Choice " + i + " of the Poll: " + request.getParameter("choice" + i + "Creation"));
                    arr[i - 1] = new Choice(request.getParameter("choice" + i + "Creation"));
                    choicesStr += "\"" + request.getParameter("choice" + i + "Creation") + "\"";
                    columnsStr += "option" + i;
                    if (i != Integer.parseInt(request.getParameter("options"))) {
                        choicesStr += ", ";
                        columnsStr += ", ";
                    }
                }
                try {
                    PollWrapper.manager.CreatePoll(
                            request.getParameter("pollNameCreation"), request.getParameter("pollQuestionCreation"),
                            arr
                    );
                    DBConnection.getConnection();
                    try {
                        Statement statement = DBConnection.conn.createStatement();
                        statement.executeUpdate(
                                "INSERT INTO poll(pollID, name, question, " + columnsStr + ") VALUES (" +
                                        "\"" + PollWrapper.manager.getPollID() + "\"" +
                                        ", \"" + PollWrapper.manager.getName() + "\"" +
                                        ", \"" + PollWrapper.manager.getQuestion() + "\"" +
                                        ", " + choicesStr +
                                        ")");
                        DBConnection.closeConnection();
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                } catch (PollException pe) {
                    System.err.println(pe);
                }
                System.out.println(PollWrapper.manager.getStatus());
                response.sendRedirect("home.jsp");
            }
            if (request.getParameter("doNotChangeValuesUpdate") != null) {
                System.out.println("--- UPDATING THE POLL ---");
                System.out.println("   [Values stay the same]");
                try {
                    PollWrapper.manager.UpdatePoll(
                            PollWrapper.manager.getName(),
                            PollWrapper.manager.getQuestion(),
                            PollWrapper.manager.getChoices()
                    );
                } catch (PollException pe) {
                    System.err.println(pe);
                }
                response.sendRedirect("home.jsp");
            } else {
                boolean verifiedUpdate = true;
                if (request.getParameter("pollNameUpdate") == null || request.getParameter("pollQuestionUpdate") == null)
                    verifiedUpdate = false;
                if (verifiedUpdate) {
                    for (int i = 1; i <= Integer.parseInt(request.getParameter("options")); i++) {
                        if (request.getParameter("choice" + i + "Update") == null) {
                            verifiedUpdate = false;
                            break;
                        }
                    }
                }
                if (verifiedUpdate) {
                    System.out.println("--- UPDATING THE POLL ---");
                    System.out.println("Name of the Poll: " + request.getParameter("pollNameUpdate"));
                    System.out.println("Question of the Poll: " + request.getParameter("pollQuestionUpdate"));
                    Choice[] arr = new Choice[PollWrapper.manager.getChoices().length];
                    for (int i = 1; i <= arr.length; i++) {
                        System.out.println("Choice " + i + " of the Poll UPDATED: " + request.getParameter("choice" + i + "Update"));
                        arr[i - 1] = new Choice(request.getParameter("choice" + i + "Update"));
                    }
                    try {
                        PollWrapper.manager.UpdatePoll(
                                request.getParameter("pollNameUpdate"), request.getParameter("pollQuestionUpdate"),
                                arr
                        );
                    } catch (PollException pe) {
                        System.err.println(pe);
                    }
                    response.sendRedirect("home.jsp");
                }
            }
        }
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("choice") != null) {
            System.out.println(" The choice selected was: " + request.getParameter("choice"));
            for (int i = 0; i < PollWrapper.manager.getChoices().length; i++) {
                System.out.println(PollWrapper.manager.getChoices()[i].getDescription());
                if (request.getParameter("choice").equals(PollWrapper.manager.getChoices()[i].getDescription())) {
                    try {
                        DBConnection.getConnection();
                        try {
                            Statement statement = DBConnection.conn.createStatement();
                            ResultSet resultSet = statement.executeQuery("SELECT * FROM vote WHERE " +
                                    "sessionID = " + "\"" + request.getSession().getId() + "\"" +
                                    "AND pollID = " + "\"" + PollWrapper.manager.getPollID() + "\""
                            );
                            if (!resultSet.first()) {
                                System.out.println("Generating a PIN for this Poll...");
                                PollWrapper.manager.generatePIN();
                                PreparedStatement preparedStatement = DBConnection.conn.prepareStatement(
                                        "INSERT INTO vote (pollID, sessionID, pin, choice) VALUES (?,?,?,?)"
                                );
                                preparedStatement.setString(1, PollWrapper.manager.getPollID());
                                preparedStatement.setString(2, request.getSession().getId());
                                preparedStatement.setString(3, PollWrapper.manager.getPIN());
                                preparedStatement.setString(4, PollWrapper.manager.getChoices()[i].getDescription());
                                preparedStatement.executeUpdate();
                                DBConnection.closeConnection();
                            } else if(resultSet.first()){
                                DBConnection.getConnection();
                                PreparedStatement preparedStatement = DBConnection.conn.prepareStatement(
                                        "UPDATE vote SET choice = ? WHERE pollID = ? AND pin = ?"
                                );
                                preparedStatement.setString(1, PollWrapper.manager.getChoices()[i].getDescription());
                                preparedStatement.setString(2, PollWrapper.manager.getPollID());
                                preparedStatement.setString(3, PollWrapper.manager.getPIN());
                                preparedStatement.executeUpdate();
                                DBConnection.closeConnection();
                                if(request.getParameter("pinInputVote").equals("")){
                                    System.out.println("We used your generated PIN from before to process the vote!");
                                } else if(!request.getParameter("pinInputVote").equals(resultSet.first())){
                                    // can the user enter different PINS within the same session?
                                    System.out.println("The PIN was not equal to the one from your session!\n" +
                                            "We used the generated one from before to process the vote.");
                                }
                            }
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                        PollWrapper.manager.Vote(request.getSession().getId(), PollWrapper.manager.getChoices()[i]);
                    } catch (PollException pe) {
                        System.err.println(pe);
                    }
                    break;
                }
            }
            response.sendRedirect("home.jsp");
        }
    }
}
