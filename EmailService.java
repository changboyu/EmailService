import java.util.*;

import mail.*;

public class EmailService {
    static Properties props = System.getProperties();
    static Session session = null;

    public String connectAndSend(String serverName, String portNo, String secureConnection, String userName, String password, String toEmail, String subject, String msg, String[] attachFiles) {
        System.out.println("connecting");
        emailSettings(serverName, portNo, secureConnection);
        createSession(userName, password);
        System.out.println("sending");
        String issend = sendMessage(userName, toEmail, subject, msg, attachFiles);
        return issend;
    }

    public void emailSettings(String host, String port, String secureCon) {
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.auth", "true");
        props.put("mail.debug", "false");
        props.put("mail.smtp.port", port);
        if (secureCon.equalsIgnoreCase("tls")) {
            props.put("mail.smtp.starttls.enable", "true");
        } else if (secureCon.equalsIgnoreCase("ssl")) {
            props.put("mail.smtp.startssl.enable", "true");
        }
    }

    public void createSession(final String username, final String pass) {
        session = new Session();
        System.out.println("Session created");
        session.setDebug(true);
    }

    public String sendMessage(String fromEmail, String toEmail, String subject, String body, String[] attachFiles) {
        String msgsendresponse = "";

        try {

            MimeMessage mimeMessage = new MimeMessage(session);
            mimeMessage.setFrom(fromEmail);
            mimeMessage.addRecipient(toEmail);
            mimeMessage.setSubject(subject);
            mimeMessage.setBody(body);

            try {
                new Transport().send(mimeMessage);
                msgsendresponse = "Message_Sent";
                System.out.println(msgsendresponse);
            } catch (Exception e) {
                throw new MessagingException();
            }
        } catch (MessagingException ex) {
            msgsendresponse = "Email sending failed due to: " + ex.getLocalizedMessage();
            System.out.println(msgsendresponse);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return msgsendresponse;
    }
}

