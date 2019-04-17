package dao;

import database.SQLConnection;
import model.User;
import java.sql.*;

public class UserDAOImpl implements UserDAO{

    @Override
    public Boolean findUser(String query) {
        SQLConnection conn = new SQLConnection();
        ResultSet rs = conn.makeQuery(query);

        try{
            if(rs.next()){
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally{
            conn.closeConnect(rs);
        }
        return false;
    }

    @Override
    public Boolean findOne(String login, String haslo){
        String query = "SELECT * FROM uzytkownicy WHERE login = '" + login + "' AND " +
                       "haslo = '" + haslo + "';";
        return findUser(query);
    }

    @Override
    public Boolean findOne(String login){
        String query = "SELECT * FROM uzytkownicy WHERE login = '" + login + "';";
        return findUser(query);
    }

    @Override
    public String whoIs(String login){
        String query = "SELECT * FROM uzytkownicy WHERE login = '" + login + "';";
        String uprawnieniaDb = null;

        SQLConnection conn = new SQLConnection();
        ResultSet rs = conn.makeQuery(query);

        try{
            if(rs.next()){
                uprawnieniaDb = rs.getString("uprawnienia");
            }
        } catch (SQLException e){
            e.printStackTrace();
        } finally{
            conn.closeConnect(rs);
        }

        return uprawnieniaDb;
    }

    @Override
    public void save(User user){
        String query = "INSERT INTO uzytkownicy(imie, nazwisko, login, haslo, " +
                "email, uprawnienia, data_rejestracji) VALUES ('" +
                user.getImie() + "', '" + user.getNazwisko() + "', '" +
                user.getLogin() + "', '" + user.getHaslo() + "', '" +
                user.getEmail() + "', '" + user.getUprawnienia() + "', '" +
                user.getData_rejestracji() + "');";

        SQLConnection conn = new SQLConnection();
        conn.makeQueryToDatabase(query);
        conn.closeConnect();
    }
}