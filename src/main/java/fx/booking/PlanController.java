package fx.booking;

import com.sun.org.apache.xpath.internal.functions.FuncFloor;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

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
    private DatePicker fromDatePicker;

    @FXML
    private DatePicker toDatePicker;

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
    private Button room109Button;

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
    private Button room206Button;

    @FXML
    private Button room207Button;

    @FXML
    private Button room208Button;

    @FXML
    private Button room209Button;

    @FXML
    private Button room210Button;

    @FXML
    private Button room211Button;

    @FXML
    private Button room212Button;

    @FXML
    private Button room213Button;

    @FXML
    private Button room214Button;

    @FXML
    private Button room215Button;

    @FXML
    private Button room216Button;

    @FXML
    private Button room217Button;

    @FXML
    private Button room218Button;

    @FXML
    private Button room219Button;

    @FXML
    private Button room220Button;

    @FXML
    private Button room221Button;


    @FXML
    public void initialize() {
        floorComboBox.getItems().addAll(1,2);
        mapPane.setStyle("-fx-background-image: url(img/Floor1.png);");
        room201Button.setVisible(false);
        room202Button.setVisible(false);
        room203Button.setVisible(false);
        room204Button.setVisible(false);
        room205Button.setVisible(false);
        room206Button.setVisible(false);
        room207Button.setVisible(false);
        room208Button.setVisible(false);
        room209Button.setVisible(false);
        room210Button.setVisible(false);
        room211Button.setVisible(false);
        room212Button.setVisible(false);
        room213Button.setVisible(false);
        room214Button.setVisible(false);
        room215Button.setVisible(false);
        room216Button.setVisible(false);
        room217Button.setVisible(false);
        room218Button.setVisible(false);
        room219Button.setVisible(false);
        room220Button.setVisible(false);
        room221Button.setVisible(false);
    }

    @FXML
    public void twoPeopleCheckBoxEntered(ActionEvent event) throws IOException {

    }

    @FXML
    public void threePeopleCheckBoxEntered(ActionEvent event) throws IOException {

    }

    @FXML
    public void fourPeopleCheckBoxEntered(ActionEvent event) throws IOException {

    }

    @FXML
    public void fromPriceTextFieldEntered(ActionEvent event) throws IOException {

    }

    @FXML
    public void toPriceTextFieldEntered(ActionEvent event) throws IOException {

    }

    @FXML
    public void fromDatePickerAccessed(ActionEvent event) throws IOException {

    }

    @FXML
    public void toDatePickerAccessed(ActionEvent event) throws IOException {

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
    public void roomButtonPressed(MouseEvent event) throws IOException {
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
                room101Button.setVisible(true);
                room102Button.setVisible(true);
                room103Button.setVisible(true);
                room104Button.setVisible(true);
                room105Button.setVisible(true);
                room106Button.setVisible(true);
                room107Button.setVisible(true);
                room108Button.setVisible(true);
                room109Button.setVisible(true);
                room110Button.setVisible(true);
                room111Button.setVisible(true);
                room112Button.setVisible(true);
                room113Button.setVisible(true);
                room114Button.setVisible(true);
                room115Button.setVisible(true);
                room116Button.setVisible(true);
                room117Button.setVisible(true);
                room118Button.setVisible(true);
                room119Button.setVisible(true);
                room120Button.setVisible(true);
                room121Button.setVisible(true);
                room201Button.setVisible(false);
                room202Button.setVisible(false);
                room203Button.setVisible(false);
                room204Button.setVisible(false);
                room205Button.setVisible(false);
                room206Button.setVisible(false);
                room207Button.setVisible(false);
                room208Button.setVisible(false);
                room209Button.setVisible(false);
                room210Button.setVisible(false);
                room211Button.setVisible(false);
                room212Button.setVisible(false);
                room213Button.setVisible(false);
                room214Button.setVisible(false);
                room215Button.setVisible(false);
                room216Button.setVisible(false);
                room217Button.setVisible(false);
                room218Button.setVisible(false);
                room219Button.setVisible(false);
                room220Button.setVisible(false);
                room221Button.setVisible(false);
                break;
            case 2:
                mapPane.setStyle("-fx-background-image: url(img/Floor2.png);");
                room101Button.setVisible(false);
                room102Button.setVisible(false);
                room103Button.setVisible(false);
                room104Button.setVisible(false);
                room105Button.setVisible(false);
                room106Button.setVisible(false);
                room107Button.setVisible(false);
                room108Button.setVisible(false);
                room109Button.setVisible(false);
                room110Button.setVisible(false);
                room111Button.setVisible(false);
                room112Button.setVisible(false);
                room113Button.setVisible(false);
                room114Button.setVisible(false);
                room115Button.setVisible(false);
                room116Button.setVisible(false);
                room117Button.setVisible(false);
                room118Button.setVisible(false);
                room119Button.setVisible(false);
                room120Button.setVisible(false);
                room121Button.setVisible(false);
                room201Button.setVisible(true);
                room202Button.setVisible(true);
                room203Button.setVisible(true);
                room204Button.setVisible(true);
                room205Button.setVisible(true);
                room206Button.setVisible(true);
                room207Button.setVisible(true);
                room208Button.setVisible(true);
                room209Button.setVisible(true);
                room210Button.setVisible(true);
                room211Button.setVisible(true);
                room212Button.setVisible(true);
                room213Button.setVisible(true);
                room214Button.setVisible(true);
                room215Button.setVisible(true);
                room216Button.setVisible(true);
                room217Button.setVisible(true);
                room218Button.setVisible(true);
                room219Button.setVisible(true);
                room220Button.setVisible(true);
                room221Button.setVisible(true);
                break;
        }
    }
}
