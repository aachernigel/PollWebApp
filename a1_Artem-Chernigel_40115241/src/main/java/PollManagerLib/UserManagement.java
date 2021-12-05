package PollManagerLib;

import userManagement.EmailType;
import userManagement.User;

public interface UserManagement {

    boolean signUp(User user);

    boolean forgotPassword();

    boolean emailVerification(String userID, String verificationToken);

    boolean changePassword();

}
