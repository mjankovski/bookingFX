package fx.booking.controller;

import fx.booking.repository.Reservation;
import fx.booking.repository.ReservationKeeper;
import fx.booking.repository.User;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
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
public class ClientDetailsController {

    @Autowired
    private ConfigurableApplicationContext springContext;

    @Autowired
    private ReservationKeeper reservationKeeper;

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
    public void initialize() {
        idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));

        invoiceColumn.setCellValueFactory(new PropertyValueFactory<>("invoiceNumber"));

        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));

        fromDateColumn.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));

        toDateColumn.setCellValueFactory(new PropertyValueFactory<>("endingDate"));

        currencyColumn.setCellValueFactory(new PropertyValueFactory<>("currency"));

        costColumn.setCellValueFactory(new PropertyValueFactory<>("cost"));

        deleteButton.setDisable(true);
    }

    @FXML void initReservationTable(String login) {
        reservationTable.getItems().clear();
        List<Reservation> list = reservationKeeper.getReservationList(login);
        for (Reservation reservation : list) {
            reservationTable.getItems().add(reservation);
        }
        loginLabel.setText(login);
        makeFadeIn();
    }

    @FXML
    void backButtonClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/AdminPanel.fxml"));
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
    void deleteButtonPressed(ActionEvent event) {
        ObservableList<Reservation> allReservations;
        Reservation selectedReservation;
        allReservations = reservationTable.getItems();

        selectedReservation = reservationTable.getSelectionModel().getSelectedItem();

        //TODO tu sprawdzasz czy mozna usunac rezerwacje
        allReservations.remove(selectedReservation);
    }

    @FXML
    void logOutButtonClicked(ActionEvent event) {
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
    void userSelected(MouseEvent event) {
        deleteButton.setDisable(false);
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
