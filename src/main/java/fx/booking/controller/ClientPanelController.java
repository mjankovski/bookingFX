package fx.booking.controller;

import fx.booking.DocumentGenerator;
import fx.booking.dao.AccountDAO;
import fx.booking.repository.Reservation;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;

@Controller
public class ClientPanelController {
    @Autowired
    private ConfigurableApplicationContext springContext;

    @Autowired
    private AccountDAO accountDAO;

    @FXML
    private TableView<Reservation> reservationTable;

    @FXML
    private TableColumn<Reservation, Integer> roomNumberColumn;

    @FXML
    private TableColumn<Reservation, LocalDate> fromDateColumn;

    @FXML
    private TableColumn<Reservation, LocalDate> toDateColumn;

    @FXML
    private ObservableList<Reservation> reservationsList;

    @FXML
    private Button invoiceButton;

    @FXML
    private VBox mainVBox;

    private DocumentGenerator documentGenerator;

    @FXML
    public void initialize() {
        documentGenerator = new DocumentGenerator();
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        fromDateColumn.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        toDateColumn.setCellValueFactory(new PropertyValueFactory<>("endingDate"));
    }

    @FXML void initReservationTable(ObservableList<Reservation> list) {
        reservationTable.getItems().clear();
        for (Reservation reservation : list) {
            reservationTable.getItems().add(reservation);
        }
        makeFadeIn();
    }

    @FXML
    public void reservationSelected(MouseEvent event) throws IOException {
        invoiceButton.setDisable(false);
    }

    @FXML
    public void invoiceButtonClicked(ActionEvent event) throws IOException {
        System.out.println(reservationTable.getSelectionModel().getSelectedItem().getId());
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

    private void makeFadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration((Duration.seconds(1)));
        fadeTransition.setNode(mainVBox);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }
}
