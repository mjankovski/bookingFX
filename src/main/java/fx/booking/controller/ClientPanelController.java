package fx.booking.controller;

import fx.booking.DocumentGenerator;
import fx.booking.dao.*;
import fx.booking.repository.Reservation;
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
    private Button planButton;

    @FXML
    private Button resetButton;

    @FXML
    private Button editButton;

    @FXML
    private VBox mainVBox;

    @FXML
    private HBox titleHBox;

    @FXML
    private HBox panelLabelHBox;

    @FXML
    private HBox avatarHBox;

    @FXML
    private HBox tableHBox;

    @FXML
    private HBox dataHBox;

    @FXML
    private ProgressIndicator progressIndicator;

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
    private PasswordField passField;

    @FXML
    private Label peselLabel;

    @FXML
    private Label emailLabel;

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
    private Plan plan;

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

        initTextFields();
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
    public void resetButtonClicked(ActionEvent event) {
        initTextFields();
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
            Scene scene = new Scene(parent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        });

        plan.setOnFailed(e -> {
            plan.getException().printStackTrace();
        });

        Thread thread = new Thread(plan);
        thread.start();
    }

    @FXML
    public void editButtonClicked(ActionEvent event) {
        disableWhileProgressing();
        edit = new Edit(event);
        progressIndicator.visibleProperty().bind(edit.runningProperty());
        edit.setOnSucceeded(e -> {
            enableWhileProgressing();
            Button button = (Button)event.getSource();
            if(edit.getValue() == 0) {
                button.setText("ZAPISZ");
            }
            else if(edit.getValue() == 1) {
                button.setText("EDYTUJ");
            }
            else if(edit.getValue() == 2) {
                showAlert("Błąd!", "Pola nie moga byc puste lub krotsze niz 3 znaki!", Alert.AlertType.ERROR);
                initTextFields();
                button.setText("EDYTUJ");
            }
            else if(edit.getValue() == 3) {
                showAlert("Błąd!", "Błędny adres e-mail!", Alert.AlertType.ERROR);
                initTextFields();
                button.setText("EDYTUJ");
            }
            else if(edit.getValue() == 4) {
                showAlert("Błąd!", "Błędny adres e-mail!", Alert.AlertType.ERROR);
                initTextFields();
                button.setText("EDYTUJ");
            }
            else if(edit.getValue() == 5) {
                showAlert("Błąd!", "Błędny numer karty kredytowej!", Alert.AlertType.ERROR);
                initTextFields();
                button.setText("EDYTUJ");
            }
        });

        edit.setOnFailed(e -> {
            edit.getException().printStackTrace();
        });

        Thread thread = new Thread(edit);
        thread.start();
    }

    private void disableWhileProgressing() {
        titleHBox.setDisable(true);
        panelLabelHBox.setDisable(true);
        avatarHBox.setDisable(true);
        dataHBox.setDisable(true);
        tableHBox.setDisable(true);
        planButton.setDisable(true);
        invoiceButton.setDisable(true);
    }

    private void enableWhileProgressing() {
        titleHBox.setDisable(false);
        panelLabelHBox.setDisable(false);
        avatarHBox.setDisable(false);
        dataHBox.setDisable(false);
        tableHBox.setDisable(false);
        planButton.setDisable(false);
        invoiceButton.setDisable(false);
    }

    class Plan extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/Plan.fxml"));
            Parent tableViewParent = fxmlLoader.load();
            return tableViewParent;
        }
    }

    class Edit extends Task<Integer> {
        private ActionEvent event;

        Edit(ActionEvent event) {
            this.event = event;
        }

        private void setFields(boolean isDisabled) {
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

        private void updateInformations(String login, String pw, String firstname, String lastname, String email,
                                        String creditCardNumber, String phoneNumber) {
            accountDAO.updateInformation(login, pw, firstname, lastname, email, creditCardNumber, phoneNumber);

        }

        private int checkIfPossibleToChangeData(Button button) {
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
                //showAlert("Błąd!", "Pola nie moga byc puste lub krotsze niz 3 znaki!", Alert.AlertType.ERROR);
            } catch (InvalidEmailException e) {
                return 3;
                // showAlert("Błąd!", "Błędny adres e-mail!", Alert.AlertType.ERROR);
            } catch (InvalidPhoneNumberException e) {
                return 4;
                // showAlert("Błąd!", "Błędny adres e-mail!", Alert.AlertType.ERROR);
            } catch (InvalidCreditCardNumberException e) {
                return 5;
                // showAlert("Błąd!", "Błędny numer karty kredytowej!", Alert.AlertType.ERROR);
            }
            return 1;
        }

        @Override
        protected Integer call() throws Exception {
            Button button = (Button) event.getSource();
            if (button.getText().equals("EDYTUJ")) {
                setFields(false);
                return 0;
            } else if (button.getText().equals("ZAPISZ")) {
                setFields(true);
                return checkIfPossibleToChangeData(button);
            }
            return -1;
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

    private void initTextFields() {
        resetButton.setDisable(true);
        invoiceButton.setDisable(true);
        editButton.setDisable(false);
        editButton.setText("EDYTUJ");

        nameTextfield.setText(accountDAO.getFirstname());
        nameTextfield.setDisable(true);

        surnameTextField.setText(accountDAO.getLastname());
        surnameTextField.setDisable(true);

        loginLabel.setText(accountDAO.getLogin());

        passField.setText("");
        passField.setDisable(true);

        peselLabel.setText(accountDAO.getPesel());

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
}
