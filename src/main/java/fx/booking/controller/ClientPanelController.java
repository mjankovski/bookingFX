package fx.booking.controller;

import fx.booking.DocumentGenerator;
import fx.booking.dao.*;
import fx.booking.repository.Reservation;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;

@Controller
public class ClientPanelController extends SuperController {

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
    private Button invoiceButton;

    @FXML
    private Button editButton;

    @FXML
    private VBox mainVBox;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private DocumentGenerator documentGenerator;

    @FXML
    private TextField nameTextfield;

    @FXML
    private TextField surnameTextField;

    @FXML
    private Label loginLabel;

    @FXML
    private PasswordField passField;

    @FXML
    private Label peselLabel;

    @FXML
    private TextField emailTextField;

    @FXML
    private TextField partFourCardNumberTextField1;

    @FXML
    private TextField partFourCardNumberTextField2;

    @FXML
    private Button resetButton;

    @FXML
    private TextField partFourCardNumberTextField3;

    @FXML
    private TextField partFourCardNumberTextField4;

    @FXML
    private TextField directionNumbertextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private Edit edit;

    @FXML
    public void initialize() {
        documentGenerator = new DocumentGenerator();
        roomNumberColumn.setCellValueFactory(new PropertyValueFactory<>("roomNumber"));
        fromDateColumn.setCellValueFactory(new PropertyValueFactory<>("beginningDate"));
        toDateColumn.setCellValueFactory(new PropertyValueFactory<>("endingDate"));

        progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);
        invoiceButton.setDisable(true);

        setClientInfo();
        disableData(true);
    }

    @FXML
    void initReservationTable(ObservableList<Reservation> list) {
        reservationTable.getItems().clear();
        for (Reservation reservation : list) {
            reservationTable.getItems().add(reservation);
        }
        makeFadeIn(mainVBox);
    }

    @FXML
    public void reservationSelected() {
        invoiceButton.setDisable(false);
    }

    @FXML
    public void invoiceButtonClicked() {
        documentGenerator.generateDocument(documentDAO.getDocumentsInformation(reservationTable.getSelectionModel().getSelectedItem().getId()));
    }

    @FXML
    public void resetButtonClicked() {
        defaultDataTableState();
    }

    @FXML
    public void editButtonClicked(ActionEvent event) {
        disableWhileProgressing(true);
        edit = new Edit(event);
        progressIndicator.visibleProperty().bind(edit.runningProperty());
        edit.setOnSucceeded(e -> {
            disableWhileProgressing(false);
            Button button = (Button) event.getSource();
            if (edit.getValue() == 0) {
                button.setText("ZAPISZ");
            } else if (edit.getValue() == 1) {
                button.setText("EDYTUJ");
            } else if (edit.getValue() == 2) {
                showAlertInfo("Pola nie moga byc puste lub krotsze niz 3 znaki!");
                defaultDataTableState();
            } else if (edit.getValue() == 3) {
                showAlertInfo("Błędny adres e-mail!");
                defaultDataTableState();
            } else if (edit.getValue() == 4) {
                showAlertInfo("Błędny numer telefonu!");
                defaultDataTableState();
            } else if (edit.getValue() == 5) {
                showAlertInfo("Błędny numer karty kredytowej!");
                defaultDataTableState();
            }
        });

        edit.setOnFailed(e -> {
            edit.getException().printStackTrace();
        });

        Thread thread = new Thread(edit);
        thread.start();
    }

    class Edit extends Task<Integer> {
        private ActionEvent event;

        Edit(ActionEvent event) {
            this.event = event;
        }

        private void updateInformations(String login, String pw, String firstname, String lastname, String email,
                                        String creditCardNumber, String phoneNumber) {
            accountDAO.updateInformation(login, pw, firstname, lastname, email, creditCardNumber, phoneNumber);

        }

        private int checkIfPossibleToChangeData() {
            try {
                accountDAO.checkDataFormat(
                        passField.getText(),
                        nameTextfield.getText(),
                        surnameTextField.getText(),
                        emailTextField.getText(),
                        partFourCardNumberTextField1.getText() + partFourCardNumberTextField2.getText() + partFourCardNumberTextField3.getText() + partFourCardNumberTextField4.getText(),
                        directionNumbertextField.getText() + phoneNumberTextField.getText()
                );
                updateInformations(accountDAO.getLogin(), passField.getText(),
                        nameTextfield.getText(),
                        surnameTextField.getText(),
                        emailTextField.getText(),
                        partFourCardNumberTextField1.getText() + partFourCardNumberTextField2.getText() + partFourCardNumberTextField3.getText() + partFourCardNumberTextField4.getText(),
                        directionNumbertextField.getText() + phoneNumberTextField.getText());
            } catch (IllegalArgumentException e) {
                return 2;
            } catch (InvalidEmailException e) {
                return 3;
            } catch (InvalidPhoneNumberException e) {
                return 4;
            } catch (InvalidCreditCardNumberException e) {
                return 5;
            }
            return 1;
        }

        @Override
        protected Integer call() {
            Button button = (Button) event.getSource();
            if (button.getText().equals("EDYTUJ")) {
                disableData(false);
                return 0;
            } else if (button.getText().equals("ZAPISZ")) {
                disableData(true);
                return checkIfPossibleToChangeData();
            }
            return -1;
        }
    }

    private void defaultDataTableState() {
        disableData(true);
        setClientInfo();
        editButton.setText("EDYTUJ");
        editButton.setDisable(false);
    }

    private void disableData(boolean isDisabled) {
        nameTextfield.setDisable(isDisabled);
        surnameTextField.setDisable(isDisabled);
        passField.setDisable(isDisabled);
        emailTextField.setDisable(isDisabled);
        partFourCardNumberTextField1.setDisable(isDisabled);
        partFourCardNumberTextField2.setDisable(isDisabled);
        partFourCardNumberTextField3.setDisable(isDisabled);
        partFourCardNumberTextField4.setDisable(isDisabled);
        directionNumbertextField.setDisable(isDisabled);
        phoneNumberTextField.setDisable(isDisabled);
        resetButton.setDisable(isDisabled);
    }

    private void setClientInfo() {
        nameTextfield.setText(accountDAO.getFirstname());
        surnameTextField.setText(accountDAO.getLastname());
        loginLabel.setText(accountDAO.getLogin());
        passField.setText("");
        peselLabel.setText(accountDAO.getPesel());
        emailTextField.setText(accountDAO.getEmail());
        partFourCardNumberTextField1.setText(accountDAO.getCreditCardNumber().substring(0, 4));
        partFourCardNumberTextField2.setText(accountDAO.getCreditCardNumber().substring(4, 8));
        partFourCardNumberTextField3.setText(accountDAO.getCreditCardNumber().substring(8, 12));
        partFourCardNumberTextField4.setText(accountDAO.getCreditCardNumber().substring(12, 16));
        directionNumbertextField.setText(accountDAO.getPhoneNumber().substring(0, 2));
        phoneNumberTextField.setText(accountDAO.getPhoneNumber().substring(2, 11));
    }
}
