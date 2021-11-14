package web;

import PollManagerLib.PollException;
import PollManagerLib.PollManager;
import PollManagerLib.PollWrapper;
import com.google.protobuf.Message;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javax.servlet.*;
import javax.servlet.http.*;
import javax.servlet.annotation.*;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;
import java.util.LinkedList;

@WebServlet(name = "LogInServlet", value = "/LogInServlet")
// passwords
//      secretPassword
//      qwerty
//      123
public class LogInServlet extends HttpServlet {
    private static int attempts = 3;
    private static String DECRYPTION_ALGORITHM = "MD5";

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.getSession().setAttribute("userID", null);
        response.sendRedirect("index.jsp");
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        try {
            MessageDigest digest = MessageDigest.getInstance(DECRYPTION_ALGORITHM);
            byte[] hashedBytes = digest.digest(request.getParameter("passwordLogIn").getBytes(StandardCharsets.UTF_8));
            String hash = convertByteArrayToHexString(hashedBytes);

            JSONParser jsonParser = new JSONParser();
            // Is it necessary to make a relative path?
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");
            LinkedList<JSONObject> users = new LinkedList<>();
            for (int i = 0; i < jsonArray.size(); i++)
                users.add((JSONObject) jsonArray.get(i));
            boolean authentication = false;

            for (JSONObject u : users) {
                if (u.get("userID").equals(request.getParameter("userIDLogIn")) &&
                        u.get("password").equals(hash))
                    authentication = true;
            }
            if (authentication) {
                HttpSession session = request.getSession(true);
                String userID = request.getParameter("userIDLogIn");
                session.setAttribute("userID", userID);
                response.sendRedirect("index.jsp");
            } else {
                request.setAttribute("error", "User-ID or Password does not match!");
                request.getRequestDispatcher("index.jsp").forward(request, response);
            }

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String convertByteArrayToHexString(byte[] arrayBytes) {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < arrayBytes.length; i++) {
            stringBuffer.append(Integer.toString((arrayBytes[i] & 0xff) + 0x100, 16)
                    .substring(1));
        }
        return stringBuffer.toString();
    }
}
