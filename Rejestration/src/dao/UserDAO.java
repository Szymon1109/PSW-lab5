package dao;

import model.User;

public interface UserDAO {
    Boolean findUser(String query);
    Boolean findOne(String login, String haslo);
    Boolean findOne(String login);
    String whoIs(String login);
    void save(User user);
}
