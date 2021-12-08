package emailManagement;

import PollManagerLib.EmailManagement;
import userManagement.User;

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
            MessageTransformer.transformMessage(message, user, emailType);
            Transport.send(message);
        } catch (MessagingException e) {
            System.err.println(e);
            return false;
        }
        return true;
    }
}