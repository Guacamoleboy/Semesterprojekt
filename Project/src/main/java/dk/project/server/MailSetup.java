/*

    Switched to MailSetupAPI since Digital Ocean blocks default ports for SMTP.
    See MailSetupAPI for config.

    ______________________________________________________

    DO NOT REMOVE THIS CLASS

    ______________________________________________________

    - Guac

*/

// Package
package dk.project.server;

// Imports
import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

public class MailSetup {

    // Attributes
    private static final String SMTP_HOST = "smtp-relay.brevo.com";
    private static final String SMTP_PORT = "587";
    private static final String OUR_EMAIL = "fog@travlr.dk";
    private static final String OUR_PASSWORD = "XX";

    // _________________________________________________

    private static Session createSession() {

        Properties props = new Properties();
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.host", SMTP_HOST);
        props.put("mail.smtp.port", SMTP_PORT);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.ssl.enable", "false");
        props.put("mail.smtp.connectiontimeout", "10000"); // Timeout Fix
        props.put("mail.smtp.timeout", "10000"); // Timeout Fix
        props.put("mail.smtp.writetimeout", "10000"); // Timeout Fix
        props.put("mail.debug", "true");

        return Session.getInstance(props, new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(OUR_EMAIL, OUR_PASSWORD);
            }
        });

    }

    // __________________________________________________________________

    public static boolean sendMail(String to, String subject, String body) {
        try {
            Session session = createSession();

            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(OUR_EMAIL));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);

            System.out.println("Sender mail til: " + to); // DEBUG
            Transport.send(message);
            System.out.println("Mail sendt!"); // DEBUG

            return true;

        } catch (MessagingException e) {
            e.printStackTrace();
            return false;
        }
    }

} // MailSetup end