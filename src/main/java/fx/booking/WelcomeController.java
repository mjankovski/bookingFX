package fx.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

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
    private Label FXlabel;

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
    public void initialize() {
    }

    @FXML
    public void loginButtonClicked(ActionEvent event) throws IOException {
        int login = accountDAO.login(loginTextField.getText(),passTextField.getText());
        System.out.println(login);
        System.out.println(accountDAO);
        System.out.println(springContext);
        try {
            if(login>0) {
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(springContext::getBean);
                fxmlLoader.setLocation(getClass().getResource("/Plan.fxml"));
                Parent tableViewParent = fxmlLoader.load();
                Scene tableViewScene = new Scene(tableViewParent);

                Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
                window.setScene(tableViewScene);
                window.show();
            }
        }
        catch(Exception e) {
        }
    }

    @FXML
    public void makeAccountButtonClicked(ActionEvent event) throws IOException {
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
}

