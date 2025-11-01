package dao;
import jakarta.mail.*;
import jakarta.mail.internet.*;
import java.util.Properties;

public class EmailSender {
    public static boolean send(String toEmail, String code) {
        String from = "emanuelmalbarosa4@gmail.com"; 
        System.out.println(toEmail);
        String password = "kpilzbrrejcsifyx";     // Google gave this password, NOT your real password, I forgot how to get this from the google account you'll use, watch youtube = jakartamail sender tutorial

        // Set SMTP properties
        Properties props = new Properties();
        props.put("mail.smtp.host", "smtp.gmail.com");
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.starttls.enable", "true");

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(from, password);
            }
        });

        try {
            // Ready the message
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(toEmail));
            message.setSubject("Password Reset Confirmation");
            message.setText("The code is "+code+"\n Do not share this with anyone :).");
            
            // Send it
            Transport.send(message);
            System.out.println("✅ Email sent successfully!");

        } catch (MessagingException e) {
            e.printStackTrace();
        }
        return true;
    }
}
