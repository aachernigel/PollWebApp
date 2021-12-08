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

@WebServlet(name = "RegisterServlet", value = "/RegisterServlet")
public class RegisterServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("userIDRegister") == null || request.getParameter("userIDRegister").equals("")) {
            request.setAttribute("error", "User ID cannot be empty!");
        } else if (request.getParameter("firstNameRegister") == null || request.getParameter("firstNameRegister").equals("")) {
            request.setAttribute("error", "First name cannot be empty!");
        } else if (request.getParameter("lastNameRegister") == null || request.getParameter("lastNameRegister").equals("")) {
            request.setAttribute("error", "Last name cannot be empty!");
        } else if (request.getParameter("emailAddressRegister") == null || request.getParameter("emailAddressRegister").equals("")) {
            request.setAttribute("error", "Email address cannot be empty!");
        } else if (request.getParameter("passwordRegister") == null || request.getParameter("passwordRegister").equals("")) {
            request.setAttribute("error", "Password cannot be empty!");
        } else {
            boolean found = false;
            try{
                LinkedList<JSONObject> users = PollUserManager.getUsers("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
                for (JSONObject u : users) {
                    if(u.get("userID").equals(request.getParameter("userIDRegister"))){
                        request.setAttribute("error", "Such userID already exists!");
                        found = true;
                    } else if(u.get("emailAddress").equals(request.getParameter("emailAddressRegister"))){
                        request.setAttribute("error", "Such email address already has an account!");
                        found = true;
                    }
                }
            } catch (IOException | ParseException e){
                System.err.println(e);
                request.setAttribute("error", "Oops... Something went completely wrong!");
                found = true;
            }
            if(!found){
                User user = new User(
                        request.getParameter("userIDRegister"),
                        request.getParameter("firstNameRegister"),
                        request.getParameter("lastNameRegister"),
                        request.getParameter("emailAddressRegister"),
                        request.getParameter("passwordRegister")
                );
                PollPluginFactory pollPluginFactory = new PollPluginFactory();
                PluginManager userManager = pollPluginFactory.getPlugin(PollUserManager.class);
                PluginManager emailManager = pollPluginFactory.getPlugin(EmailGateway.class);
                userManager.getUserManagement().signUp(user);
                emailManager.getEmailManagement().sendEmail(user, EmailType.ACCOUNT_CREATION);
                request.setAttribute("registered", "true");
            }
        }
        request.getRequestDispatcher("register.jsp").forward(request, response);
    }
}
