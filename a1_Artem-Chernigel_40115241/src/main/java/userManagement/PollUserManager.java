package userManagement;

import PollManagerLib.UserManagement;
import org.json.simple.JSONArray;

import java.io.*;
import java.util.LinkedList;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PollUserManager implements UserManagement {

    @Override
    public boolean signUp(User user) {
        boolean finished = false;
        try{
            JSONParser jsonParser = new JSONParser();
            JSONObject jsonObject = (JSONObject) jsonParser.parse(new FileReader("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json"));
            JSONArray jsonArray = (JSONArray) jsonObject.get("users");
            LinkedList<JSONObject> users = new LinkedList<>();
            for (int i = 0; i < jsonArray.size(); i++)
                users.add((JSONObject) jsonArray.get(i));
            for(JSONObject u : users){
                if(u.get("userID").equals(user.getUserID()) || u.get("emailAddress").equals(user.getEmailAddress())){
                    return false;
                }
            }

            org.json.JSONObject resultJson = new org.json.JSONObject();
            org.json.JSONArray arrOfUsers = new org.json.JSONArray();
            for(JSONObject u : users){
                org.json.JSONObject userObj = new org.json.JSONObject();
                userObj.put("userID", u.get("userID"));
                userObj.put("firstName", u.get("firstName"));
                userObj.put("lastName", u.get("lastName"));
                userObj.put("emailAddress", u.get("emailAddress"));
                userObj.put("password", u.get("password"));
                arrOfUsers.put(userObj);
            }
            org.json.JSONObject newUser = new org.json.JSONObject();
            newUser.put("userID", user.getUserID());
            newUser.put("firstName", user.getFirstName());
            newUser.put("lastName", user.getLastName());
            newUser.put("emailAddress", user.getEmailAddress());
            newUser.put("password", user.getPassword());

            arrOfUsers.put(newUser);
            resultJson.put("users", arrOfUsers);

            FileWriter output = new FileWriter("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            output.write(resultJson.toString(4));
            System.out.println(resultJson.toString(4));
            output.flush();
            output.close();
            finished = true;
        } catch (IOException | ParseException e){
            System.err.println(e);
        }
        if(finished)
            return true;
        else
            return false;
    }

    @Override
    public boolean forgotPassword() {
        return false;
    }

    @Override
    public boolean emailVerification() {
        return false;
    }

    @Override
    public boolean changePassword() {
        return false;
    }
}
