package fx.booking.controller;

import fx.booking.api.NbpApi;
import fx.booking.dao.ReservationDAO;
import fx.booking.repository.Reservation;

import fx.booking.repository.ReservationKeeper;
import fx.booking.repository.Room;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
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
    private HBox titleHBox;

    @FXML
    private HBox roomInfoHBox;

    @FXML
    private HBox descriptionHBox;

    @FXML
    private HBox reservationTitleHBox;

    @FXML
    private HBox tableHBox;

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

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private LogOut logOut;

    @FXML
    private Plan plan;

    @FXML
    private MakeReservation makeReservation;

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

        progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);
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
        disableWhileProgressing();
        logOut = new LogOut();
        progressIndicator.visibleProperty().bind(logOut.runningProperty());
        logOut.setOnSucceeded(e -> {
            enableWhileProgressing();
            Parent parent = logOut.getValue();
            enableWhileProgressing();
            Scene scene = new Scene(parent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        });

        logOut.setOnFailed(e -> {
            logOut.getException().printStackTrace();
        });

        Thread thread = new Thread(logOut);
        thread.start();
    }

    @FXML
    public void planButtonClicked(ActionEvent event) throws IOException {
        disableWhileProgressing();
        plan = new Plan();
        progressIndicator.visibleProperty().bind(plan.runningProperty());
        plan.setOnSucceeded(e -> {
            enableWhileProgressing();
            Parent parent = plan.getValue();
            enableWhileProgressing();
            Scene scene = new Scene(parent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        });

        plan.setOnFailed(e -> {
            plan.getException().printStackTrace();
        });

        Thread thread = new Thread(plan);
        thread.start();
    }

    @FXML
    public void reservationButtonClicked(ActionEvent event) throws IOException {
        disableWhileProgressing();
        makeReservation = new MakeReservation();
        progressIndicator.visibleProperty().bind(makeReservation.runningProperty());
        makeReservation.setOnSucceeded(e -> {
            enableWhileProgressing();
            if(makeReservation.getValue() == 2) {
                showAlertInfo("Błąd!", "Nie dokonano rezerwacji, ponieważ podano błędną datę!", Alert.AlertType.ERROR);
            }
            else if(makeReservation.getValue() == 3) {
                showAlertInfo("Błąd!", "Nie dokonano rezerwacji, ponieważ w tych dniach pokój jest już zarezerwowany!", Alert.AlertType.ERROR);
            }
        });

        makeReservation.setOnFailed(e -> {
            makeReservation.getException().printStackTrace();
        });

        Thread thread = new Thread(makeReservation);
        thread.start();
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

    private void disableWhileProgressing() {
        titleHBox.setDisable(true);
        roomInfoHBox.setDisable(true);
        descriptionHBox.setDisable(true);;
        reservationTitleHBox.setDisable(true);
        tableHBox.setDisable(true);
        menuButton.setDisable(true);
        planButton.setDisable(true);
    }

    private void enableWhileProgressing() {
        titleHBox.setDisable(false);
        roomInfoHBox.setDisable(false);
        descriptionHBox.setDisable(false);;
        reservationTitleHBox.setDisable(false);
        tableHBox.setDisable(false);
        menuButton.setDisable(false);
        planButton.setDisable(false);
    }

    class LogOut extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/Welcome.fxml"));
            Parent tableViewParent = fxmlLoader.load();
            return tableViewParent;
        }
    }

    class Plan extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/Plan.fxml"));
            Parent tableViewParent = fxmlLoader.load();
            return tableViewParent;
        }
    }

    class MakeReservation extends Task<Integer> {
        @Override
        protected Integer call() throws Exception {
            if(reservationDAO.checkIfRoomFree(Integer.valueOf(roomNumberLabel.getText()), fromDatePicker.getValue().toString(), toDatePicker.getValue().toString())){
                try {
                    reservationDAO.insertReservation(Integer.valueOf(roomNumberLabel.getText()), fromDatePicker.getValue().toString(), toDatePicker.getValue().toString(), new BigDecimal(costLabel.getText()),actualCurrency);
                    reservationsList = reservationKeeper.getReservationList(selectedRoom.getNumber());
                    reservationTable.getItems().add(reservationsList.get(reservationsList.size() - 1));
                    return 1;
                }catch(IllegalArgumentException e){
                    return 2;
                }
            }
            else{
                return 3;
            }
        }
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

