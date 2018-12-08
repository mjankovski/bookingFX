package fx.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.sql.SQLException;

@Controller
public class RegistrationController {

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
        int flag = 1;
        try {
            accountDAO.createAccount(
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
            flag=1;
        } catch (IllegalArgumentException e){
            System.out.println("bledny argument");
            flag = 0;
            //tu powinno byc wyswietlenie komunikatu
        } catch (DuplicateKeyException e){
            System.out.println("blad na bazie");
            flag=0;
            //tu powinno byc wyswietlenie komunikatu
        }
        /*to nie powinno byc tu*/

        if(flag==1) {
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
}

