package fx.booking.controller;

import fx.booking.dao.AccountDAO;
import fx.booking.repository.Room;
import fx.booking.repository.RoomKeeper;
import fx.booking.repository.User;
import fx.booking.repository.UserKeeper;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;
import java.util.List;

@Controller
public class AdminPanelController {

    @Autowired
    private ConfigurableApplicationContext springContext;

    @Autowired
    private AccountDAO accountDAO;

    @Autowired
    private UserKeeper userKeeper;

    @FXML
    private Button deleteButton;

    @FXML
    private VBox mainVBox;

    @FXML
    private Button detailButton;

    @FXML
    private HBox titleHBox;

    @FXML
    private Label hotelLabel;

    @FXML
    private HBox panelLabelHBox;

    @FXML
    private HBox tabelHBox;

    @FXML
    private HBox buttonsHBox;

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
    private Button logOutButton;

    @FXML
    private ProgressIndicator progressIndicator;

    @FXML
    private ClientDetail clientDetail;

    @FXML
    private LogOut logOut;

    @FXML
    private DeleteUser deleteUser;

    @FXML
    public void initialize() {
        //userTable.setEditable(true);
        //userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

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

    @FXML void initUserTable() {
        userTable.getItems().clear();
        List<User> list = userKeeper.getUserList();
        for (User user : list) {
            userTable.getItems().add(user);
        }
        makeFadeIn();
    }


    @FXML
    public void logOutButtonClicked(ActionEvent event) {
        disableWhileProgressing();
        logOut = new LogOut();
        progressIndicator.visibleProperty().bind(logOut.runningProperty());
        logOut.setOnSucceeded(e -> {
            enableWhileProgressing();
            Parent parent = logOut.getValue();
            enableWhileProgressing();
            Scene scene = new Scene(parent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        });

        logOut.setOnFailed(e -> {
            logOut.getException().printStackTrace();
        });

        Thread thread = new Thread(logOut);
        thread.start();
    }

    @FXML
    public void userSelected(MouseEvent event) {
        deleteButton.setDisable(false);
        detailButton.setDisable(false);
    }

    @FXML
    public void deleteButtonPressed(ActionEvent event) {
        disableWhileProgressing();
        deleteUser = new DeleteUser();
        progressIndicator.visibleProperty().bind(deleteUser.runningProperty());
        deleteUser.setOnSucceeded(e -> {
            enableWhileProgressing();
        });

        deleteUser.setOnFailed(e -> {
            deleteUser.getException().printStackTrace();
        });

        Thread thread = new Thread(deleteUser);
        thread.start();
    }

    @FXML
    public void detailButtonClicked(ActionEvent event) {
        disableWhileProgressing();
        clientDetail = new ClientDetail();
        progressIndicator.visibleProperty().bind(clientDetail.runningProperty());
        clientDetail.setOnSucceeded(e -> {
            enableWhileProgressing();
            Parent parent = clientDetail.getValue();
            enableWhileProgressing();
            Scene scene = new Scene(parent);

            Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
            window.setScene(scene);
            window.show();
        });

        clientDetail.setOnFailed(e -> {
            clientDetail.getException().printStackTrace();
        });

        Thread thread = new Thread(clientDetail);
        thread.start();
    }

    class ClientDetail extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/ClientDetails.fxml"));
            Parent tableViewParent = fxmlLoader.load();

            //przekazywanie informacji o kliencie
            ClientDetailsController controller = fxmlLoader.getController();
            User selectedUser = userTable.getSelectionModel().getSelectedItem();
            String login = selectedUser.getLogin();
            controller.initReservationTable(login);

            return tableViewParent;
        }
    }

    class LogOut extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/Welcome.fxml"));
            Parent tableViewParent = fxmlLoader.load();
            return tableViewParent;
        }
    }

    class DeleteUser extends Task<Void> {
        @Override
        protected Void call() throws Exception {
            ObservableList<User> allUsers;
            User selectedUser;
            allUsers = userTable.getItems();

            selectedUser = userTable.getSelectionModel().getSelectedItem();

            if(selectedUser.getPermissions()!=2) {
                accountDAO.deleteUser(selectedUser.getLogin());
                allUsers.remove(selectedUser);
            }

            return null;
        }
    }

    private void disableWhileProgressing() {
        titleHBox.setDisable(true);
        panelLabelHBox.setDisable(true);
        tabelHBox.setDisable(true);
        buttonsHBox.setDisable(true);
        logOutButton.setDisable(true);
    }

    private void enableWhileProgressing() {
            titleHBox.setDisable(false);
            panelLabelHBox.setDisable(false);
            tabelHBox.setDisable(false);
            buttonsHBox.setDisable(false);
            logOutButton.setDisable(false);
    }

    private void makeFadeIn() {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration((Duration.seconds(1)));
        fadeTransition.setNode(mainVBox);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

}

