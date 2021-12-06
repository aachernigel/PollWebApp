package web;

import PollManagerLib.PluginManager;
import userManagement.*;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hashedPassword = "";
        hashedPassword = Encryptor.getEncryption(request.getParameter("passwordRegister"));
        User user = new User(
                request.getParameter("userIDRegister"),
                request.getParameter("firstNameRegister"),
                request.getParameter("lastNameRegister"),
                request.getParameter("emailAddressRegister"),
                hashedPassword
        );
        user.generateVerificationToken();
        PollPluginFactory pollPluginFactory = new PollPluginFactory();
        PluginManager userManager = pollPluginFactory.getPlugin(PollUserManager.class);
        PluginManager emailManager = pollPluginFactory.getPlugin(EmailGateway.class);
        userManager.getUserManagement().signUp(user);
        emailManager.getEmailManagement().sendEmail(user, EmailType.ACCOUNT_CREATION);
        request.setAttribute("registered", "true");
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}
