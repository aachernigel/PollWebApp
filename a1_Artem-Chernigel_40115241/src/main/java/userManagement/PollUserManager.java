package userManagement;

import PollManagerLib.UserManagement;
import org.json.simple.JSONArray;

import java.io.*;
import java.util.LinkedList;
import java.util.Properties;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

public class PollUserManager implements UserManagement {

    @Override
    public boolean signUp(User user) {
        boolean finished = false;
        try {
            LinkedList<JSONObject> users = getUsers("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            for (JSONObject u : users) {
                if (u.get("userID").equals(user.getUserID()) || u.get("emailAddress").equals(user.getEmailAddress())) {
                    return false;
                }
            }

            org.json.JSONObject resultJson = new org.json.JSONObject();
            org.json.JSONArray arrOfUsers = new org.json.JSONArray();
            for (JSONObject u : users) {
                org.json.JSONObject userObj = new org.json.JSONObject();
                userObj.put("userID", u.get("userID"));
                userObj.put("firstName", u.get("firstName"));
                userObj.put("lastName", u.get("lastName"));
                userObj.put("emailAddress", u.get("emailAddress"));
                userObj.put("password", u.get("password"));
                userObj.put("verificationToken", u.get("verificationToken"));
                userObj.put("verified", u.get("verified"));
                arrOfUsers.put(userObj);
            }
            org.json.JSONObject newUser = new org.json.JSONObject();
            newUser.put("userID", user.getUserID());
            newUser.put("firstName", user.getFirstName());
            newUser.put("lastName", user.getLastName());
            newUser.put("emailAddress", user.getEmailAddress());
            newUser.put("password", user.getPassword());
            newUser.put("verificationToken", user.getVerificationToken());
            newUser.put("verified", "false");

            arrOfUsers.put(newUser);
            resultJson.put("users", arrOfUsers);

            FileWriter output = new FileWriter("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            output.write(resultJson.toString(4));
            output.flush();
            output.close();
            finished = true;
        } catch (IOException | ParseException e) {
            System.err.println(e);
        }
        if (finished)
            return true;
        else
            return false;
    }

    @Override
    public boolean forgotPassword() {
        return false;
    }

    @Override
    public boolean emailVerification(String userID, String verificationToken) {
        boolean verified = false;
        try {
            LinkedList<JSONObject> users = PollUserManager.getUsers("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            for (JSONObject u : users) {
                if (u.get("userID").equals(userID) && u.get("verificationToken").equals(verificationToken)) {
                    verified = true;
                }
            }
            if (!verified)
                return false;
            org.json.JSONObject resultJson = new org.json.JSONObject();
            org.json.JSONArray arrOfUsers = new org.json.JSONArray();
            for (JSONObject u : users) {
                org.json.JSONObject userObj = new org.json.JSONObject();
                userObj.put("userID", u.get("userID"));
                userObj.put("firstName", u.get("firstName"));
                userObj.put("lastName", u.get("lastName"));
                userObj.put("emailAddress", u.get("emailAddress"));
                userObj.put("password", u.get("password"));
                userObj.put("verificationToken", u.get("verificationToken"));
                if (u.get("userID").equals(userID) && verified)
                    userObj.put("verified", "true");
                else
                    userObj.put("verified", u.get("verified"));
                arrOfUsers.put(userObj);
            }
            resultJson.put("users", arrOfUsers);
            FileWriter output = new FileWriter("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            output.write(resultJson.toString(4));
            output.flush();
            output.close();
        } catch (IOException | ParseException e) {
            System.err.println(e);
        }
        return true;
    }

    @Override
    public boolean changePassword() {
        return false;
    }

    public static LinkedList<JSONObject> getUsers(String filename) throws IOException, ParseException {
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader(filename));
        JSONArray jsonArray = (JSONArray) jsonObject.get("users");
        LinkedList<JSONObject> users = new LinkedList<>();
        for (int i = 0; i < jsonArray.size(); i++)
            users.add((JSONObject) jsonArray.get(i));
        return users;
    }
}
