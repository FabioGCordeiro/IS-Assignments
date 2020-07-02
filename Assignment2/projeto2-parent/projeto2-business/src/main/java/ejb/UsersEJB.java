package ejb;

import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.List;
import java.util.logging.Logger;

import javax.ejb.Stateless;
import javax.persistence.Query;
import javax.persistence.EntityExistsException;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import data.Item;
import data.User;
import data.Hash;

/**
 * Session Bean implementation class PlayersEJB
 */
@Stateless
public class UsersEJB implements UsersEJBRemote {
    @PersistenceContext(name = "User")
    EntityManager em;

    Logger logger = Logger.getLogger(UsersEJB.class.getName());

    public boolean createUser(String username, String password, String email, String country) {
        logger.info("Trying to create a new user");
        if (!username.equals("") && !password.equals("") && !email.equals("") && !country.equals("")) {
            Hash passwordHash = new Hash();
            String hashedPassword = "";
            try {
                hashedPassword = passwordHash.createHash(password);
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                logger.info("Password hash failed");
                return false;
            }
            logger.info("Password hash complete");
            User newUser = new User(username, hashedPassword, email, country);
            try {
                em.persist(newUser);
            } catch (EntityExistsException e) {
                logger.info("Email already in use");
                return false;
            }
            logger.info("New user created");
            return true;
        }
        return false;
    }

    public void deleteUser(String email){
        logger.info("Deleting a user");
        Query q = em.createQuery("from User u where u.email = :e");
        q.setParameter("e",email);
        User user = (User) q.getSingleResult();

        // DELETE USER ITEMS
        logger.info("Deleting user's items");
        if(!(user.getItems().isEmpty())){
            for (Item i : user.getItems()) {
                q = em.createQuery("delete Item item where item.id = :i");
                q.setParameter("i",i.getId()); 
                q.executeUpdate();
                logger.info("User items deleted");
            }
        }

        //DELETE USER
        logger.info("Deleting user");
        q = em.createQuery("delete User u where u.email = :e");
        q.setParameter("e",email);
        q.executeUpdate();

        logger.info("User deleted");
    }

    public boolean editUserInfo(String username, String password, String email, String country, String emailS){
        if(!username.equals("") && !password.equals("") && !email.equals("") && !country.equals("")){
            logger.info("Starting edit user information");
            Hash passwordHash = new Hash();
            String hashedPassword = "";
            try {
                hashedPassword = passwordHash.createHash(password);
                logger.info("Password hash complete");
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                logger.info("Password hash failed");
                e.printStackTrace();
            }

            Query q = em.createQuery("update User set username='" + username + "' where email = '"+ emailS+"'");
            q.executeUpdate();
            q = em.createQuery("update User set password='" + hashedPassword + "' where email = '"+ emailS+"'");
            q.executeUpdate();
            q = em.createQuery("update User set country='" + country + "' where email = '"+ emailS+"'");
            q.executeUpdate();
            q = em.createQuery("update User set email='" + email + "' where email = '"+ emailS+"'");
            q.executeUpdate();

            logger.info("User information edited");
            return true;
        }
        else{
            logger.info("Empty camps are not allowed");
            return false;
        }

        
    }

    public boolean checkUserLogin(String email, String password){
        logger.info("Validating Credentials");
        User user = null;
        try{
            Query q = em.createQuery("from User u where u.email = :e");
            q.setParameter("e",email);
            user = (User) q.getSingleResult();
            logger.info("User exists");
        }catch(NoResultException e){
            logger.severe("No user found");
            return false;
        }
        if(user!=null){
            Hash passwordHash = new Hash();
            try {
                if (passwordHash.validatePassword(password, user.getPassword())) {
                    logger.info("Credentials are valid");
                    return true;
                }
            } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
                logger.severe(e.toString());
            }
            logger.warning("Credentials are not valid");
            return false;
        }
        else{
            logger.warning("Credentials are not valid");
            return false;
        }
        
    }

    public List<User> getUsers(){
        try{
            Query q = em.createQuery("from User");
            List<User> users = q.getResultList();
            return users;
        }catch(NoResultException e){
            logger.severe("No user found");
            return null;
        }
    }

    public User getUser(String email){
        logger.info("Getting user: "+email);
        User user = null;
        try{
            Query q = em.createQuery("from User u where u.email = :e");
            q.setParameter("e",email);
            user = (User) q.getSingleResult();
            return user;
        }catch(NoResultException e){
            logger.severe("No user found");
            return null;
        }
    }
}