package dao;

import java.util.List;
import model.Event;
import model.User;

public interface EventDAO {
    List findAllEvents();
    List findEventsForUser(User user);
    Boolean findOne(String nazwa);

    void confirm(User user, Event event);

    void save(Event event);
    void update(Integer id, String column, String newValue);
    void delete(String nazwa);
}
