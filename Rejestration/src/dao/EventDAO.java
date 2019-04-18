package dao;

import model.Event;
import model.User;
import java.util.List;
import java.util.Optional;

public interface EventDAO {
    List findAllEvents();
    List findEventsForUser(User user);

    void confirm(User user, Event event);

    void save(Event event);
    void update(Event event, String column, String newValue);
    void delete(Event event);
}
