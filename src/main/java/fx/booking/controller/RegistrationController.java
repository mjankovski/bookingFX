package fx.booking.controller;

import fx.booking.dao.*;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;

import org.simplejavamail.email.Email;
import org.simplejavamail.email.EmailBuilder;
import org.simplejavamail.mailer.Mailer;
import org.simplejavamail.mailer.MailerBuilder;
import org.simplejavamail.mailer.config.TransportStrategy;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.util.Objects;

@Controller
@PropertySource("classpath:mail.properties")
public class RegistrationController extends SuperController{

    @Autowired
    private Environment env;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ConfigurableApplicationContext springContext;

    @FXML
    private VBox mainVBox;

    @FXML
    private HBox titleHBox;

    @FXML
    private HBox registrationLabelHBox;

    @FXML
    private HBox dataHBox;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private Label nameLabel;

    @FXML
    private Label surnameLabel;

    @FXML
    private Label creditCardLabel;

    @FXML
    private TextField partFourCardNumberTextField1;

    @FXML
    private Label peselLabel;

    @FXML
    private TextField peselTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField directionNumberTextField;

    @FXML
    private Label plusLabel;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passwordLabel;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Label emailLabel;

    @FXML
    private Button menuButton;

    @FXML
    private Button makeAccountButton;

    @FXML
    private Label titleLabel;

    @FXML
    private TextField partFourCardNumberTextField4;

    @FXML
    private TextField partFourCardNumberTextField3;

    @FXML
    private TextField partFourCardNumberTextField2;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private Back back;

    @FXML
    private MakeAccount makeAccount;

    @FXML
    public void initialize() {
        progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);

        mainVBox.setOpacity(0);
        makeFadeIn(mainVBox);
    }


    @FXML
    public void peselTextFieldEntered(KeyEvent event) {
        if(peselTextField.getText().length() >= 11) {
            if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.TAB) {
                return;
            }
            emailTextField.requestFocus();
            String text = peselTextField.getText().substring(0,11);
            peselTextField.setText(text);
        }
    }

    @FXML
    public void cardNumberTextField1Entered (KeyEvent event) {
        if(partFourCardNumberTextField1.getText().length() >= 4) {
            if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.TAB) {
                return;
            }
            partFourCardNumberTextField2.requestFocus();
            String text = partFourCardNumberTextField1.getText().substring(0,4);
            partFourCardNumberTextField1.setText(text);
        }
    }

    @FXML
    public void cardNumberTextField2Entered(KeyEvent event) {
        if(partFourCardNumberTextField2.getText().length() >= 4) {
            if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.TAB) {
                return;
            }
            partFourCardNumberTextField3.requestFocus();
            String text = partFourCardNumberTextField2.getText().substring(0,4);
            partFourCardNumberTextField2.setText(text);
        }
    }

    @FXML
    public void cardNumberTextField3Entered(KeyEvent event) {
        if(partFourCardNumberTextField3.getText().length() >= 4) {
            if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.TAB) {
                return;
            }
            partFourCardNumberTextField4.requestFocus();
            String text = partFourCardNumberTextField3.getText().substring(0,4);
            partFourCardNumberTextField3.setText(text);
        }
    }

    @FXML
    public void cardNumberTextField4Entered(KeyEvent event) {
        if(partFourCardNumberTextField4.getText().length() >= 4) {
            if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.TAB) {
                return;
            }
            directionNumberTextField.requestFocus();
            String text = partFourCardNumberTextField4.getText().substring(0,4);
            partFourCardNumberTextField4.setText(text);
        }
    }

    @FXML
    public void directionNumberTextFieldEntered(KeyEvent event) {
        if(directionNumberTextField.getText().length() >= 3) {
            if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.TAB) {
                return;
            }
            phoneNumberTextField.requestFocus();
            String text = directionNumberTextField.getText().substring(1,3);
            directionNumberTextField.setText(text);
        }
    }

    @FXML
    public void numberTextFieldEntered(KeyEvent event) {
        if(phoneNumberTextField.getText().length() >= 9) {
            if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.TAB) {
                return;
            }
            phoneNumberTextField.requestFocus();
            String text = phoneNumberTextField.getText().substring(0,9);
            menuButton.requestFocus();
        }
    }

    @FXML
    public void registerButtonPressed(ActionEvent event){
        disableWhileProgressing();
        makeAccount = new MakeAccount();
        progressIndicator.visibleProperty().bind(makeAccount.runningProperty());
        makeAccount.setOnSucceeded(e -> {
            enableWhileProgressing();
            switch(makeAccount.getValue()) {
                case 0: {
                    showAlert("Info", "Konto zostało utworzone. Sprawdź e-mail.", Alert.AlertType.INFORMATION);
                    FXMLLoader fxmlLoader = new FXMLLoader();
                    fxmlLoader.setControllerFactory(springContext::getBean);
                    fxmlLoader.setLocation(getClass().getResource("/Welcome.fxml"));
                    Parent tableViewParent = null;
                    try {
                        tableViewParent = fxmlLoader.load();
                    } catch (IOException e1) {
                        e1.printStackTrace();
                    }
                    changeScene(event, tableViewParent);
                    break;
                }
                case 1:
                    showAlert("Błąd!", "Konto nie zostało utworzone. Pola nie moga byc puste lub krotsze niz 3 znaki!", Alert.AlertType.ERROR);
                    break;
                case 2:
                    showAlert("Błąd!", "Konto nie zostało utworzone. Błędny adres e-mail!", Alert.AlertType.ERROR);
                    break;
                case 3:
                    showAlert("Błąd!", "Konto nie zostało utworzone. Błędny numer telefonu!", Alert.AlertType.ERROR);
                    break;
                case 4:
                    showAlert("Błąd!", "Konto nie zostało utworzone. Błędny numer karty kredytowej!", Alert.AlertType.ERROR);
                    break;
                case 5:
                    showAlert("Błąd!", "Konto nie zostało utworzone. Błędny numer pesel!", Alert.AlertType.ERROR);
                    break;
                case 6:
                    showAlert("Błąd!", "Konto nie zostało utworzone. Login lub adres e-mail są już w użyciu!", Alert.AlertType.ERROR);
                    break;
            }
        });

        makeAccount.setOnFailed(e -> {
            makeAccount.getException().printStackTrace();
        });

        Thread thread = new Thread(makeAccount);
        thread.start();
    }

    @FXML
    public void menuButtonClicked(ActionEvent event) throws IOException {
        disableWhileProgressing();
        back = new Back();
        progressIndicator.visibleProperty().bind(back.runningProperty());
        back.setOnSucceeded(e -> {
            enableWhileProgressing();
            Parent parent = back.getValue();
            enableWhileProgressing();
            changeScene(event, parent);
        });

        back.setOnFailed(e -> {
            back.getException().printStackTrace();
        });

        Thread thread = new Thread(back);
        thread.start();
    }

    private void showAlert(String title, String header, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    private void sendMail(String clientEmail){
        Email email = EmailBuilder.startingBlank()
                .from("BookingFX", Objects.requireNonNull(env.getProperty("mail.from")))
                .to("Klient", clientEmail)
                .withSubject(env.getProperty("mail.subject"))
                .withPlainText(env.getProperty("mail.text"))
                .buildEmail();

        Mailer mailer = MailerBuilder
                .withSMTPServer(env.getProperty("mail.host"), 587, env.getProperty("mail.from"), env.getProperty("mail.password"))
                .withTransportStrategy(TransportStrategy.SMTP)
                .buildMailer();

        mailer.sendMail(email);
    }

    private void disableWhileProgressing() {
        titleHBox.setDisable(true);
        registrationLabelHBox.setDisable(true);
        dataHBox.setDisable(true);
        menuButton.setDisable(true);
    }

    private void enableWhileProgressing() {
        titleHBox.setDisable(false);
        registrationLabelHBox.setDisable(false);
        dataHBox.setDisable(false);
        menuButton.setDisable(false);
    }

    class Back extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            return loadScene("/Welcome.fxml");
        }
    }

    class MakeAccount extends Task<Integer> {
        @Override
        protected Integer call() throws Exception {
            try {
                accountDAO.createAccount(
                        loginTextField.getText(),
                        passField.getText(),
                        nameTextField.getText(),
                        surnameTextField.getText(),
                        emailTextField.getText(),
                        partFourCardNumberTextField1.getText() + partFourCardNumberTextField2.getText() + partFourCardNumberTextField3.getText() + partFourCardNumberTextField4.getText(),
                        peselTextField.getText(),
                        directionNumberTextField.getText() + phoneNumberTextField.getText(),
                        1
                );
                sendMail(emailTextField.getText());
            } catch (IllegalArgumentException e){
                //showAlert("Błąd!", "Konto nie zostało utworzone. Pola nie moga byc puste lub krotsze niz 3 znaki!", Alert.AlertType.ERROR);
                return 1;
            } catch (InvalidEmailException e){
                // showAlert("Błąd!", "Konto nie zostało utworzone. Błędny adres e-mail!", Alert.AlertType.ERROR);
                return 2;
            } catch (InvalidPhoneNumberException e){
                // showAlert("Błąd!", "Konto nie zostało utworzone. Błędny numer telefonu!", Alert.AlertType.ERROR);
                return 3;
            } catch (InvalidCreditCardNumberException e){
                // showAlert("Błąd!", "Konto nie zostało utworzone. Błędny numer karty kredytowej!", Alert.AlertType.ERROR);
                return 4;
            } catch (InvalidPeselException e){
                // showAlert("Błąd!", "Konto nie zostało utworzone. Błędny numer pesel!", Alert.AlertType.ERROR);
                return 5;
            }  catch (DuplicateKeyException e){
                // showAlert("Błąd!", "Konto nie zostało utworzone. Login lub adres e-mail są już w użyciu!", Alert.AlertType.ERROR);
                return 6;
            }

            return 0;
        }
    }
}

