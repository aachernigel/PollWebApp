package web;

import PollManagerLib.PluginManager;
import userManagement.EmailType;
import userManagement.PollPluginFactory;
import userManagement.PollUserManager;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;

@WebServlet(name = "EmailVerificationServlet", value = "/EmailVerificationServlet")
public class EmailVerificationServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String verificationToken = request.getParameter("verificationToken");
        String userID = request.getParameter("userID");
        PollPluginFactory pollPluginFactory = new PollPluginFactory();
        PluginManager userManager = pollPluginFactory.getPlugin(PollUserManager.class);
        request.setAttribute("accountIsVerified", userManager.getUserManagement().emailVerification(userID, verificationToken, EmailType.ACCOUNT_CREATION, request));
        request.getRequestDispatcher("emailVerificationMessage.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
