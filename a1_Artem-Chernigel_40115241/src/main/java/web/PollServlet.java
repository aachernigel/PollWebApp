package web;

import PollManagerLib.Choice;
import PollManagerLib.PollException;
import PollManagerLib.PollWrapper;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.*;
import java.sql.*;
import java.time.LocalDateTime;

@WebServlet(name = "PollServlet", value = "/PollServlet")
public class PollServlet extends HttpServlet {
    private final String FORMATS = ".txt.json.xml";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("pollNameDownload") != null && request.getParameter("pollFormatDownload") != null) {
            if (!request.getParameter("pollNameDownload").equals(PollWrapper.manager.getName())) {
                System.err.println("ERROR: Poll name does not match in doGet");
                response.sendRedirect("home.jsp");
            } else if (!FORMATS.contains(request.getParameter("pollFormatDownload"))) {
                System.err.println("ERROR: Download format does not match in doGet");
                response.sendRedirect("home.jsp");
            } else {
                String filename = PollWrapper.manager.getName() + "-" + PollWrapper.manager.getDate() + request.getParameter("pollFormatDownload");
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
                        Statement statement = DBConnection.connection.createStatement();
                        statement.executeUpdate(
                                "INSERT INTO poll(pollID, name, question, status, creatorID, " + columnsStr + ") VALUES (" +
                                        "\"" + PollWrapper.manager.getPollID() + "\"" +
                                        ", \"" + PollWrapper.manager.getName() + "\"" +
                                        ", \"" + PollWrapper.manager.getQuestion() + "\"" +
                                        ", \"" + PollWrapper.manager.getStatus().toString() + "\"" +
                                        ", \"" + request.getSession().getAttribute("userID") + "\"" +
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
                        // update on DB side
                        PollWrapper.manager.UpdatePoll(
                                request.getParameter("pollNameUpdate"), request.getParameter("pollQuestionUpdate"),
                                arr
                        );
                        DBPollGateway.dbPoll.updatePoll();
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
                            PreparedStatement preparedStatement = DBConnection.connection.prepareStatement("SELECT * FROM vote WHERE sessionID = ? AND pollID = ?");
                            preparedStatement.setString(1, request.getSession().getId());
                            preparedStatement.setString(2, PollWrapper.manager.getPollID());
                            ResultSet rsSessionIDAndPollID = preparedStatement.executeQuery();

                            preparedStatement = DBConnection.connection.prepareStatement("SELECT * FROM vote WHERE pollID = ?");
                            preparedStatement.setString(1, PollWrapper.manager.getPollID());
                            ResultSet rsPollID = preparedStatement.executeQuery();
                            boolean foundPIN = false;
                            String tempPIN = "";
                            while (rsPollID.next()) {
                                if (rsPollID.getString("pin").equals(request.getParameter("pinInputVote"))) {
                                    foundPIN = true;
                                    tempPIN = rsPollID.getString("pin");
                                }
                            }
                            // If the user has not been voting before
                            if (!rsSessionIDAndPollID.first()) {
                                // If user did not put anything for the PIN input
                                if (request.getParameter("pinInputVote").equals("")) {
                                    System.out.println("Generating a PIN for the User for this Poll...");
                                    PollWrapper.manager.generatePIN();
                                    DBPollGateway.dbPoll.insertVote(
                                            PollWrapper.manager.getPollID(),
                                            request.getSession().getId(),
                                            PollWrapper.manager.getPin(),
                                            PollWrapper.manager.getChoices()[i].getDescription(),
                                            LocalDateTime.now()
                                    );
                                } else {
                                    if (foundPIN) {
                                        DBPollGateway.dbPoll.updateVote(
                                                PollWrapper.manager.getPollID(),
                                                tempPIN,
                                                PollWrapper.manager.getChoices()[i].getDescription(),
                                                LocalDateTime.now()
                                        );
                                    } else {
                                        System.out.println("There is no such PIN!");
                                    }
                                }
                                DBConnection.closeConnection();
                            } else if (rsSessionIDAndPollID.first()) {
                                if (request.getParameter("pinInputVote").equals("")) {
                                    System.out.println("Processing a Vote with the pre-generated PIN!");
                                    DBPollGateway.dbPoll.updateVote(
                                            PollWrapper.manager.getPollID(),
                                            PollWrapper.manager.getPin(),
                                            PollWrapper.manager.getChoices()[i].getDescription(),
                                            LocalDateTime.now()
                                    );
                                } else {
                                    if (foundPIN) {
                                        DBPollGateway.dbPoll.updateVote(
                                                PollWrapper.manager.getPollID(),
                                                tempPIN,
                                                PollWrapper.manager.getChoices()[i].getDescription(),
                                                LocalDateTime.now()
                                        );
                                    } else {
                                        System.out.println("There is no such PIN!");
                                    }
                                }
                                DBConnection.closeConnection();
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
