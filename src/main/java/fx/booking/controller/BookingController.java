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
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;

@Controller
public class BookingController extends SuperController {

    @Autowired
    private ReservationDAO reservationDAO;

    @Autowired
    private ReservationKeeper reservationKeeper;

    @Autowired
    private NbpApi nbpApi;

    @FXML
    private VBox mainVBox;

    @FXML
    private Label currencyLabel;

    @FXML
    private Label peopleLabel;

    @FXML
    private Label costLabel;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Label roomNumberLabel;

    @FXML
    private Room selectedRoom;

    @FXML
    private Label dateValueLabel;

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
    private RadioButton plnRadioButton;

    @FXML
    private RadioButton eurRadioButton;

    @FXML
    private ProgressIndicator progressIndicator;

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

    @FXML
    void initReservationTable(ObservableList<Reservation> list) {
        reservationTable.getItems().clear();
        for (Reservation reservation : list) {
            reservationTable.getItems().add(reservation);
        }
    }

    @FXML
    public void reservationButtonClicked() {
        disableWhileProgressing(true);
        makeReservation = new MakeReservation();
        progressIndicator.visibleProperty().bind(makeReservation.runningProperty());
        makeReservation.setOnSucceeded(e -> {
            disableWhileProgressing(false);
            if (makeReservation.getValue() == 2) {
                showAlertInfo("Nie dokonano rezerwacji, ponieważ podano błędną datę!");
            } else if (makeReservation.getValue() == 3) {
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
        BigDecimal newValue = selectedRoom.getDailyCost().divide(currencyConverter, 0);
        costLabel.setText(newValue.toString());
        currencyLabel.setText("EUR");
    }

    @FXML
    void plnRadioButtonSelected() {
        actualCurrency = "PLN";
        BigDecimal newValue = selectedRoom.getDailyCost();
        costLabel.setText(newValue.toString());
        currencyLabel.setText("PLN");
    }

    class MakeReservation extends Task<Integer> {
        @Override
        protected Integer call() {
            if (reservationDAO.checkIfRoomFree(Integer.valueOf(roomNumberLabel.getText()), fromDatePicker.getValue().toString(), toDatePicker.getValue().toString())) {
                try {
                    ObservableList<Reservation> reservationsList;
                    reservationDAO.insertReservation(Integer.valueOf(roomNumberLabel.getText()), fromDatePicker.getValue().toString(), toDatePicker.getValue().toString(), new BigDecimal(costLabel.getText()), actualCurrency);
                    reservationsList = reservationKeeper.getReservationList(selectedRoom.getNumber());
                    reservationTable.getItems().add(reservationsList.get(reservationsList.size() - 1));
                    return 1;
                } catch (IllegalArgumentException e) {
                    return 2;
                }
            } else {
                initReservationTable(reservationKeeper.getReservationList(selectedRoom.getNumber()));
                return 3;
            }
        }
    }
}

