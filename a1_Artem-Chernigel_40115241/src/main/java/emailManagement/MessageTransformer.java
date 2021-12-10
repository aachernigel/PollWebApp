package emailManagement;

import userManagement.User;

import javax.mail.*;

public class MessageTransformer {
    public static void transformMessage(Message message, User user, EmailType type) throws MessagingException {
        if (type.equals(EmailType.ACCOUNT_CREATION)) {
            message.setSubject("Email Verification");
            message.setText(
                    "Hey, " + user.getFirstName() + " " + user.getLastName() + "!\n" +
                            "\tThank you so much for creating your account on our website! For security purposes, " +
                            "we would like to verify your email account." +
                            "\n\tPlease click on the link below in order to verify:\n" +
                            "http://localhost:8978/a1_Artem_Chernigel_40115241_war_exploded/EmailVerificationServlet?userID="
                            + user.getUserID() + "&verificationToken=" + user.getVerificationToken()
            );
        } else if (type.equals(EmailType.FORGOT_PASSWORD)) {
            message.setSubject("Forgot Password");
            message.setText(
                    "Hey, " + user.getFirstName() + " " + user.getLastName() + "!\n" +
                            "\tWe are sorry to hear that you forgot your password. But do not worry, we got you!\n" +
                            "\tPlease click on the link below in order to restore your password:\n" +
                            "http://localhost:8978/a1_Artem_Chernigel_40115241_war_exploded/ForgotPasswordServlet?userID="
                            + user.getUserID() + "&changePasswordToken=" + user.getChangePasswordToken()
            );
        }
    }
}
