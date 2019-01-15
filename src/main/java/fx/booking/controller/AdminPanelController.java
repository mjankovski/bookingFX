package fx.booking.controller;

import fx.booking.dao.AccountDAO;
import fx.booking.repository.User;
import fx.booking.repository.UserRepository;

import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.VBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AdminPanelController extends SuperController {

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private UserRepository userRepository;

    @FXML
    private Button deleteButton;

    @FXML
    private VBox mainVBox;

    @FXML
    private Button detailButton;

    @FXML
    private TableView<User> userTable;

    @FXML
    private TableColumn<User, String> surnameColumn;

    @FXML
    private TableColumn<User, String> nameColumn;

    @FXML
    private TableColumn<User, String> loginColumn;

    @FXML
    private TableColumn<User, String> emailColumn;

    @FXML
    private TableColumn<User, String> creditCardColumn;

    @FXML
    private TableColumn<User, String> peselColumn;

    @FXML
    private TableColumn<User, String> phoneNumberColumn;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    public void initialize() {
        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        creditCardColumn.setCellValueFactory(new PropertyValueFactory<>("creditCardNumber"));

        peselColumn.setCellValueFactory(new PropertyValueFactory<>("pesel"));

        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        deleteButton.setDisable(true);
        detailButton.setDisable(true);

        progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);

        initUserTable();
    }

    @FXML
    private void initUserTable() {
        userTable.getItems().clear();
        List<User> list = userRepository.getUserList();
        for (User user : list) {
            userTable.getItems().add(user);
        }
        makeFadeIn(mainVBox);
    }

    @FXML
    public void userSelected(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            disableWhileProgressing(true);
            LogOut logOut = new LogOut();
            startThreadWithEndingAction(logOut, event);
        }
        else if(event.getCode() == KeyCode.ENTER) {
            disableWhileProgressing(true);
            ClientDetail clientDetail = new ClientDetail();
            startThreadWithEndingAction(clientDetail, event);
        }
        else if(event.getCode() == KeyCode.DELETE) {
            disableWhileProgressing(true);
            DeleteUser deleteUser = new DeleteUser();
            startThreadWithEndingAction(deleteUser);
        }
        deleteButton.setDisable(false);
        detailButton.setDisable(false);
    }

    @FXML
    public void deleteButtonPressed() {
        disableWhileProgressing(true);
        DeleteUser deleteUser = new DeleteUser();
        startThreadWithEndingAction(deleteUser);
    }

    @FXML
    public void detailButtonClicked(ActionEvent event) {
        disableWhileProgressing(true);
        ClientDetail clientDetail = new ClientDetail();
        startThreadWithEndingAction(clientDetail, event);
    }

    @FXML
    public void mainVBoxKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            disableWhileProgressing(true);
            LogOut logOut = new LogOut();
            startThreadWithEndingAction(logOut, event);
        }
    }

    class ClientDetail extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            FXMLLoader fxmlLoader = setFxmlLoader("/ClientDetails.fxml");
            Parent tableViewParent = fxmlLoader.load();

            ClientDetailsController controller = fxmlLoader.getController();
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            String login = selectedUser.getLogin();
            controller.initReservationTable(login);

            return tableViewParent;
        }
    }

    class DeleteUser extends Task<Void> {
        @Override
        protected Void call() {
            ObservableList<User> allUsers = userTable.getItems();
            User selectedUser = userTable.getSelectionModel().getSelectedItem();

            if (selectedUser.getPermissions() != 2) {
                accountDAO.deleteUser(selectedUser.getLogin());
                allUsers.remove(selectedUser);
            }
            return null;
        }
    }
}

