package fx.booking.controller;

import fx.booking.dao.ReservationDAO;
import fx.booking.repository.Reservation;
import fx.booking.repository.ReservationKeeper;

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
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class ClientDetailsController extends SuperController{

    @Autowired
    private ConfigurableApplicationContext springContext;

    @Autowired
    private ReservationKeeper reservationKeeper;

    @Autowired
    private ReservationDAO reservationDAO;

    @FXML
    private VBox mainVBox;

    @FXML
    private HBox titleHBox;

    @FXML
    private Label loginLabel;

    @FXML
    private Label hotelLabel;

    @FXML
    private HBox panelLabelHBox;

    @FXML
    private HBox tabelHBox;

    @FXML
    private HBox deleteButtonHBox;


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
    private Button logOutButton;

    @FXML
    private Button backButton;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private LogOut logOut;

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

    @FXML void initReservationTable(String login) {
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
        disableWhileProgressing();
        back = new Back();
        progressIndicator.visibleProperty().bind(back.runningProperty());
        back.setOnSucceeded(e -> {
            enableWhileProgressing();
            Parent parent = back.getValue();
            enableWhileProgressing();
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
    void deleteButtonPressed(ActionEvent event) {
        disableWhileProgressing();
        deleteReservation = new DeleteReservation();
        progressIndicator.visibleProperty().bind(deleteReservation.runningProperty());
        deleteReservation.setOnSucceeded(e -> {
            enableWhileProgressing();
        });

        deleteReservation.setOnFailed(e -> {
            deleteReservation.getException().printStackTrace();
        });

        Thread thread = new Thread(deleteReservation);
        thread.start();
    }

    @FXML
    void logOutButtonClicked(ActionEvent event) {
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

    class DeleteReservation extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            ObservableList<Reservation> allReservations;
            Reservation selectedReservation;
            allReservations = reservationTable.getItems();

            selectedReservation = reservationTable.getSelectionModel().getSelectedItem();

            reservationDAO.deleteReservation(selectedReservation.getId());
            allReservations.remove(selectedReservation);

            return null;
        }
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

    class Back extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            return loadScene("/AdminPanel.fxml");
        }
    }

    private void disableWhileProgressing() {
        titleHBox.setDisable(true);
        panelLabelHBox.setDisable(true);
        tabelHBox.setDisable(true);
        deleteButtonHBox.setDisable(true);
        logOutButton.setDisable(true);
        backButton.setDisable(true);
    }

    private void enableWhileProgressing() {
        titleHBox.setDisable(false);
        panelLabelHBox.setDisable(false);
        tabelHBox.setDisable(false);
        deleteButtonHBox.setDisable(false);
        logOutButton.setDisable(false);
        backButton.setDisable(false);
    }

    @FXML
    void userSelected(MouseEvent event) {
        deleteButton.setDisable(false);
    }
}
