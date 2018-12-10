package fx.booking;

import com.sun.org.apache.xpath.internal.functions.FuncFloor;
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
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.io.IOException;

@Controller
public class PlanController {

    @Autowired
    private ConfigurableApplicationContext springContext;

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
    private ComboBox<Integer> floorComboBox;

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
    private Pane mapPane;

    @FXML
    public void initialize() {
        floorComboBox.getItems().addAll(1,2,3);
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
            e.printStackTrace();
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

    @FXML
    public void floorComboBoxUsed(ActionEvent e) {

        switch(floorComboBox.getValue()) {
            case 1:
                mapPane.setStyle("-fx-background-image: url(img/Floor1.jpg);");
                break;
            case 2:
                mapPane.setStyle("-fx-background-image: url(img/Floor2.jpg);");
                break;
            case 3:
                mapPane.setStyle("-fx-background-image: url(img/Floor3.jpg);");
                break;
        }
    }

}
