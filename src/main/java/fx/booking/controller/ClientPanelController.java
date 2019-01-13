package fx.booking.controller;

import fx.booking.DocumentGenerator;
import fx.booking.dao.AccountDAO;
import fx.booking.dao.DocumentDAO;
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
    private Plan plan;

    @FXML
    private Edit edit;

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

        progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);
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
            if(edit.getValue() == 1) {
                button.setText("ZAPISZ");
            }
            else if(edit.getValue() == 2) {
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

        @Override
        protected Integer call() throws Exception {
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
                return 1;
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
                return 2;
            }
            return 0;
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
