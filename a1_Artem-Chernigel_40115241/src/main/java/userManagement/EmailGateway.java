package userManagement;

import PollManagerLib.EmailManagement;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class EmailGateway implements EmailManagement {
    @Override
    public boolean sendEmail(User user, EmailType emailType) {
        String recipient = user.getEmailAddress();
        String sender = "ttayadamson@gmail.com";
        String password = "tayloradamson12345";

        System.out.println("Sending email from " + sender + " to " + recipient);

        Properties properties = new Properties();
        properties.put("mail.smtp.auth", "true");
        properties.put("mail.smtp.starttls.enable", "true");
        properties.put("mail.smtp.host", "smtp.gmail.com");
        properties.put("mail.smtp.port", "587");

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(sender, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(sender));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(recipient));
            if (emailType == EmailType.ACCOUNT_CREATION) {
                message.setSubject("Email Verification");
                message.setText(
                        "Hey, " + user.getFirstName() + " " + user.getLastName() + "!\n" +
                                "\tThank you so much for creating your account on our website! For security purposes, " +
                                "we would like to verify your email account." +
                                "\nPlease click on the link below in order to verify:\n" +
                                "http://localhost:8978/a1_Artem_Chernigel_40115241_war_exploded/EmailVerificationServlet?userID="
                                + user.getUserID() + "&verificationToken=" + user.getVerificationToken()
                );
            } else if (emailType == EmailType.FORGOT_PASSWORD) {
                message.setSubject("Forgot Password");
                message.setText(
                        "Hey, " + user.getFirstName() + " " + user.getLastName() + "!\n" +
                                "\tWe are sorry to hear that you forgot your password. But do not worry, we got you!\n" +
                                "\tPlease click on the link below in order to restore your password:\n" +
                                "http://localhost:8978/a1_Artem_Chernigel_40115241_war_exploded/ForgotPasswordServlet?userID="
                                + user.getUserID() + "&changePasswordToken=" + user.getChangePasswordToken()
                );
            }
            Transport.send(message);
        } catch (MessagingException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }
}