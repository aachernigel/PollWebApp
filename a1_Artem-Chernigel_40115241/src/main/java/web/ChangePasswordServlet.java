package web;

import PollManagerLib.PluginManager;
import org.json.simple.JSONObject;
import org.json.simple.parser.ParseException;
import userManagement.Encryptor;
import userManagement.PollPluginFactory;
import userManagement.PollUserManager;
import userManagement.User;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "ChangePasswordServlet", value = "/ChangePasswordServlet")
public class ChangePasswordServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String userID = request.getParameter("userIDChangePassword");
        String prevPassword = request.getParameter("prevPasswordChangePassword");
        String newPassword = request.getParameter("newPasswordChangePassword");
        User user = null;
        boolean verified = false;
        try{
            LinkedList<JSONObject> users = PollUserManager.getUsers("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            for (JSONObject u : users) {
                if(u.get("userID").equals(userID)){
                    if(Encryptor.getEncryption(prevPassword).equals(u.get("password"))){
                        verified = true;
                        user = new User(userID,
                                (String) u.get("firstName"), (String) u.get("lastName"),
                                (String) u.get("emailAddress"), Encryptor.getEncryption(newPassword));
                    }
                }
            }
            if(!verified){
                request.setAttribute("error", "Oops! The previous password is not matching!");
            } else{
                PollPluginFactory pollPluginFactory = new PollPluginFactory();
                PluginManager userManager = pollPluginFactory.getPlugin(PollUserManager.class);
                userManager.getUserManagement().changePassword(user);
                request.setAttribute("passwordChanged", true);
            }
        } catch (IOException | ParseException e){
            System.err.println(e);
        }
        request.getRequestDispatcher("changePassword.jsp").forward(request, response);
    }
}
