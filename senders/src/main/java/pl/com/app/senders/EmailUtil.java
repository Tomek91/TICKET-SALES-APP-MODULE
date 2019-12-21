package pl.com.app.senders;


import pl.com.app.exceptions.MyException;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.FileInputStream;
import java.util.Properties;

public class EmailUtil {

    private static String PROPERTIES_NAME = "mail.properties";

    public static boolean send(String emailTo, String subject, String htmlText) {
        try {
            Properties props = new Properties();
            props.load(new FileInputStream(PROPERTIES_NAME));

            final String username = props.getProperty("mail.smtp.user");
            final String password = props.getProperty("mail.smtp.password");
            final String emailFrom = props.getProperty("mail.smtp.from");

            Session session = Session.getInstance(props, new Authenticator() {
                @Override
                protected PasswordAuthentication getPasswordAuthentication() {
                    return new PasswordAuthentication(username, password);
                }
            });

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(emailFrom));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(emailTo));
            message.setSubject(subject);
            message.setText(htmlText);

            System.out.println("Trwa wysyłanie emaila...");
            Transport.send(message);

            System.out.println("Mail wysłano pomyślnie.");
        } catch (Exception e) {
            e.printStackTrace();
            throw new MyException("SEND EMAIL ERROR");
        }

        return true;
    }
}
