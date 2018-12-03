package fx.booking;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;
import java.io.IOException;

@Controller
public class BookingController {

    @Autowired
    private ConfigurableApplicationContext springContext;

    @FXML
    private AnchorPane informationAnchorPane;

    @FXML
    private ScrollPane bookingScrollPane;

    @FXML
    private TextArea outputTextArea;

    @FXML
    private Pane outputPane;

    @FXML
    private Label titleLabel;

    @FXML
    private Pane infoPane;

    @FXML
    private Label peopleLabel;

    @FXML
    private Label costLabel;

    @FXML
    private Label bookDateLabel;

    @FXML
    private Label floorLabel;

    @FXML
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

    @FXML
    private Label fromLabel;

    @FXML
    private Label toLabel;

    @FXML
    private Button bookButton;

    @FXML
    private ButtonBar buttonBar;

    @FXML
    private Button menuButton;

    @FXML
    private Button planButton;

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
    public void planButtonClicked(ActionEvent event) throws IOException {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/Plan.fxml"));
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
    public void initialize() {

    }
}

