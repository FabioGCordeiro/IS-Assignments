package ejb;

import java.util.List;

import javax.ejb.Remote;

import data.User;

@Remote
public interface UsersEJBRemote {
    public boolean createUser(String username, String password, String email, String country);
    public boolean checkUserLogin(String email, String password);
    public boolean editUserInfo(String username, String password, String email, String country, String emailS);
    public void deleteUser(String email);
    public List<User> getUsers();
    public User getUser(String email);
}