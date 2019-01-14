package fx.booking.controller;

import fx.booking.dao.AccountDAO;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

@Controller
public class WelcomeController extends SuperController{

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
        makeFadeIn(mainVBox);
    }

    @FXML
    public void loginButtonClicked(ActionEvent event) {
        disableWhileProgressing(true);
        logging = new Logging();
        progressIndicator.visibleProperty().bind(logging.runningProperty());
        logging.setOnSucceeded(e -> {
            disableWhileProgressing(false);
            Parent parent = logging.getValue();
            if(parent == null) {
                showAlertInfo("Błędne dane logowania!");
                return;
            }
            changeScene(event, parent);
        });

        logging.setOnFailed(e -> {
            logging.getException().printStackTrace();
        });

        Thread thread = new Thread(logging);
        thread.start();
    }

    @FXML
    public void makeAccountButtonClicked(ActionEvent event) {
        disableWhileProgressing(true);
        makeAccount = new MakeAccount();
        progressIndicator.visibleProperty().bind(makeAccount.runningProperty());
        makeAccount.setOnSucceeded(e -> {
            disableWhileProgressing(false);
            Parent parent = makeAccount.getValue();
            changeScene(event, parent);
        });

        makeAccount.setOnFailed(e -> {
            makeAccount.getException().printStackTrace();
        });

        Thread thread = new Thread(makeAccount);
        thread.start();
    }

    class Logging extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            int login = accountDAO.login(loginTextField.getText(),passTextField.getText());
            if(login==1) {
                accountDAO.getAccountInformation(loginTextField.getText());
                return loadScene("/Plan.fxml");
            }
            else if(login==2){
                return loadScene("/AdminPanel.fxml");
            }
            else {
                return null;
            }
        }
    }

    class MakeAccount extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            return loadScene("/Registration.fxml");
        }
    }
}
