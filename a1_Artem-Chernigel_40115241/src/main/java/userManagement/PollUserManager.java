package userManagement;

import PollManagerLib.UserManagement;
import emailManagement.EmailType;
import org.json.simple.JSONArray;

import java.io.*;
import java.util.LinkedList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class PollUserManager implements UserManagement {
    private final static int TEMPORARY_PASSWORD_LENGTH = 13;

    @Override
    public boolean signUp(User user) {
        try {
            LinkedList<JSONObject> users = getUsers("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            for (JSONObject u : users) {
                if (u.get("userID").equals(user.getUserID()) || u.get("emailAddress").equals(user.getEmailAddress())) {
                    return false;
                }
            }
            user.generateVerificationToken();

            org.json.JSONObject resultJson = new org.json.JSONObject();
            org.json.JSONArray arrOfUsers = new org.json.JSONArray();
            for (JSONObject u : users) {
                org.json.JSONObject userObj = setUserValues(u);
                userObj.put("verified", u.get("verified"));
                userObj.put("active", u.get("active"));
                userObj.put("changePasswordToken", u.get("changePasswordToken"));
                arrOfUsers.put(userObj);
            }
            org.json.JSONObject newUser = new org.json.JSONObject();
            newUser.put("userID", user.getUserID());
            newUser.put("firstName", user.getFirstName());
            newUser.put("lastName", user.getLastName());
            newUser.put("emailAddress", user.getEmailAddress());
            newUser.put("password", Encryptor.getEncryption(user.getPassword()));
            newUser.put("verificationToken", user.getVerificationToken());
            newUser.put("verified", "false");
            newUser.put("active", "false");
            newUser.put("changePasswordToken", "");

            arrOfUsers.put(newUser);
            resultJson.put("users", arrOfUsers);

            FileWriter output = new FileWriter("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            output.write(resultJson.toString(4));
            output.flush();
            output.close();
        } catch (IOException | ParseException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean forgotPassword(User user) {
        if (!user.getChangePasswordToken().equals(""))
            return false;
        user.generateChangePasswordToken();
        try {
            LinkedList<JSONObject> users = PollUserManager.getUsers("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");

            org.json.JSONObject resultJson = new org.json.JSONObject();
            org.json.JSONArray arrOfUsers = new org.json.JSONArray();
            for (JSONObject u : users) {
                org.json.JSONObject userObj = setUserValues(u);
                userObj.put("verified", u.get("verified"));
                if (u.get("userID").equals(user.getUserID())) {
                    userObj.put("active", "false");
                    userObj.put("changePasswordToken", user.getChangePasswordToken());
                } else {
                    userObj.put("active", u.get("active"));
                    userObj.put("changePasswordToken", u.get("changePasswordToken"));
                }
                arrOfUsers.put(userObj);
            }
            resultJson.put("users", arrOfUsers);
            FileWriter output = new FileWriter("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            output.write(resultJson.toString(4));
            output.flush();
            output.close();
        } catch (IOException | ParseException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean emailVerification(String userID, String token, EmailType type, String temporaryPassword) {
        boolean verified = false;
        try {
            LinkedList<JSONObject> users = PollUserManager.getUsers("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            for (JSONObject u : users) {
                if (u.get("userID").equals(userID)) {
                    if (type.equals(EmailType.ACCOUNT_CREATION)) {
                        if (u.get("verificationToken").equals(token))
                            verified = true;
                    } else if (type.equals(EmailType.FORGOT_PASSWORD)) {
                        if (u.get("changePasswordToken").equals(token))
                            verified = true;
                    }
                }
            }

            if (!verified)
                return false;

            org.json.JSONObject resultJson = new org.json.JSONObject();
            org.json.JSONArray arrOfUsers = new org.json.JSONArray();
            for (JSONObject u : users) {
                org.json.JSONObject userObj = setUserValues(u);
                userObj.put("changePasswordToken", u.get("changePasswordToken"));
                if (type.equals(EmailType.ACCOUNT_CREATION)) {
                    if (u.get("userID").equals(userID)) {
                        userObj.put("verified", "true");
                        userObj.put("active", "true");
                    } else {
                        userObj.put("verified", u.get("verified"));
                        userObj.put("active", u.get("active"));
                    }
                } else if (type.equals(EmailType.FORGOT_PASSWORD)) {
                    userObj.put("verified", u.get("verified"));
                    if (u.get("userID").equals(userID)) {
                        String hash = Encryptor.getEncryption(temporaryPassword);

                        userObj.put("active", "true");
                        userObj.remove("password");
                        userObj.put("password", hash);
                    } else {
                        userObj.put("active", u.get("active"));
                    }
                }

                arrOfUsers.put(userObj);
            }
            resultJson.put("users", arrOfUsers);
            FileWriter output = new FileWriter("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            output.write(resultJson.toString(4));
            output.flush();
            output.close();
        } catch (IOException | ParseException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }

    @Override
    public boolean changePassword(User user) {
        try {
            LinkedList<JSONObject> users = getUsers("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            org.json.JSONObject resultJson = new org.json.JSONObject();
            org.json.JSONArray arrOfUsers = new org.json.JSONArray();
            user.setPassword(Encryptor.getEncryption(user.getPassword()));
            for (JSONObject u : users) {
                org.json.JSONObject userObj = setUserValues(u);
                userObj.put("verified", u.get("verified"));
                userObj.put("active", u.get("active"));
                userObj.put("changePasswordToken", u.get("changePasswordToken"));
                if (u.get("userID").equals(user.getUserID())) {
                    userObj.remove("password");
                    userObj.put("password", user.getPassword());
                }
                arrOfUsers.put(userObj);
            }
            resultJson.put("users", arrOfUsers);
            FileWriter output = new FileWriter("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\webapp\\users\\userInfo.json");
            output.write(resultJson.toString(4));
            output.flush();
            output.close();
        } catch (IOException | ParseException e) {
            System.err.println(e);
            return false;
        }
        return true;
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

    // Sets the values that do not need an extra if statement
    public static org.json.JSONObject setUserValues(JSONObject u) {
        org.json.JSONObject userObj = new org.json.JSONObject();
        userObj.put("userID", u.get("userID"));
        userObj.put("firstName", u.get("firstName"));
        userObj.put("lastName", u.get("lastName"));
        userObj.put("emailAddress", u.get("emailAddress"));
        userObj.put("password", u.get("password"));
        userObj.put("verificationToken", u.get("verificationToken"));
        return userObj;
    }

    public static String createTemporaryPassword() {
        String alphabet = "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789_!";
        String temporaryPassword = "";
        int randomIndex;
        for (int i = 0; i < TEMPORARY_PASSWORD_LENGTH; i++) {
            randomIndex = (int) (Math.random() * alphabet.length());
            temporaryPassword += alphabet.charAt(randomIndex);
        }
        return temporaryPassword;
    }
}
