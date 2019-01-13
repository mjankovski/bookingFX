package fx.booking.controller;

import fx.booking.DocumentGenerator;
import fx.booking.dao.*;
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
    private DocumentDAO documentDAO;

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
        documentGenerator = new DocumentGenerator();
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        fromDateColumn.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        toDateColumn.setCellValueFactory(new PropertyValueFactory<>("endingDate"));

        nameTextfield.setText(accountDAO.getFirstname());
        nameTextfield.setDisable(true);

        surnameTextField.setText(accountDAO.getLastname());
        surnameTextField.setDisable(true);

        loginTextField.setText(accountDAO.getLogin());
        loginTextField.setDisable(true);

        passField.setText("");
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

        directionNumbertextField.setText(accountDAO.getPhoneNumber().substring(0,2));
        directionNumbertextField.setDisable(true);

        phoneNumberTextField.setText(accountDAO.getPhoneNumber().substring(2,11));
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
        documentGenerator.generateDocument(documentDAO.getDocumentsInformation(reservationTable.getSelectionModel().getSelectedItem().getId()));
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
        Button button = (Button) event.getSource();
        if (button.getText().equals("EDYTUJ")) {
            setFields(false);
            button.setText("ZAPISZ");
        } else if (button.getText().equals("ZAPISZ")) {
            checkIfPossibleToChangeData(button);
        }
    }

    private void updateInformations(String login, String pw, String firstname, String lastname, String email,
                                    String creditCardNumber, String phoneNumber){
        accountDAO.updateInformation(login, pw, firstname, lastname, email, creditCardNumber, phoneNumber);

    }

    private void setFields(boolean isDisabled){
        nameTextfield.setDisable(isDisabled);
        surnameTextField.setDisable(isDisabled);
        loginTextField.setDisable(isDisabled);
        passField.setDisable(isDisabled);
        peselTextField.setDisable(isDisabled);
        emailTextField.setDisable(isDisabled);
        partFourCardNumberTextField1.setDisable(isDisabled);
        partFourCardNumberTextField2.setDisable(isDisabled);
        partFourCardNumberTextField3.setDisable(isDisabled);
        partFourCardNumberTextField4.setDisable(isDisabled);
        directionNumbertextField.setDisable(isDisabled);
        phoneNumberTextField.setDisable(isDisabled);
    }

    private void checkIfPossibleToChangeData(Button button){
            try {
                accountDAO.checkDataFormat(
                        passField.getText(),
                        nameTextfield.getText(),
                        surnameTextField.getText(),
                        emailTextField.getText(),
                        partFourCardNumberTextField1.getText() + partFourCardNumberTextField2.getText() + partFourCardNumberTextField3.getText() + partFourCardNumberTextField4.getText(),
                        directionNumbertextField.getText() + phoneNumberTextField.getText()
                );
                setFields(true);
                updateInformations(accountDAO.getLogin(),passField.getText(),
                        nameTextfield.getText(),
                        surnameTextField.getText(),
                        emailTextField.getText(),
                        partFourCardNumberTextField1.getText() + partFourCardNumberTextField2.getText() + partFourCardNumberTextField3.getText() + partFourCardNumberTextField4.getText(),
                        directionNumbertextField.getText() + phoneNumberTextField.getText());

                button.setText("EDYTUJ");
            } catch (IllegalArgumentException e){
                showAlert("Błąd!", "Pola nie moga byc puste lub krotsze niz 3 znaki!", Alert.AlertType.ERROR);
            } catch (InvalidEmailException e){
                showAlert("Błąd!", "Błędny adres e-mail!", Alert.AlertType.ERROR);
            } catch (InvalidPhoneNumberException e){
                showAlert("Błąd!", "Błędny numer telefonu!", Alert.AlertType.ERROR);
            } catch (InvalidCreditCardNumberException e){
                showAlert("Błąd!", "Błędny numer karty kredytowej!", Alert.AlertType.ERROR);
            }
        }

    private void showAlert(String title, String header, Alert.AlertType type){
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
