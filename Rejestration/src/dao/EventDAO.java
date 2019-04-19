package dao;

import java.util.List;
import model.Event;
import model.User;

public interface EventDAO {
    List findAllEvents();
    List findEventsForUser(User user);

    void confirm(User user, Event event);

    void save(Event event);
    void update(Event event, String column, String newValue);
    void delete(Event event);
}
