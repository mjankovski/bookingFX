package fx.booking.controller;

import fx.booking.api.NbpApi;
import fx.booking.dao.ReservationDAO;
import fx.booking.repository.Reservation;

import fx.booking.repository.ReservationKeeper;
import fx.booking.repository.Room;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class BookingController {

    @Autowired
    private ConfigurableApplicationContext springContext;

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private ReservationKeeper reservationKeeper;

    @Autowired
    private NbpApi nbpApi;

    @FXML
    private VBox mainVBox;

    @FXML
    private ScrollPane bookingScrollPane;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Label currencyLabel;

    @FXML
    private Pane outputPane;

    @FXML
    private Label titleLabel;

    @FXML
    private Pane infoPane;

    @FXML
    private Label peopleLabel;

    @FXML
    private Label costLabel;

    @FXML
    private Label bookDateLabel;

    @FXML
    private Label floorLabel;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Label roomNumberLabel;

    @FXML
    private Label fromLabel;

    @FXML
    private Label toLabel;

    @FXML
    private Button bookButton;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private Button menuButton;

    @FXML
    private Button planButton;

    @FXML
    private Button addButton;

    @FXML
    private Button deleteButton;

    @FXML
    private Room selectedRoom;

    @FXML
    private Label dateTextLabel;

    @FXML
    private Label dateValueLabel;

    @FXML
    private Label currencyTextLabel;

    @FXML
    private Label currencyValueLabel;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Integer> reservationNumberColumn;

    @FXML
    private TableColumn<Reservation, LocalDate> fromDateColumn;

    @FXML
    private TableColumn<Reservation, LocalDate> toDateColumn;

    @FXML
    private ObservableList<Reservation> reservationsList;

    @FXML
    private RadioButton plnRadioButton;

    @FXML
    private RadioButton eurRadioButton;

    private ToggleGroup toggleGroup;

    private BigDecimal currencyConverter;

    private String actualCurrency;

    @FXML
    public void initialize() {
        reservationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fromDateColumn.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        toDateColumn.setCellValueFactory(new PropertyValueFactory<>("endingDate"));

        toggleGroup = new ToggleGroup();

        plnRadioButton.setToggleGroup(toggleGroup);
        eurRadioButton.setToggleGroup(toggleGroup);

        plnRadioButton.setSelected(true);
        actualCurrency = "PLN";

        currencyConverter = nbpApi.getPlnToEuroCurrency();

        dateValueLabel.setText(java.time.LocalDate.now().toString());
        currencyValueLabel.setText(currencyConverter.setScale(2, BigDecimal.ROUND_UP).toString() + " EUR");
    }

    @FXML
    public void initRoom(Room room) {
        selectedRoom = room;
        roomNumberLabel.setText(Integer.toString(room.getNumber()));
        peopleLabel.setText(Integer.toString(room.getPeopleSize()));
        costLabel.setText(room.getDailyCost().toString());
        currencyLabel.setText("PLN");
    }

    @FXML void initReservationTable(ObservableList<Reservation> list) {
        reservationTable.getItems().clear();
        for (Reservation reservation : list) {
            reservationTable.getItems().add(reservation);
        }
        makeFadeIn();
    }

    @FXML
    public void menuButtonClicked(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/Welcome.fxml"));
            Parent tableViewParent = fxmlLoader.load();
            Scene tableViewScene = new Scene(tableViewParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void planButtonClicked(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/Plan.fxml"));
            Parent tableViewParent = fxmlLoader.load();
            Scene tableViewScene = new Scene(tableViewParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void reservationButtonClicked(ActionEvent event) throws IOException {

        if(reservationDAO.checkIfRoomFree(Integer.valueOf(roomNumberLabel.getText()), fromDatePicker.getValue().toString(), toDatePicker.getValue().toString())){
            try {
                reservationDAO.insertReservation(Integer.valueOf(roomNumberLabel.getText()), fromDatePicker.getValue().toString(), toDatePicker.getValue().toString(), new BigDecimal(costLabel.getText()),actualCurrency);
                reservationsList = reservationKeeper.getReservationList(selectedRoom.getNumber());
                reservationTable.getItems().add(reservationsList.get(reservationsList.size() - 1));
            }catch(IllegalArgumentException e){
                showAlertInfo("Błąd!", "Nie dokonano rezerwacji, ponieważ podano błędną datę!", Alert.AlertType.ERROR);
            }
        }
        else{
            showAlertInfo("Błąd!", "Nie dokonano rezerwacji, ponieważ w tych dniach pokój jest już zarezerwowany!", Alert.AlertType.ERROR);
        }
    }

    @FXML
    void eurRadioButtonSelected(ActionEvent event) {
        actualCurrency = "EUR";
        BigDecimal newValue =  selectedRoom.getDailyCost().divide(currencyConverter, 0);
        costLabel.setText(newValue.toString());
        currencyLabel.setText("EUR");
    }

    @FXML
    void plnRadioButtonSelected(ActionEvent event) {
        actualCurrency = "PLN";
        BigDecimal newValue =  selectedRoom.getDailyCost();
        costLabel.setText(newValue.toString());
        currencyLabel.setText("PLN");
    }

    private void showAlertInfo(String title, String header, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    private void makeFadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration((Duration.seconds(1)));
        fadeTransition.setNode(mainVBox);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}

