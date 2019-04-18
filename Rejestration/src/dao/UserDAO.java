package dao;

import model.Event;
import model.User;

import java.util.List;

public interface UserDAO {

    Boolean findOne(String login, String haslo);
    Boolean findOne(String login);

    String whoIs(String login);

    List findAllUsers();
    List findAllEvents();

    void save(User user);
    void save(Event event);

    void changePassword(User user, String newPassword);
    void update(Event event);

    void delete(User user);
    void delete(Event event);

    void confirm(User user, Event event);
}
