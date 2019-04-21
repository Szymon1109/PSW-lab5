package controller;

import dao.EventDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Event;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController implements Initializable {

    @FXML
    private ComboBox<String> wydarzenia;

    @FXML
    private Label agenda;

    @FXML
    private Label termin;

    @FXML
    private ChoiceBox<String> typUczestnictwa;

    @FXML
    private ChoiceBox<String> wyzywienie;

    @FXML
    private Button wyloguj;

    @FXML
    private TableView tableZatw;

    @FXML
    private TableColumn idZatw;

    @FXML
    private TableColumn nazwaZatw;

    @FXML
    private TableColumn agendaZatw;

    @FXML
    private TableColumn terminZatw;

    @FXML
    private TableView tableNiezatw;

    @FXML
    private TableColumn idNiezatw;

    @FXML
    private TableColumn nazwaNiezatw;

    @FXML
    private TableColumn agendaNiezatw;

    @FXML
    private TableColumn terminNiezatw;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setItemsInWydarzenia();
        setItemsInTypUczestnictwa();
        setItemsInWyzywienie();
        setItemsInZatwWyd();
        setItemsInNiezatwWyd();
    }

    public void setItemsInWydarzenia(){
        EventDAOImpl eventDAOImpl = new EventDAOImpl();
        List<Event> data = eventDAOImpl.findAllEvents();

        ObservableList<String> list = FXCollections.observableArrayList();
        for (Event event : data) {
            String nazwa = event.getNazwa();
            list.add(nazwa);
        }

        wydarzenia.setItems(list);
    }

    public void setItemsInTypUczestnictwa(){

        ObservableList<String> list = FXCollections.observableArrayList("Słuchacz", "Autor", "Sponsor", "Organizator");
        typUczestnictwa.setItems(list);
    }

    public void setItemsInWyzywienie(){

        ObservableList<String> list = FXCollections.observableArrayList("Bez preferencji", "Wegetariańskie", "Bez glutenu");
        wyzywienie.setItems(list);
    }

    //TODO:
    //dopisać metodę zwracającą indeksy wydarzeń zatwierdzonych i nie
    //dopisać metodę zwracającą odpowiednie wydarzenia

    public void setItemsInZatwWyd(){
        EventDAOImpl event = new EventDAOImpl();
        List<Event> data = event.findAllEvents();
        ObservableList<Event> list = FXCollections.observableArrayList(data);

        idZatw.setCellValueFactory(
                new PropertyValueFactory<Event,Integer>("id"));

        nazwaZatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("nazwa"));

        agendaZatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("agenda"));

        terminZatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("termin"));

        tableZatw.setItems(list);
    }

    public void setItemsInNiezatwWyd(){
        EventDAOImpl event = new EventDAOImpl();
        List<Event> data = event.findAllEvents();
        ObservableList<Event> list = FXCollections.observableArrayList(data);

        idNiezatw.setCellValueFactory(
                new PropertyValueFactory<Event,Integer>("id"));

        nazwaNiezatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("nazwa"));

        agendaNiezatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("agenda"));

        terminNiezatw.setCellValueFactory(
                new PropertyValueFactory<Event,String>("termin"));

        tableNiezatw.setItems(list);
    }

    @FXML
    public void wydarzeniaChanged(ActionEvent actionEvent) {
        String wydarzenie = wydarzenia.getValue();
        setAgenda(wydarzenie);
        setTermin(wydarzenie);
    }

    public void setAgenda(String wydarzenie){
        String agendaTxt = null;

        EventDAOImpl eventDAOImpl = new EventDAOImpl();
        List<Event> data = eventDAOImpl.findAllEvents();

        for (Event event : data) {
            if (wydarzenie.equals(event.getNazwa())) {
                agendaTxt = event.getAgenda();
                break;
            }
        }
        agenda.setText(setNewLines(agendaTxt));
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

    public void setTermin(String wydarzenie){
        String terminTxt = null;

        EventDAOImpl eventDAOImpl = new EventDAOImpl();
        List<Event> data = eventDAOImpl.findAllEvents();

        for (Event event : data) {
            if (wydarzenie.equals(event.getNazwa())) {
                terminTxt = event.getTermin();
                break;
            }
        }
        termin.setText(terminTxt);
    }

    @FXML
    public void zapisz(ActionEvent actionEvent){
        //TODO:
        //pobranie ID użytkownika, ID wydarzenia i dodanie zapisu
    }

    @FXML
    public void wyloguj(ActionEvent actionEvent){
        Stage stage =(Stage)wyloguj.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void odswiezZatw(ActionEvent actionEvent){
        setItemsInZatwWyd();
    }

    @FXML
    public void odswiezNiezatw(ActionEvent actionEvent){
        setItemsInNiezatwWyd();
    }
}