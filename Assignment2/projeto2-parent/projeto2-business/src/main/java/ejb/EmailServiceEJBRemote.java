package ejb;

import javax.ejb.Remote;
import javax.mail.PasswordAuthentication;

@Remote
public interface EmailServiceEJBRemote {
    public PasswordAuthentication getPasswordAuth(String serviceUsername, String servicePassword);
    //public void sendAccountActivationLinkToBuyer();
}