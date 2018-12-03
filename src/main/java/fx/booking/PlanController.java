package fx.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class PlanController {
    @Autowired
    private ConfigurableApplicationContext springContext;

    @Autowired
    private AccountDAO accountDAO;

    @FXML
    private AnchorPane planAnchorPane;

    @FXML
    private ScrollPane planScrollPane;

    @FXML
    private AnchorPane imageAnchorPane;

    @FXML
    private ImageView planImageView;

    @FXML
    private Label titleLabel;

    @FXML
    private ComboBox<?> floorComboBox;

    @FXML
    private Label filtersLabel;

    @FXML
    private CheckBox twoPeopleCheckBox;

    @FXML
    private CheckBox threePeopleChechBox;

    @FXML
    private CheckBox fourPeopleCheckBox;

    @FXML
    private CheckBox fivePeopleCheckBox;

    @FXML
    private Label pepoleNumberLabel;

    @FXML
    private Button menuButton;

    @FXML
    public void initialize() {
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
        }
        catch(Exception e) {
        }
    }

    @FXML
    public void sampleButtonClicked(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/Booking.fxml"));
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
