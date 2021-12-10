package emailManagement;

import PollManagerLib.EmailManagement;
import userManagement.User;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class EmailGateway implements EmailManagement {
    @Override
    public boolean sendEmail(User user, EmailType emailType) {
        String recipient = user.getEmailAddress();
        Properties properties = new Properties();
        InputStream inputStream;
        try {
            inputStream = new FileInputStream("C:\\Users\\Admin\\IdeaProjects\\PollWebApp\\a1_Artem-Chernigel_40115241\\src\\main\\resources\\config.properties");
            properties.load(inputStream);
        } catch (FileNotFoundException e) {
            System.err.println(e);
            return false;
        } catch (IOException e) {
            System.err.println(e);
            return false;
        }
        String sender = properties.getProperty("email_sender_login");
        String password = properties.getProperty("email_sender_password");

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