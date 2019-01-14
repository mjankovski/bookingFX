package fx.booking.controller;

import fx.booking.dao.ReservationDAO;
import fx.booking.repository.Reservation;
import fx.booking.repository.ReservationRepository;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ClientDetailsController extends SuperController {

    @Autowired
    private ReservationRepository reservationRepository;

    @Autowired
    private ReservationDAO reservationDAO;

    @FXML
    private VBox mainVBox;

    @FXML
    private Label loginLabel;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, String> idColumn;

    @FXML
    private TableColumn<Reservation, String> invoiceColumn;

    @FXML
    private TableColumn<Reservation, String> roomNumberColumn;

    @FXML
    private TableColumn<Reservation, String> fromDateColumn;

    @FXML
    private TableColumn<Reservation, String> toDateColumn;

    @FXML
    private TableColumn<Reservation, String> currencyColumn;

    @FXML
    private TableColumn<Reservation, String> costColumn;

    @FXML
    private Button deleteButton;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        invoiceColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));

        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        fromDateColumn.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));

        toDateColumn.setCellValueFactory(new PropertyValueFactory<>("endingDate"));

        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        deleteButton.setDisable(true);

        progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);
    }

    @FXML
    void initReservationTable(String login) {
        reservationTable.getItems().clear();
        List<Reservation> list = reservationRepository.getReservationList(login);
        for (Reservation reservation : list) {
            reservationTable.getItems().add(reservation);
        }
        loginLabel.setText(login);
        makeFadeIn(mainVBox);
    }

    @FXML
    void deleteButtonPressed() {
        disableWhileProgressing(true);
        DeleteReservation deleteReservation = new DeleteReservation();
        startThreadWithEndingAction(deleteReservation);
    }

    class DeleteReservation extends Task<Void> {
        @Override
        protected Void call() {
            ObservableList<Reservation> allReservations;
            Reservation selectedReservation;
            allReservations = reservationTable.getItems();

            selectedReservation = reservationTable.getSelectionModel().getSelectedItem();

            reservationDAO.deleteReservation(selectedReservation.getId());
            allReservations.remove(selectedReservation);

            return null;
        }
    }

    @FXML
    void userSelected() {
        deleteButton.setDisable(false);
    }
}
