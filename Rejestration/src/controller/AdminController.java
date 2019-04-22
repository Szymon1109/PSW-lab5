package controller;

import dao.EventDAO;
import dao.EventDAOImpl;
import dao.UserDAO;
import dao.UserDAOImpl;
import email.Email;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Event;
import model.User;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class AdminController implements Initializable {

    @FXML
    private TableView uzytkTable;

    @FXML
    private TableColumn uzytkIdTable;

    @FXML
    private TableColumn uzytkImieTable;

    @FXML
    private TableColumn uzytkNazwiskoTable;

    @FXML
    private TableColumn uzytkLoginTable;

    @FXML
    private TableColumn uzytkHasloTable;

    @FXML
    private TableColumn uzytkEmailTable;

    @FXML
    private TableColumn uzytkDataTable;

    @FXML
    private Button wyloguj;

    @FXML
    private TextField obslugaImie;

    @FXML
    private TextField obslugaNazwisko;

    @FXML
    private TextField obslugaLogin;

    @FXML
    private TextField obslugaHaslo;

    @FXML
    private TextField obslugaEmail;

    @FXML
    private ComboBox<String> obslugaLoginUsun;

    @FXML
    private ComboBox<String> obslugaLoginZmien;

    @FXML
    private PasswordField obslugaNoweHaslo1;

    @FXML
    private PasswordField obslugaNoweHaslo2;

    @FXML
    private TableView wydTable;

    @FXML
    private TableColumn wydIdTable;

    @FXML
    private TableColumn wydNazwaTable;

    @FXML
    private TableColumn wydAgendaTable;

    @FXML
    private TableColumn wydTerminTable;

    @FXML
    private TextField wydNazwaDodaj;

    @FXML
    private DatePicker wydTerminDodaj;

    @FXML
    private TextArea wydAgendaDodaj;

    @FXML
    private ComboBox<String> wydNazwaUsun;

    @FXML
    private ComboBox<Integer> wydIdZmien;

    @FXML
    private TextField wydNazwaZmien;

    @FXML
    private DatePicker wydTerminZmien;

    @FXML
    private TextArea wydAgendaZmien;

    @FXML
    private TableView zatwTable;

    @FXML
    private TableColumn zatwIdTable;

    @FXML
    private TableColumn zatwIdUTable;

    @FXML
    private TableColumn zatwIdWTable;

    @FXML
    private TableView niezatwTable;

    @FXML
    private TableColumn niezatwIdTable;

    @FXML
    private TableColumn niezatwIdUTable;

    @FXML
    private TableColumn niezatwIdWTable;

    @FXML
    private ComboBox<Integer> idZapis;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setItemsInUzytkTable();
        setItemsInObslugaLogin();
        setItemsInWydTable();
        setItemsInWydNazwaUsun();
        setItemsInWydIdZmien();
    }

    public void setItemsInUzytkTable(){
        UserDAO userDAO = new UserDAOImpl();
        List<User> data = userDAO.findAllUsers();
        ObservableList<User> list = FXCollections.observableArrayList(data);

        uzytkIdTable.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("id"));

        uzytkImieTable.setCellValueFactory(
                new PropertyValueFactory<User, String>("imie"));

        uzytkNazwiskoTable.setCellValueFactory(
                new PropertyValueFactory<User, String>("nazwisko"));

        uzytkLoginTable.setCellValueFactory(
                new PropertyValueFactory<User, String>("login"));

        uzytkHasloTable.setCellValueFactory(
                new PropertyValueFactory<User, String>("haslo"));

        uzytkEmailTable.setCellValueFactory(
                new PropertyValueFactory<User, String>("email"));

        uzytkDataTable.setCellValueFactory(
                new PropertyValueFactory<User, String>("data_rejestracji"));

        uzytkTable.setItems(list);
    }

    @FXML
    public void uzytkOdswiez(ActionEvent actionEvent){
        setItemsInUzytkTable();
        setItemsInObslugaLogin();
    }

    @FXML
    public void wyloguj(ActionEvent actionEvent){
        Stage stage =(Stage)wyloguj.getScene().getWindow();
        stage.close();
    }

    public void uwaga(String string){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uwaga!");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }

    public void clearUzytkTextField(){
        obslugaImie.clear();
        obslugaNazwisko.clear();
        obslugaLogin.clear();
        obslugaHaslo.clear();
        obslugaEmail.clear();
    }

    public boolean checkString(String string){
        if(string.length() < 3){
            return false;
        }
        else {
            return true;
        }
    }

    public boolean checkLogin(String login){
        UserDAO userDAO = new UserDAOImpl();

        if(userDAO.findOne(login)){
            return false;
        }
        else{
            return true;
        }
    }

    public boolean checkEmail(String string){
        String pattern = "^[a-zA-Z0-9_.+-]+@gmail.com$";
        return string.matches(pattern);
    }

    public void addData(User user){
        UserDAO userDAO = new UserDAOImpl();
        userDAO.save(user);
    }

    public String getDate(){
        Date date = new Date(System.currentTimeMillis());
        return date.toString();
    }

    @FXML
    public void dodaj(javafx.event.ActionEvent actionEvent){
        String imieTxt = obslugaImie.getText();
        String nazwiskoTxt = obslugaNazwisko.getText();
        String loginTxt = obslugaLogin.getText();
        String hasloTxt = obslugaHaslo.getText();
        String emailTxt = obslugaEmail.getText();
        String uprawnienia = "user";
        String data = getDate();

        if (checkString(imieTxt) && checkString(nazwiskoTxt) && checkString(loginTxt) &&
                checkString(hasloTxt) && checkString(emailTxt)) {
            if (checkLogin(loginTxt)) {
                if (checkEmail(emailTxt)) {
                    User user = new User(imieTxt, nazwiskoTxt, loginTxt, hasloTxt, emailTxt, uprawnienia, data);
                    addData(user);

                    Email email = new Email(user);
                    email.sendEmail();

                    uwaga("Dodano nowego użytkownika! Na podany adres e-mail wygenerowana została wiadomość.");
                    clearUzytkTextField();
                    setItemsInUzytkTable();
                    setItemsInObslugaLogin();
                } else {
                    uwaga("Podano nieprawidłowy adres e-mail!\n(wymagany format: nazwa@gmail.com)");
                }
            } else {
                uwaga("Podano istniejący login - wybierz inny!");
            }
        } else {
            uwaga("Dane muszą składać się z conajmniej 3 znaków!");
        }
    }

    public void setItemsInObslugaLogin(){
        UserDAO userDAO = new UserDAOImpl();
        List<User> data = userDAO.findAllUsers();

        ObservableList<String> list = FXCollections.observableArrayList();
        for (User user : data) {
            String login = user.getLogin();
            list.add(login);
        }

        obslugaLoginUsun.setItems(list);
        obslugaLoginZmien.setItems(list);
    }

    @FXML
    public void usun(javafx.event.ActionEvent actionEvent){
        if(obslugaLoginUsun.getValue().equals(null)){
            uwaga("Nie wybrano użytkownika!");
        }
        else {
            UserDAO userDAO = new UserDAOImpl();
            String loginTxt = obslugaLoginUsun.getValue();
            userDAO.delete(loginTxt);
            uwaga("Wybrany użytkownik został usunięty!");

            setItemsInUzytkTable();
            setItemsInObslugaLogin();
        }
    }

    public boolean checkPassword(){
        if(obslugaNoweHaslo1.getText().equals(obslugaNoweHaslo2.getText())){
            return true;
        }
        else {
            return false;
        }
    }

    @FXML
    public void zmien(javafx.event.ActionEvent actionEvent){
        if(obslugaLoginZmien.getValue().equals(null)){
            uwaga("Nie wybrano użytkownika!");
        }
        else {
            if(obslugaNoweHaslo1.getText().equals(null)){
                uwaga("Nie podano nowego hasła!");
            }
            else {
                if(checkPassword()) {
                    String loginTxt = obslugaLoginZmien.getValue();
                    String hasloTxt = obslugaNoweHaslo1.getText();

                    UserDAO userDAO = new UserDAOImpl();
                    userDAO.changePassword(loginTxt, hasloTxt);
                    uwaga("Wybranemu użytkownikowi zmieniono hasło!");

                    setItemsInUzytkTable();
                    setItemsInObslugaLogin();
                }
                else{
                    uwaga("Podane hasła nie zgadzają się");
                }
            }
        }
    }

    @FXML
    public void setItemsInWydTable(){
        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();
        ObservableList<Event> list = FXCollections.observableArrayList(data);

        wydIdTable.setCellValueFactory(
                new PropertyValueFactory<User, Integer>("id"));

        wydNazwaTable.setCellValueFactory(
                new PropertyValueFactory<User, String>("nazwa"));

        wydAgendaTable.setCellValueFactory(
                new PropertyValueFactory<User, String>("agenda"));

        wydTerminTable.setCellValueFactory(
                new PropertyValueFactory<User, String>("termin"));

        wydTable.setItems(list);
    }

    @FXML
    public void wydOdswiez(ActionEvent actionEvent){
        setItemsInWydTable();
        setItemsInWydNazwaUsun();
        setItemsInWydIdZmien();
    }

    public void clearWydTextField(){
        wydNazwaDodaj.clear();
        wydTerminDodaj.setValue(null);
        wydAgendaDodaj.clear();
    }

    @FXML
    public void dodajWyd(javafx.event.ActionEvent actionEvent){
        String nazwaTxt = wydNazwaDodaj.getText();
        String agendaTxt = wydAgendaDodaj.getText();

        LocalDate termin = wydTerminDodaj.getValue();
        String terminTxt = termin.toString();

        if (checkString(nazwaTxt) && checkString(agendaTxt) && !terminTxt.equals(null)) {
            if (checkNazwa(nazwaTxt)) {
                Event event = new Event(nazwaTxt, agendaTxt, terminTxt);
                EventDAO eventDAO = new EventDAOImpl();
                eventDAO.save(event);

                uwaga("Dodano nowe wydarzenie!");
                clearWydTextField();
                setItemsInWydTable();
                setItemsInWydNazwaUsun();
                setItemsInWydIdZmien();
            } else {
                uwaga("Podano istniejące wydarzenie - wybierz inne!");
            }
        } else {
            uwaga("Dane muszą składać się z conajmniej 3 znaków!");
        }
    }

    public boolean checkNazwa(String nazwa){
        EventDAO eventDAO = new EventDAOImpl();

        if(eventDAO.findOne(nazwa)){
            return false;
        }
        else{
            return true;
        }
    }

    public void setItemsInWydNazwaUsun(){
        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();

        ObservableList<String> list = FXCollections.observableArrayList();
        for (Event event : data) {
            String nazwa = event.getNazwa();
            list.add(nazwa);
        }

        wydNazwaUsun.setItems(list);
    }

    public void setItemsInWydIdZmien(){
        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();

        ObservableList<Integer> list = FXCollections.observableArrayList();
        for (Event event : data) {
            Integer id = event.getId();
            list.add(id);
        }

        wydIdZmien.setItems(list);
    }

    @FXML
    public void usunWyd(javafx.event.ActionEvent actionEvent){
        if(wydNazwaUsun.getValue().equals(null)){
            uwaga("Nie wybrano wydarzenia!");
        }
        else {
            EventDAO eventDAO = new EventDAOImpl();
            String nazwaTxt = wydNazwaUsun.getValue();
            eventDAO.delete(nazwaTxt);
            uwaga("Wybrane wydarzenie zostało usunięte!");

            setItemsInWydTable();
            setItemsInWydNazwaUsun();
            setItemsInWydIdZmien();
        }
    }

    @FXML
    public void wydarzeniaChanged(ActionEvent actionEvent) {
        Integer wydarzenie = wydIdZmien.getValue();
        setNazwa(wydarzenie);
        setAgenda(wydarzenie);
        setTermin(wydarzenie);
    }

    public void setNazwa(Integer wydarzenie){
        String nazwaTxt = null;

        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();

        for (Event event : data) {
            if (wydarzenie.equals(event.getId())) {
                nazwaTxt = event.getNazwa();
                break;
            }
        }
        wydNazwaZmien.setText(nazwaTxt);
    }

    public void setAgenda(Integer wydarzenie){
        String agendaTxt = null;

        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();

        for (Event event : data) {
            if (wydarzenie.equals(event.getId())) {
                agendaTxt = event.getAgenda();
                break;
            }
        }
        wydAgendaZmien.setText(setNewLines(agendaTxt));
    }

    public String setNewLines(String string){
        String patternStr = "\\. [A-Z]";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher(string);
        while(matcher.find()){
            string = string.substring(0, matcher.start() + 1)
                    + "\n"
                    + string.substring(matcher.start() + 2);

            matcher = pattern.matcher(string);
        }
        return string;
    }

    public void setTermin(Integer wydarzenie){
        String terminTxt = null;

        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();

        for (Event event : data) {
            if (wydarzenie.equals(event.getId())) {
                terminTxt = event.getTermin();
                break;
            }
        }
        LocalDate localDate = LocalDate.parse(terminTxt);
        wydTerminZmien.setValue(localDate);
    }

    @FXML
    public void wydZmien(javafx.event.ActionEvent actionEvent){
        String nazwaTxt = wydNazwaZmien.getText();
        String agendaTxt = wydAgendaZmien.getText();

        LocalDate termin = wydTerminZmien.getValue();
        String terminTxt = termin.toString();

        if (checkString(nazwaTxt) && checkString(agendaTxt) && !terminTxt.equals(null)) {
            if (checkNazwa(nazwaTxt)) {
                Event event = new Event(nazwaTxt, agendaTxt, terminTxt);
                EventDAO eventDAO = new EventDAOImpl();
                eventDAO.save(event);

                uwaga("Zmieniono wybrane wydarzenie!");
                clearWydTextField();
                setItemsInWydTable();
                setItemsInWydNazwaUsun();
                setItemsInWydIdZmien();
            } else {
                uwaga("Podano istniejące wydarzenie - wybierz inne!");
            }
        } else {
            uwaga("Dane muszą składać się z conajmniej 3 znaków!");
        }
    }
}