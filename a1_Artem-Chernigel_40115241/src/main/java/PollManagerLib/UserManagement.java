package PollManagerLib;

import userManagement.EmailType;
import userManagement.User;

import javax.servlet.http.HttpServletRequest;

public interface UserManagement {

    boolean signUp(User user);

    boolean forgotPassword(User user);

    boolean emailVerification(String userID, String token, EmailType type, HttpServletRequest request);

    boolean changePassword(User user);

}
