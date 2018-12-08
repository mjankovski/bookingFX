package fx.booking;

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
    private TextField passwordTextField;

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
    public void menuButtonClicked(ActionEvent event) throws IOException {
        /*to nie powinno byc tu*/
        boolean isSigned;
        try {
                    isSigned = accountDAO.createAccount(
                    loginTextField.getText(),
                    passwordTextField.getText(),
                    nameTextField.getText(),
                    surnameTextfield.getText(),
                    emailTextField.getText(),
                    partFourCardNumberTextField1.getText() + partFourCardNumberTextField2.getText() + partFourCardNumberTextField3.getText() + partFourCardNumberTextField4.getText(),
                    peselTextField.getText(),
                    phoneNumberTextField.getText(),
                    1
            );
                    sendMail(emailTextField.getText());
        } catch (InvalidEmailException e){
            showAlert("Błąd!", "Konto nie zostało utworzone. Błędny adres e-mail!");
            isSigned = false;
        } catch (InvalidPhoneNumberException e){
            showAlert("Błąd!", "Konto nie zostało utworzone. Błędny adres numer telefonu!");
            isSigned = false;
        } catch (InvalidCreditCardNumberException e){
            showAlert("Błąd!", "Konto nie zostało utworzone. Błędny numer karty kredytowej!");
            isSigned = false;;
        } catch (InvalidPeselException e){
            showAlert("Błąd!", "Konto nie zostało utworzone. Błędny numer pesel!");
            isSigned = false;
        }  catch (DuplicateKeyException e){
            showAlert("Błąd!", "Konto nie zostało utworzone. Login lub adres e-mail są już w użyciu!");
            isSigned=false;
        }
        /*to nie powinno byc tu*/

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
        }
    }

    private void showAlert(String title, String header){
        Alert alert = new Alert(Alert.AlertType.ERROR);
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

