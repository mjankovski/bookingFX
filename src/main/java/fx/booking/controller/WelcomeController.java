package fx.booking.controller;

import fx.booking.dao.AccountDAO;
import javafx.animation.FadeTransition;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
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
    private VBox mainVBox;

    @FXML
    private HBox phraseHBox;

    @FXML
    private HBox formHBox;

    @FXML
    private HBox footerHBox;

    @FXML
    private HBox titleHBox;

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
    private Logging logging;

    @FXML
    private MakeAccount makeAccount;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    public void initialize() {
        progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);
        mainVBox.setOpacity(0);
        makeFadeIn();
    }

    @FXML
    public void loginButtonClicked(ActionEvent event) {
        disableWhileProgressing();
        logging = new Logging();
        progressIndicator.visibleProperty().bind(logging.runningProperty());
        logging.setOnSucceeded(e -> {
            enableWhileProgressing();
            Parent parent = logging.getValue();
            if(parent == null) {
                showAlertInfo("Błąd!", "Błędne dane logowania!", Alert.AlertType.ERROR);
                return;
            }
            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        });

        logging.setOnFailed(e -> {
            logging.getException().printStackTrace();
        });

        Thread thread = new Thread(logging);
        thread.start();
    }

    @FXML
    public void makeAccountButtonClicked(ActionEvent event) {
        disableWhileProgressing();
        makeAccount = new MakeAccount();
        progressIndicator.visibleProperty().bind(makeAccount.runningProperty());
        makeAccount.setOnSucceeded(e -> {
            enableWhileProgressing();
            Parent parent = makeAccount.getValue();

            Scene scene = new Scene(parent);
            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        });

        logging.setOnFailed(e -> {
            makeAccount.getException().printStackTrace();
        });

        Thread thread = new Thread(makeAccount);
        thread.start();
    }

    private void makeFadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration((Duration.seconds(1)));
        fadeTransition.setNode(mainVBox);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }



    private void showAlertInfo(String title, String header, Alert.AlertType type){
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    private void disableWhileProgressing() {
        titleHBox.setDisable(true);
        phraseHBox.setDisable(true);
        formHBox.setDisable(true);
    }

    private void enableWhileProgressing() {
        titleHBox.setDisable(false);
        phraseHBox.setDisable(false);
        formHBox.setDisable(false);
    }

    class Logging extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            Parent tableViewParent = null;
            int login = accountDAO.login(loginTextField.getText(),passTextField.getText());
            if(login==1) {
                accountDAO.getAccountInformation(loginTextField.getText());

                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(springContext::getBean);
                fxmlLoader.setLocation(getClass().getResource("/Plan.fxml"));
                tableViewParent = fxmlLoader.load();
                return tableViewParent;
            }
            else if(login==2){
                FXMLLoader fxmlLoader = new FXMLLoader();
                fxmlLoader.setControllerFactory(springContext::getBean);
                fxmlLoader.setLocation(getClass().getResource("/AdminPanel.fxml"));
                tableViewParent = fxmlLoader.load();
                return tableViewParent;
            }
            else {
                return tableViewParent;
            }
        }
    }

    class MakeAccount extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/Registration.fxml"));
            Parent tableViewParent = fxmlLoader.load();
            return tableViewParent;
        }
    }
}

