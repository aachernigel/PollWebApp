package web;

import PollManagerLib.PluginManager;
import PollManagerLib.PollPluginFactory;
import emailManagement.EmailGateway;
import emailManagement.EmailType;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import userManagement.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "ForgotPasswordServlet", value = "/ForgotPasswordServlet")
public class ForgotPasswordServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userID");
        String temporaryPassword = PollUserManager.createTemporaryPassword();
        String changePasswordToken = request.getParameter("changePasswordToken");
        PollPluginFactory pollPluginFactory = new PollPluginFactory();
        PluginManager userManager = pollPluginFactory.getPlugin(PollUserManager.class);
        request.setAttribute("password", temporaryPassword);
        userManager.getUserManagement().emailVerification(userID, changePasswordToken, EmailType.FORGOT_PASSWORD, temporaryPassword);
        request.getRequestDispatcher("forgotVerificationMessage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userIDForgotPassword");
        if (userID == null || userID.equals("")) {
            request.setAttribute("error", "User ID cannot be empty!");
        } else {
            User user = null;
            try {
                LinkedList<JSONObject> users = PollUserManager.getUsers("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
                for (JSONObject u : users) {
                    if (u.get("userID").equals(userID)) {
                        user = new User(
                                userID,
                                (String) u.get("firstName"), (String) u.get("lastName"),
                                (String) u.get("emailAddress"), (String) u.get("password")
                        );
                        user.setChangePasswordToken((String) u.get("changePasswordToken"));
                    }
                }
                if(user == null){
                    request.setAttribute("error", "Such User ID does not exist!");
                } else if (!user.getChangePasswordToken().equals("")) {
                    request.setAttribute("error", "Sorry, you already restored your password once!");
                } else {
                    PollPluginFactory pollPluginFactory = new PollPluginFactory();
                    PluginManager userManager = pollPluginFactory.getPlugin(PollUserManager.class);
                    PluginManager emailManager = pollPluginFactory.getPlugin(EmailGateway.class);
                    userManager.getUserManagement().forgotPassword(user);
                    request.setAttribute("tokenEmailed", emailManager.getEmailManagement().sendEmail(user, EmailType.FORGOT_PASSWORD));
                }
            } catch (IOException | ParseException e) {
                System.err.println(e);
            }
        }
        request.getRequestDispatcher("forgotPasswordAction.jsp").forward(request, response);
    }
}
