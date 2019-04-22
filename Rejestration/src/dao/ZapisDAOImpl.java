package dao;

import database.SQLConnection;
import javafx.collections.FXCollections;
import model.Event;
import model.User;
import model.Zapis;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ZapisDAOImpl implements ZapisDAO{

    @Override
    public void confirm(Integer id){
        String query = "UPDATE zapisy SET zgoda='tak' WHERE id=" + id + ";";

        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }

    @Override
    public void reject(Integer id){
        String query = "UPDATE zapisy SET zgoda='nie' WHERE id=" + id + ";";

        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }

    @Override
    public List<Zapis> findAllConfirmedZapis(){
        String query = "SELECT * FROM zapisy WHERE zgoda='tak'";

        return getData(query);
    }

    @Override
    public List<Zapis> findAllNotConfirmedZapis(){
        String query = "SELECT * FROM zapisy WHERE zgoda IS NULL";

        return getData(query);
    }

    public List<Zapis> getData(String query) {
        List<Zapis> data = FXCollections.observableArrayList();
        SQLConnection conn = new SQLConnection();
        ResultSet rs = conn.makeQuery(query);

        try {
            while (rs.next()) {
                Integer id = rs.getInt("id");
                Integer id_uzytkownika = rs.getInt("id_uzytkownika");
                Integer id_wydarzenia = rs.getInt("id_wydarzenia");
                String typUczestnictwa = rs.getString("typ_uczestnictwa");
                String wyzywienie = rs.getString("wyzywienie");

                Zapis zapis = new Zapis(id, id_uzytkownika, id_wydarzenia, typUczestnictwa, wyzywienie);
                data.add(zapis);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            conn.closeConnect();
        }

        return data;
    }
}
