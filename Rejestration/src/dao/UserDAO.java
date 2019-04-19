package dao;

import java.util.List;
import model.User;

public interface UserDAO {
    Boolean findOne(String login, String haslo);
    Boolean findOne(String login);

    String whoIs(String login);
    List findAllUsers();

    void save(User user);
    void changePassword(User user, String newPassword);
    void delete(User user);
}
