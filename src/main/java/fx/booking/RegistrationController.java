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
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class RegistrationController {

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
    private TextField PartFourCardNumberLabel1;

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
    private TextField PartFourCardNumberLabel4;

    @FXML
    private TextField PartFourCardNumberLabel3;

    @FXML
    private TextField PartFourCardNumberLabel2;


    @FXML
    public void menuButtonClicked(ActionEvent event) throws IOException {
        try {
            Parent tableViewParent = FXMLLoader.load(getClass().getResource("/Welcome.fxml"));
            Scene tableViewScene = new Scene(tableViewParent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(tableViewScene);
            window.show();
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }
}

