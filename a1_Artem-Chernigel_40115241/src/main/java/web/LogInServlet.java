package web;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import userManagement.Encryptor;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.LinkedList;

@WebServlet(name = "LogInServlet", value = "/LogInServlet")
// passwords
//      secretPassword
//      qwerty
//      123
public class LogInServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("userID", null);
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            String hash =  Encryptor.getEncryption(request.getParameter("passwordLogIn"));

            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");
            LinkedList<JSONObject> users = new LinkedList<>();
            for (int i = 0; i < jsonArray.size(); i++)
                users.add((JSONObject) jsonArray.get(i));
            boolean authentication = false;
            boolean verified = false;
            boolean active = false;

            for (JSONObject u : users) {
                if (u.get("userID").equals(request.getParameter("userIDLogIn")) &&
                        u.get("password").equals(hash)){
                    if(u.get("verified").equals("true")){
                        verified = true;
                    } else{
                        verified = false;
                    }
                    if(u.get("active").equals("true")){
                        active = true;
                    } else {
                        active = false;
                    }
                    authentication = true;
                }
            }
            if (authentication && verified && active) {
                HttpSession session = request.getSession(true);
                String userID = request.getParameter("userIDLogIn");
                session.setAttribute("userID", userID);
                response.sendRedirect("index.jsp");
            } else {
                if(!authentication){
                    request.setAttribute("error", "User-ID or Password does not match!");
                } else if(!verified) {
                    request.setAttribute("error", "Please verify your account first!");
                } else if(!active) {
                    request.setAttribute("error", "Please restore your password first!");
                }
                request.getRequestDispatcher("login.jsp").forward(request, response);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
