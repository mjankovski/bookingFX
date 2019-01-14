package fx.booking.controller;

import fx.booking.dao.*;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
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
    private TextField nameTextField;

    @FXML
    private TextField surnameTextField;

    @FXML
    private TextField partFourCardNumberTextField1;

    @FXML
    private TextField peselTextField;

    @FXML
    private TextField phoneNumberTextField;

    @FXML
    private TextField directionNumberTextField;

    @FXML
    private TextField loginTextField;

    @FXML
    private TextField passField;

    @FXML
    private TextField emailTextField;

    @FXML
    private Button makeAccountButton;

    @FXML
    private TextField partFourCardNumberTextField4;

    @FXML
    private TextField partFourCardNumberTextField3;

    @FXML
    private TextField partFourCardNumberTextField2;

    @FXML
    private ProgressIndicator progressIndicator;

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
    public void cardNumberTextFieldEntered (KeyEvent event) {
        TextField textField = (TextField)event.getSource();
        if(textField.getText().length() >= 4) {
            if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.TAB) {
                return;
            }

            switch(textField.getId()) {
                case "partFourCardNumberTextField1":
                    partFourCardNumberTextField2.requestFocus();
                    break;
                case "partFourCardNumberTextField2":
                    partFourCardNumberTextField3.requestFocus();
                    break;
                case "partFourCardNumberTextField3":
                    partFourCardNumberTextField4.requestFocus();
                    break;
                case "partFourCardNumberTextField4":
                    directionNumberTextField.requestFocus();
                    break;
            }
                String text = textField.getText().substring(0,4);
                textField.setText(text);
        }
    }

    @FXML
    public void directionNumberTextFieldEntered(KeyEvent event) {
        if(directionNumberTextField.getText().length() >= 2) {
            if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.TAB) {
                return;
            }
            phoneNumberTextField.requestFocus();
            String text = directionNumberTextField.getText().substring(0,2);
            directionNumberTextField.setText(text);
        }
    }

    @FXML
    public void numberTextFieldEntered(KeyEvent event) {
        if(phoneNumberTextField.getText().length() >= 9) {
            if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.TAB) {
                return;
            }
            String text = phoneNumberTextField.getText().substring(0,9);
            phoneNumberTextField.setText(text);
            makeAccountButton.requestFocus();
        }
    }

    @FXML
    public void menuButtonPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            disableWhileProgressing(true);
            LogOut logOut = new LogOut();
            startThreadWithEndingAction(logOut, event);
        }
    }

    @FXML
    public void registerButtonClicked(ActionEvent event){
        makeAccount(event);
    }

    @FXML
    public void registerButtonPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            makeAccount(event);
        }
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

    private void makeAccount(Event event) {
        disableWhileProgressing(true);
        makeAccount = new MakeAccount();
        progressIndicator.visibleProperty().bind(makeAccount.runningProperty());
        makeAccount.setOnSucceeded(e -> {
            disableWhileProgressing(false);
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
                return 1;
            } catch (InvalidEmailException e){
                return 2;
            } catch (InvalidPhoneNumberException e){
                return 3;
            } catch (InvalidCreditCardNumberException e){
                return 4;
            } catch (InvalidPeselException e){
                return 5;
            }  catch (DuplicateKeyException e){
                return 6;
            }
            return 0;
        }
    }
}

