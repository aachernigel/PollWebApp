package PollManagerLib;

import emailManagement.EmailType;
import userManagement.User;

public interface EmailManagement {

    boolean sendEmail(User user, EmailType emailType);

}
