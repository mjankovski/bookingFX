package fx.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class WelcomeController {

    @Autowired
    private ConfigurableApplicationContext springContext;

    @Autowired
    private AccountDAO accountDAO;

    @FXML
    private AnchorPane menuAnchorPane;

    @FXML
    private Label descriptionLabel;

    @FXML
    private Label fxLabel;

    @FXML
    private Label hotelLabel;

    @FXML
    private Pane formPane;

    @FXML
    private Button logButton;

    @FXML
    private Button makeAccountButton;

    @FXML
    private TextField loginTextField;

    @FXML
    private Label loginLabel;

    @FXML
    private Label passLabel;

    @FXML
    private PasswordField passTextField;

    @FXML
    private Button authorsButton;

    @FXML
    private Label wrongDataLabel;

    @FXML
    public void initialize() {
    }

    @FXML
    public void loginButtonClicked(ActionEvent event) {
        int login = accountDAO.login(loginTextField.getText(),passTextField.getText());
        try {
            if(login>0) {
                accountDAO.getAccountInformation(loginTextField.getText());
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(springContext::getBean);
                fxmlLoader.setLocation(getClass().getResource("/Plan.fxml"));
                Parent tableViewParent = fxmlLoader.load();
                Scene tableViewScene = new Scene(tableViewParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.centerOnScreen();
                window.show();
            }
            else {
                showAlertInfo("Błąd!", "Błędne dane logowania!", Alert.AlertType.ERROR);
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void makeAccountButtonClicked(ActionEvent event) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/Registration.fxml"));
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

    private void showAlertInfo(String title, String header, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }
}

