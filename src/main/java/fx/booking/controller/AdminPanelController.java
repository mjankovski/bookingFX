package fx.booking.controller;

import fx.booking.repository.RoomKeeper;
import fx.booking.repository.User;
import fx.booking.repository.UserKeeper;
import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
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
    private UserKeeper userKeeper;

    @FXML
    private Button deleteButton;

    @FXML
    private VBox mainVBox;

    @FXML
    private HBox titleHBox;

    @FXML
    private Label hotelLabel;

    @FXML
    private HBox panelLabelHBox;

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
    public void initialize() {
        userTable.setEditable(true);
        userTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        surnameColumn.setCellValueFactory(new PropertyValueFactory<>("lastName"));

        nameColumn.setCellValueFactory(new PropertyValueFactory<>("firstName"));

        loginColumn.setCellValueFactory(new PropertyValueFactory<>("login"));

        emailColumn.setCellValueFactory(new PropertyValueFactory<>("email"));

        creditCardColumn.setCellValueFactory(new PropertyValueFactory<>("creditCardNumber"));

        peselColumn.setCellValueFactory(new PropertyValueFactory<>("pesel"));

        phoneNumberColumn.setCellValueFactory(new PropertyValueFactory<>("phoneNumber"));

        initUserTable();
        makeFadeIn();
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
    public void reservationSelected(MouseEvent event) {

    }

    @FXML
    public void deleteButtonPressed(ActionEvent event) {
        ObservableList<User> selectedUsers, allUsers;
        allUsers = userTable.getItems();

        selectedUsers = userTable.getSelectionModel().getSelectedItems();

        for(User user: selectedUsers) {
            //TODO Obsłużyć usuwanie zaznaczonych klientów
            allUsers.remove(user);
        }
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

