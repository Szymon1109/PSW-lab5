package dao;

import database.SQLConnection;
import javafx.collections.FXCollections;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Event;
import model.User;

public class EventDAOImpl implements EventDAO {

    @Override
    public List<Event> findAllEvents(){
        List<Event> data = FXCollections.observableArrayList();
        String query = "SELECT * FROM wydarzenia";
        SQLConnection conn = new SQLConnection();
        ResultSet rs = conn.makeQuery(query);

        try{
            while(rs.next()){
                Integer id = rs.getInt("id");
                String nazwa = rs.getString("nazwa");
                String agenda = rs.getString("agenda");
                String termin = rs.getString("termin");

                Event row = new Event(id, nazwa, agenda, termin);
                data.add(row);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            conn.closeConnect(rs);
        }
        return data;
    }

    @Override
    public List<Event> findEventsForUser(User user){
        List<Integer> ids = new ArrayList<>();
        String query1 = "SELECT id_wydarzenia FROM potwierdzenia WHERE id=" + user.getId() + ";";
        SQLConnection conn1 = new SQLConnection();
        ResultSet rs1 = conn1.makeQuery(query1);

        try{
            while(rs1.next()){
                Integer id_wydarzenia = rs1.getInt("id_wydarzenia");
                ids.add(id_wydarzenia);
            }
        } catch(SQLException e){
            e.printStackTrace();
        } finally{
            conn1.closeConnect();
        }

        List<Event> data = FXCollections.observableArrayList();
        for(Integer id : ids){
            String query2 = "SELECT * FROM wydarzenia WHERE id=" + id + ";";
            SQLConnection conn2 = new SQLConnection();
            ResultSet rs2 = conn2.makeQuery(query2);

            try{
                while(rs2.next()){
                    Integer id_wydarzenia = rs2.getInt("id_wydarzenia");
                    String nazwa = rs2.getString("nazwa");
                    String agenda = rs2.getString("agenda");
                    String termin = rs2.getString("termin");

                    Event event = new Event(id_wydarzenia, nazwa, agenda, termin);
                    data.add(event);
                }
            } catch(SQLException e){
                e.printStackTrace();
            } finally{
                conn2.closeConnect();
            }
        }
        return data;
    }

    @Override
    public void confirm(User user, Event event){
        String query = "INSERT INTO potwierdzenia(id_uzytkownika, id_wydarzenia) VALUES (" +
                user.getId() + ", " + event.getId() + ";";

        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }

    @Override
    public void save(Event event){
        String query = "INSERT INTO wydarzenia(nazwa, agenda, termin) VALUES ('" +
                event.getNazwa() + "', '" + event.getAgenda() + "', '" + event.getTermin() + "');";

        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }

    @Override
    public void update(Event event, String column, String newValue){
        String query = "UPDATE wydarzenia SET " + column + "='" +
                newValue + "' WHERE id=" + event.getId() + ";";

        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }

    @Override
    public void delete(Event event){
        String query = "DELETE FROM wydarzenia WHERE id=" + event.getId() + ";";

        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }
}