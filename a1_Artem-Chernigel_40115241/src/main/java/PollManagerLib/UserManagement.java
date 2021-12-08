package PollManagerLib;

import emailManagement.EmailType;
import userManagement.User;

public interface UserManagement {

    boolean signUp(User user);

    boolean forgotPassword(User user);

    boolean emailVerification(String userID, String token, EmailType type, String temporaryPassword);

    boolean changePassword(User user);

}
