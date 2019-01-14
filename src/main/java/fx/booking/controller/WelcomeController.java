package fx.booking.controller;

import fx.booking.dao.AccountDAO;
import fx.booking.repository.AccountRepository;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class WelcomeController extends SuperController{

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private AccountRepository accountRepository;

    @FXML
    private VBox mainVBox;

    @FXML
    private TextField loginTextField;

    @FXML
    private PasswordField passTextField;

    @FXML
    private Logging logging;

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
        startThreadWithCondition(event);
    }

    @FXML
    public void loginButtonPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            disableWhileProgressing(true);
            logging = new Logging();
            startThreadWithCondition(event);
        }
    }

    @FXML
    public void makeAccountButtonClicked(ActionEvent event) {
        disableWhileProgressing(true);
        MakeAccount makeAccount = new MakeAccount();
        startThreadWithEndingAction(makeAccount, event);
    }

    @FXML
    public void makeAccountButtonPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ENTER) {
            disableWhileProgressing(true);
            MakeAccount makeAccount = new MakeAccount();
            startThreadWithEndingAction(makeAccount, event);
        }
    }

    class Logging extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            int login = accountDAO.login(loginTextField.getText(),passTextField.getText());

            if(login==1) {
                accountRepository.setAccountInformation(loginTextField.getText());
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

    private void startThreadWithCondition(Event event){
        progressIndicator.visibleProperty().bind(logging.runningProperty());

        task.setOnSucceeded(e -> {
            disableWhileProgressing(false);
            Parent parent = task.getValue();
            if(parent == null) {
                showAlertInfo("Błędne dane logowania!");
                return;
            }
            changeScene(event, parent);
        });

        task.setOnFailed(e -> {
            task.getException().printStackTrace();
        });

        Thread thread = new Thread(task);
        thread.start();
    }
}
