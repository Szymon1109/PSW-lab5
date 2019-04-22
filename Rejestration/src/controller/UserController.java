package controller;

import dao.EventDAO;
import dao.EventDAOImpl;
import dao.ZapisDAO;
import dao.ZapisDAOImpl;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import model.Event;
import model.Zapis;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UserController implements Initializable {

    //TODO:
    private static final Integer id_uzytkownika_CONST = 2;

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

    public void uwaga(String string){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Uwaga!");
        alert.setHeaderText(null);
        alert.setContentText(string);
        alert.showAndWait();
    }

    public void clearTextField(){
        agenda.setText(null);
        termin.setText(null);
        typUczestnictwa.setValue(null);
        wyzywienie.setValue(null);
    }

    public void setItemsInWydarzenia(){
        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();

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

    public void setItemsInZatwWyd(){
        EventDAO event = new EventDAOImpl();
        List<Event> data = event.findConfirmedEventsForUser(id_uzytkownika_CONST);
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
        EventDAO event = new EventDAOImpl();
        List<Event> data = event.findNotConfirmedEventsForUser(id_uzytkownika_CONST);
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

        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();

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

        EventDAO eventDAO = new EventDAOImpl();
        List<Event> data = eventDAO.findAllEvents();

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
        String typUczestnictwaTxt = typUczestnictwa.getValue();
        String wyzywienieTxt = wyzywienie.getValue();
        String nazwaTxt = wydarzenia.getValue();
        Integer idWyd = getId(nazwaTxt);

        Zapis zapis = new Zapis(id_uzytkownika_CONST, idWyd, typUczestnictwaTxt, wyzywienieTxt);

        ZapisDAO zapisDAO = new ZapisDAOImpl();
        zapisDAO.save(zapis);

        uwaga("Zapisano na podane wydarzenie!");
        clearTextField();
        setItemsInWydarzenia();
        setItemsInZatwWyd();
        setItemsInNiezatwWyd();
    }

    public Integer getId(String nazwa){
        Integer id = null;

        List<Event> data = FXCollections.observableArrayList();
        EventDAO eventDAO = new EventDAOImpl();
        data = eventDAO.findAllEvents();

        for(Event event : data){
            if(event.getNazwa().equals(nazwa)){
                id = event.getId();
                break;
            }
        }
        return id;
    }

    @FXML
    public void wyloguj(ActionEvent actionEvent){
        Stage stage =(Stage)wyloguj.getScene().getWindow();
        stage.close();
    }

    @FXML
    public void odswiez(ActionEvent actionEvent){
        setItemsInZatwWyd();
        setItemsInNiezatwWyd();
    }
}