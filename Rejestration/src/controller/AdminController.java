package controller;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import java.net.URL;
import java.util.ResourceBundle;

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
    private ComboBox<Integer> obslugaIdUsun;

    @FXML
    private ComboBox<Integer> obslugaIdZmien;

    @FXML
    private PasswordField obslugaStareHaslo;

    @FXML
    private PasswordField obslugaNoweHaslo;

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
    private ComboBox<Integer> wydIdUsun;

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
        
    }
}
