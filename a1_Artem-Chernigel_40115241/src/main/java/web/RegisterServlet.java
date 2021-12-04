package web;

import PollManagerLib.UserManager;
import userManagement.PollUserManager;
import userManagement.User;

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
        try{
            MessageDigest digest = MessageDigest.getInstance(LogInServlet.DECRYPTION_ALGORITHM);
            byte[] hashedBytes = digest.digest(request.getParameter("passwordRegister").getBytes(StandardCharsets.UTF_8));
            hashedPassword = LogInServlet.convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException e){
            System.err.println(e);
        }

        User user = new User(
                request.getParameter("userIDRegister"),
                request.getParameter("firstNameRegister"),
                request.getParameter("lastNameRegister"),
                request.getParameter("emailAddressRegister"),
                hashedPassword
        );
        UserManager userManager = new UserManager(new PollUserManager());
        userManager.getUserManagement().signUp(user);
        response.sendRedirect("index.jsp");
    }
}
