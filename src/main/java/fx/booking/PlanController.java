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
    private Button room101Button;

    @FXML
    private Button room102Button;

    @FXML
    private Button room103Button;

    @FXML
    private Button room104Button;

    @FXML
    private Button room105Button;

    @FXML
    private Button room106Button;

    @FXML
    private Button room107Button;

    @FXML
    private Button room108Button;

    @FXML
    private Button room209Button;

    @FXML
    private Button room110Button;

    @FXML
    private Button room111Button;

    @FXML
    private Button room112Button;

    @FXML
    private Button room113Button;

    @FXML
    private Button room114Button;

    @FXML
    private Button room115Button;

    @FXML
    private Button room116Button;

    @FXML
    private Button room117Button;

    @FXML
    private Button room118Button;

    @FXML
    private Button room119Button;

    @FXML
    private Button room120Button;

    @FXML
    private Button room121Button;

    @FXML
    private Button room122Button;

    @FXML
    private Button room123Button;

    @FXML
    private Button room124Button;

    @FXML
    private Button room125Button;

    @FXML
    private Button room126Button;

    @FXML
    private Button room127Button;

    @FXML
    private Button room128Button;

    @FXML
    private Button room129Button;

    @FXML
    private Button room130Button;

    @FXML
    private Button room131Button;

    @FXML
    private Button room132Button;

    @FXML
    private Button room133Button;

    @FXML
    private Button room134Button;

    @FXML
    private Button room135Button;

    @FXML
    private Button room136Button;

    @FXML
    private Button room137Button;

    @FXML
    private Button room138Button;

    @FXML
    private Button room139Button;

    @FXML
    private Button room140Button;

    @FXML
    private Button room201Button;

    @FXML
    private Button room202Button;

    @FXML
    private Button room203Button;

    @FXML
    private Button room204Button;

    @FXML
    private Button room205Button;

    @FXML
    private Button room106Button1;

    @FXML
    private Button room207Button;

    @FXML
    private Button room208Button;

    @FXML
    private Button room210Button;

    @FXML
    private Button room211Button;

    @FXML
    public void initialize() {
        floorComboBox.getItems().addAll(1,2,3);
        mapPane.setStyle("-fx-background-image: url(img/Floor1.png);");
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
                mapPane.setStyle("-fx-background-image: url(img/Floor1.png);");
                room201Button.setVisible(false);
                break;
            case 2:
                mapPane.setStyle("-fx-background-image: url(img/Floor2.png);");
                room201Button.setVisible(true);
                break;
            case 3:
                mapPane.setStyle("-fx-background-image: url(img/Floor3.png);");
                break;
        }
    }
}
