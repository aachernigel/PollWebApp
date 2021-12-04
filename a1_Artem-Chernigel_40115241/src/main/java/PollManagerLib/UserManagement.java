package PollManagerLib;

import userManagement.User;

public interface UserManagement {

    boolean signUp(User user);

    boolean forgotPassword();

    boolean emailVerification();

    boolean changePassword();

}
