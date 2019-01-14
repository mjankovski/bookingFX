package fx.booking.controller;

import fx.booking.api.NbpApi;
import fx.booking.dao.ReservationDAO;
import fx.booking.repository.Reservation;

import fx.booking.repository.ReservationKeeper;
import fx.booking.repository.Room;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;

import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class BookingController extends SuperController{

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

    private BigDecimal currencyConverter;

    private String actualCurrency;

    @FXML
    public void initialize() {
        reservationNumberColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        fromDateColumn.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        toDateColumn.setCellValueFactory(new PropertyValueFactory<>("endingDate"));

        ToggleGroup toggleGroup = new ToggleGroup();

        plnRadioButton.setToggleGroup(toggleGroup);
        eurRadioButton.setToggleGroup(toggleGroup);

        plnRadioButton.setSelected(true);
        actualCurrency = "PLN";

        currencyConverter = nbpApi.getPlnToEuroCurrency();

        dateValueLabel.setText(java.time.LocalDate.now().toString());
        currencyValueLabel.setText(currencyConverter.setScale(2, BigDecimal.ROUND_UP).toString() + " EUR");

        progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);

        makeFadeIn(mainVBox);
    }

    @FXML
    void initRoom(Room room) {
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

            changeScene(event, parent);
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

            changeScene(event, parent);
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
                showAlertInfo("Nie dokonano rezerwacji, ponieważ podano błędną datę!");
            }
            else if(makeReservation.getValue() == 3) {
                showAlertInfo("Nie dokonano rezerwacji, ponieważ w tych dniach pokój jest już zarezerwowany!");
            }
        });

        makeReservation.setOnFailed(e -> {
            makeReservation.getException().printStackTrace();
        });

        Thread thread = new Thread(makeReservation);
        thread.start();
    }

    @FXML
    void eurRadioButtonSelected() {
        actualCurrency = "EUR";
        BigDecimal newValue =  selectedRoom.getDailyCost().divide(currencyConverter, 0);
        costLabel.setText(newValue.toString());
        currencyLabel.setText("EUR");
    }

    @FXML
    void plnRadioButtonSelected() {
        actualCurrency = "PLN";
        BigDecimal newValue =  selectedRoom.getDailyCost();
        costLabel.setText(newValue.toString());
        currencyLabel.setText("PLN");
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
            return loadScene("/Welcome.fxml");
        }
    }

    class Plan extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            return loadScene("/Plan.fxml");
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
                initReservationTable(reservationKeeper.getReservationList(selectedRoom.getNumber()));
                return 3;
            }
        }
    }
}

