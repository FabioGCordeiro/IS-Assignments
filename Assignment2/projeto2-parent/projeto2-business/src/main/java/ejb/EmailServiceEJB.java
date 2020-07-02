package ejb;

import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.logging.Logger;

import javax.ejb.EJB;
import javax.ejb.Schedule;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.mail.Address;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.SendFailedException;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import data.Item;
import data.User;
import ejb.ItemsEJBRemote;
import ejb.UsersEJBRemote;

@Startup
@Singleton
public class EmailServiceEJB extends Authenticator implements EmailServiceEJBRemote {

@EJB
ItemsEJBRemote ejbremote;

@EJB
UsersEJBRemote usersejbremote;

Logger logger = Logger.getLogger(EmailServiceEJB.class.getName());

public PasswordAuthentication getPasswordAuth(String serviceUsername, String servicePassword) {
    return new PasswordAuthentication(serviceUsername, servicePassword);
}

@Schedule(second = "30", minute="*", hour="*", info="Envia Email",persistent = false)
public void sendAccountActivationLinkToBuyer() {
        // OUR EMAIL SETTINGS
        String host = "smtp.gmail.com";// Gmail
        int port = 465;
        String serviceUsername = "mybayis1920@gmail.com";
        String servicePassword = "mybayis2019";// Our Gmail password

        Properties props = new Properties();
        props.put("mail.smtp.user", serviceUsername);
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.starttls.required", "true");
        props.put("mail.smtp.auth", "true");
        props.put("mail.smtp.socketFactory.port", port);
        props.put("mail.smtp.socketFactory.class", "javax.net.ssl.SSLSocketFactory");
        props.put("mail.smtp.socketFactory.fallback", "false");

        Authenticator authenticator = new Authenticator() {
            protected PasswordAuthentication getPasswordAuthentication() {
                return getPasswordAuth(serviceUsername, servicePassword);
            }
        };

        Session session = Session.getInstance(props, authenticator);

        // Destination of the email
        List<User> users = usersejbremote.getUsers();

        InternetAddress [] emails = new InternetAddress[users.size()];
        int i=0;
        for (User user : users) {
            try {
                emails[i] = new InternetAddress(user.getEmail());
                i++;
            } catch (AddressException e) {
                logger.severe("Send email failed. No users.");
                
            }
        }


        String from = serviceUsername;

        //GETS THE NEWEST ITEMS
        List<Item> newestItems = ejbremote.getNewestItems();

        String finalMessage = "<h2>Are you interested in buying anything? Here are our newest items!</h2><br>";

        for (Item item : newestItems) {
            finalMessage += item.toString() + "<br><br><br>";
        }

        try {
            Message message = new MimeMessage(session);
            // From: is our service
            message.setFrom(new InternetAddress(from));
            // To: destination given
            message.setRecipients(Message.RecipientType.TO, emails);
            message.setSubject("MyBay Catalog - Our 3 newest items");
            // Instead of simple text, a .html template should be added here!
            message.setContent(finalMessage, "text/html; charset=UTF-8");

            Transport transport = session.getTransport("smtp");
            transport.connect(host, port, serviceUsername, servicePassword);
            Transport.send(message, message.getAllRecipients());
            transport.close();

            logger.info("Catalogue sent");

        }catch (AddressException | SendFailedException e) {
            logger.severe("Send email failed. No users.");
        }
        catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }
}