package fx.booking.controller;

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
import javafx.scene.control.*;
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

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private TextField nameTextfield;

    @FXML
    private TextField surnameTextField;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passField;

    @FXML
    private Label peselLabel;

    @FXML
    private Label emailLabel;

    @FXML
    private TextField peselTextField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label creditCardLabel;

    @FXML
    private TextField partFourCardNumberTextField1;

    @FXML
    private TextField partFourCardNumberTextField2;

    @FXML
    private TextField partFourCardNumberTextField3;

    @FXML
    private TextField partFourCardNumberTextField4;

    @FXML
    private Label phoneNumberLabel;

    @FXML
    private TextField directionNumbertextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    public void initialize() {
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        fromDateColumn.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        toDateColumn.setCellValueFactory(new PropertyValueFactory<>("endingDate"));

        nameTextfield.setText(accountDAO.getFirstname());
        nameTextfield.setDisable(true);

        surnameTextField.setText(accountDAO.getLastname());
        surnameTextField.setDisable(true);

        loginTextField.setText(accountDAO.getLogin());
        loginTextField.setDisable(true);

        passField.setText(accountDAO.getLogin());
        passField.setDisable(true);

        peselTextField.setText(accountDAO.getPesel());
        peselTextField.setDisable(true);

        emailTextField.setText(accountDAO.getEmail());
        emailTextField.setDisable(true);

        partFourCardNumberTextField1.setText(accountDAO.getCreditCardNumber().substring(0, 4));
        partFourCardNumberTextField1.setDisable(true);

        partFourCardNumberTextField2.setText(accountDAO.getCreditCardNumber().substring(4, 8));
        partFourCardNumberTextField2.setDisable(true);

        partFourCardNumberTextField3.setText(accountDAO.getCreditCardNumber().substring(8, 12));
        partFourCardNumberTextField3.setDisable(true);

        partFourCardNumberTextField4.setText(accountDAO.getCreditCardNumber().substring(12, 16));
        partFourCardNumberTextField4.setDisable(true);

        directionNumbertextField.setDisable(true);

        phoneNumberTextField.setText(accountDAO.getPhoneNumber());
        phoneNumberTextField.setDisable(true);
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

    @FXML
    public void editButtonClicked(ActionEvent event) {
        Button button = (Button)event.getSource();
        if(button.getText().equals("EDYTUJ")) {
            nameTextfield.setDisable(false);
            surnameTextField.setDisable(false);
            loginTextField.setDisable(false);
            passField.setDisable(false);
            peselTextField.setDisable(false);
            emailTextField.setDisable(false);
            partFourCardNumberTextField1.setDisable(false);
            partFourCardNumberTextField2.setDisable(false);
            partFourCardNumberTextField3.setDisable(false);
            partFourCardNumberTextField4.setDisable(false);
            directionNumbertextField.setDisable(false);
            phoneNumberTextField.setDisable(false);
            button.setText("ZAPISZ");
        }
        else if(button.getText().equals("ZAPISZ")) {
            nameTextfield.setDisable(true);
            surnameTextField.setDisable(true);
            loginTextField.setDisable(true);
            passField.setDisable(true);
            peselTextField.setDisable(true);
            emailTextField.setDisable(true);
            partFourCardNumberTextField1.setDisable(true);
            partFourCardNumberTextField2.setDisable(true);
            partFourCardNumberTextField3.setDisable(true);
            partFourCardNumberTextField4.setDisable(true);
            directionNumbertextField.setDisable(true);
            phoneNumberTextField.setDisable(true);
            button.setText("EDYTUJ");
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
