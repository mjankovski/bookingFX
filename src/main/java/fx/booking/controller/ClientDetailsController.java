package fx.booking.controller;

import fx.booking.dao.ReservationDAO;
import fx.booking.repository.Reservation;
import fx.booking.repository.ReservationKeeper;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ClientDetailsController extends SuperController {


    @Autowired
    private ReservationKeeper reservationKeeper;

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
    private Back back;

    @FXML
    private DeleteReservation deleteReservation;

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
        List<Reservation> list = reservationKeeper.getReservationList(login);
        for (Reservation reservation : list) {
            reservationTable.getItems().add(reservation);
        }
        loginLabel.setText(login);
        makeFadeIn(mainVBox);
    }

    @FXML
    void backButtonClicked(ActionEvent event) {
        disableWhileProgressing(true);
        back = new Back();
        progressIndicator.visibleProperty().bind(back.runningProperty());
        back.setOnSucceeded(e -> {
            disableWhileProgressing(false);
            Parent parent = back.getValue();
            Scene scene = new Scene(parent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        });

        back.setOnFailed(e -> {
            back.getException().printStackTrace();
        });

        Thread thread = new Thread(back);
        thread.start();
    }

    @FXML
    void deleteButtonPressed() {
        disableWhileProgressing(true);
        deleteReservation = new DeleteReservation();
        progressIndicator.visibleProperty().bind(deleteReservation.runningProperty());
        deleteReservation.setOnSucceeded(e -> {
            disableWhileProgressing(false);
        });

        deleteReservation.setOnFailed(e -> {
            deleteReservation.getException().printStackTrace();
        });

        Thread thread = new Thread(deleteReservation);
        thread.start();
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

    class Back extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            return loadScene("/AdminPanel.fxml");
        }
    }

    @FXML
    void userSelected() {
        deleteButton.setDisable(false);
    }
}
