package fx.booking.controller;

import fx.booking.dao.*;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

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
import java.security.NoSuchAlgorithmException;

@Controller
@PropertySource("classpath:mail.properties")
public class RegistrationController {

    @Autowired
    private Environment env;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private ConfigurableApplicationContext springContext;

    @FXML
    private AnchorPane accountAnchorPane;

    @FXML
    private TextField nameTextField;

    @FXML
    private TextField surnameTextfield;

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
    private TextField directionNumbertextField;

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
    public void initialize() {

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
            directionNumbertextField.requestFocus();
            String text = partFourCardNumberTextField4.getText().substring(0,4);
            partFourCardNumberTextField4.setText(text);
        }
    }

    @FXML
    public void directionNumberTextFieldEntered(KeyEvent event) {
        if(directionNumbertextField.getText().length() >= 2) {
            if(event.getCode() == KeyCode.BACK_SPACE || event.getCode() == KeyCode.TAB) {
                return;
            }
            phoneNumberTextField.requestFocus();
            String text = directionNumbertextField.getText().substring(0,2);
            directionNumbertextField.setText(text);
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
    public void registerButtonPressed(ActionEvent event) throws NoSuchAlgorithmException {
        boolean isSigned;
        try {
            isSigned = accountDAO.createAccount(
                    loginTextField.getText(),
                    passField.getText(),
                    nameTextField.getText(),
                    surnameTextfield.getText(),
                    emailTextField.getText(),
                    partFourCardNumberTextField1.getText() + partFourCardNumberTextField2.getText() + partFourCardNumberTextField3.getText() + partFourCardNumberTextField4.getText(),
                    peselTextField.getText(),
                    phoneNumberTextField.getText(),
                    1
            );
            sendMail(emailTextField.getText());
        } catch (IllegalArgumentException e){
            showAlert("Błąd!", "Konto nie zostało utworzone. Pola nie moga byc puste lub krotsze niz 3 znaki!", Alert.AlertType.ERROR);
            isSigned = false;
        } catch (InvalidEmailException e){
            showAlert("Błąd!", "Konto nie zostało utworzone. Błędny adres e-mail!", Alert.AlertType.ERROR);
            isSigned = false;
        } catch (InvalidPhoneNumberException e){
            showAlert("Błąd!", "Konto nie zostało utworzone. Błędny numer telefonu!", Alert.AlertType.ERROR);
            isSigned = false;
        } catch (InvalidCreditCardNumberException e){
            showAlert("Błąd!", "Konto nie zostało utworzone. Błędny numer karty kredytowej!", Alert.AlertType.ERROR);
            isSigned = false;;
        } catch (InvalidPeselException e){
            showAlert("Błąd!", "Konto nie zostało utworzone. Błędny numer pesel!", Alert.AlertType.ERROR);
            isSigned = false;
        }  catch (DuplicateKeyException e){
            showAlert("Błąd!", "Konto nie zostało utworzone. Login lub adres e-mail są już w użyciu!", Alert.AlertType.ERROR);
            isSigned=false;
        }

        if(isSigned) {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(springContext::getBean);
                fxmlLoader.setLocation(getClass().getResource("/Welcome.fxml"));
                Parent tableViewParent = fxmlLoader.load();
                Scene tableViewScene = new Scene(tableViewParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.show();
            } catch (Exception e) {
                e.printStackTrace();
            }
            showAlert("Info", "Konto zostało utworzone. Sprawdź e-maila.", Alert.AlertType.INFORMATION);
        }
    }

    @FXML
    public void menuButtonClicked(ActionEvent event) throws IOException {
            try {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(springContext::getBean);
                fxmlLoader.setLocation(getClass().getResource("/Welcome.fxml"));
                Parent tableViewParent = fxmlLoader.load();
                Scene tableViewScene = new Scene(tableViewParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.show();
            } catch (Exception e) {
                e.printStackTrace();
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
                .from("BookingFX", env.getProperty("mail.from"))
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
}
