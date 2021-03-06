package fx.booking.controller;


import fx.booking.repository.AccountRepository;
import fx.booking.repository.ReservationRepository;
import fx.booking.repository.Room;
import fx.booking.repository.RoomRepository;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.concurrent.Task;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Controller;

import java.math.BigDecimal;

@Controller
public class PlanController extends SuperController {

    @Autowired
    private ConfigurableApplicationContext springContext;

    @Autowired
    private AccountRepository accountRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private ReservationRepository reservationRepository;

    @FXML
    private VBox mainVBox;

    @FXML
    private ComboBox<Integer> floorComboBox;

    @FXML
    private CheckBox onePeopleCheckBox;

    @FXML
    private CheckBox twoPeopleCheckBox;

    @FXML
    private CheckBox fourPeopleCheckBox;

    @FXML
    private TextField fromPriceTextField;

    @FXML
    private TextField toPriceTextField;

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
    private ObservableList<Button> allRoomButtons;

    @FXML
    private ObservableMap<Button, Room> roomList;

    @FXML
    private ImageView avatar;

    @FXML
    private ProgressIndicator progressIndicator;


    @FXML
    public void initialize() {
        floorComboBox.getItems().addAll(1, 2);
        mapPane.setStyle("-fx-background-image: url(img/Floor1.png);");

        fromPriceTextField.setText("0");
        toPriceTextField.setText("1000");

        allRoomButtons = FXCollections.observableArrayList();
        allRoomButtons.addAll(room101Button, room102Button, room103Button, room104Button,
                room105Button, room106Button, room107Button, room108Button, room109Button,
                room110Button, room111Button, room112Button, room113Button, room114Button, room115Button,
                room116Button, room117Button, room118Button, room119Button, room120Button, room121Button,
                room201Button, room202Button, room203Button, room204Button,
                room205Button, room206Button, room207Button, room208Button, room209Button,
                room210Button, room211Button, room212Button, room213Button, room214Button, room215Button,
                room216Button, room217Button, room218Button, room219Button, room220Button, room221Button);

        //przypisanie przyciskow poszczegolnym pokojom
        roomList = FXCollections.observableHashMap();
        ObservableList<Room> rooms = roomRepository.getRoomList();
        for (int i = 0; i < allRoomButtons.size(); ++i) {
            Button button = allRoomButtons.get(i);
            Room room = rooms.get(i);
            roomList.put(button, room);
        }

        initFilterState();
        setFloor2ButtonsInvisible();

        progressIndicator.setProgress(ProgressBar.INDETERMINATE_PROGRESS);
        progressIndicator.setVisible(false);

        mainVBox.setOpacity(0);
        makeFadeIn(mainVBox);
    }

    @FXML
    private void setFloor1ButtonsVisible() {
        for (Button button : allRoomButtons) {
            if ((roomList.get(button)).getNumber() < 200) {
                button.setVisible(true);
            }
        }
    }

    @FXML
    private void setFloor2ButtonsVisible() {
        for (Button button : allRoomButtons) {
            if ((roomList.get(button)).getNumber() >= 200) {
                button.setVisible(true);
            }
        }
    }

    @FXML
    private void setFloor1ButtonsInvisible() {
        for (Button button : allRoomButtons) {
            if ((roomList.get(button)).getNumber() < 200) {
                button.setVisible(false);
            }
        }
    }

    @FXML
    private void setFloor2ButtonsInvisible() {
        for (Button button : allRoomButtons) {
            if ((roomList.get(button)).getNumber() >= 200) {
                button.setVisible(false);
            }
        }
    }

    @FXML
    private void filter() {
        BigDecimal minPrice = new BigDecimal(fromPriceTextField.getText());
        BigDecimal maxPrice = new BigDecimal(toPriceTextField.getText());

        for (Button button : allRoomButtons) {
            BigDecimal roomPrice = roomList.get(button).getDailyCost();
            final boolean priceCondition = (roomPrice.compareTo(minPrice) > 0 || roomPrice.compareTo(minPrice) == 0) && (roomPrice.compareTo(maxPrice) < 0 || roomPrice.compareTo(maxPrice) == 0);
            if ((onePeopleCheckBox.isSelected()) && (roomList.get(button)).getPeopleSize() == 1 && priceCondition) {
                button.setStyle("-fx-background-color: green");
            }
            else if ((twoPeopleCheckBox.isSelected()) && (roomList.get(button)).getPeopleSize() == 2 && priceCondition) {
                button.setStyle("-fx-background-color: green");
            }
            else if ((fourPeopleCheckBox.isSelected()) && (roomList.get(button)).getPeopleSize() == 4 && priceCondition) {
                button.setStyle("-fx-background-color: green");
            } else {
                button.setStyle("-fx-background-color: red");
            }
        }
    }

    @FXML
    public void peopleCheckBoxEntered() {
        try {
            filter();
        } catch (IllegalArgumentException e) {
            showAlertInfo("Podaj wartość minimalnej oraz maksymalnej ceny!");
            initFilterState();
        }
    }

    @FXML
    public void priceTextFieldEntered() {
        try {
            filter();
        } catch (IllegalArgumentException e) {
            showAlertInfo("Podaj wartość minimalnej oraz maksymalnej ceny!");
            initFilterState();
        }
    }

    @FXML
    public void roomButtonPressed(MouseEvent event) {
        disableWhileProgressing(true);
        RoomDetail roomDetail = new RoomDetail(event);
        startThreadWithEndingAction(roomDetail, event);
    }

    @FXML
    public void floorComboBoxUsed() {

        switch (floorComboBox.getValue()) {
            case 1:
                mapPane.setStyle("-fx-background-image: url(img/Floor1.png);");
                setFloor1ButtonsVisible();
                setFloor2ButtonsInvisible();
                break;
            case 2:
                mapPane.setStyle("-fx-background-image: url(img/Floor2.png);");
                setFloor1ButtonsInvisible();
                setFloor2ButtonsVisible();
                break;
        }
    }

    @FXML
    public void mainVBoxKeyPressed(KeyEvent event) {
        if(event.getCode() == KeyCode.ESCAPE) {
            disableWhileProgressing(true);
            LogOut logOut = new LogOut();
            startThreadWithEndingAction(logOut, event);
        }
    }

    @FXML
    public void avatarMouseEntered() {
        avatar.setImage(new Image("img/hover-icon.png"));
    }

    @FXML
    public void avatarMouseExited() {
        avatar.setImage(new Image("img/login-icon.png"));
    }

    @FXML
    public void avatarClicked(MouseEvent event) {
        disableWhileProgressing(true);
        ClientPanel clientPanel = new ClientPanel();
        startThreadWithEndingAction(clientPanel, event);
    }

    private void initFilterState() {
        for (Button button : allRoomButtons) {
            button.setStyle("-fx-background-color: green");
        }

        onePeopleCheckBox.setSelected(true);
        twoPeopleCheckBox.setSelected(true);
        fourPeopleCheckBox.setSelected(true);
        fromPriceTextField.setText("0");
        toPriceTextField.setText("1000");
    }

    class RoomDetail extends Task<Parent> {
        private MouseEvent event;

        RoomDetail(MouseEvent event) {
            this.event = event;
        }

        @Override
        protected Parent call() throws Exception {
            FXMLLoader fxmlLoader = setFxmlLoader("/Booking.fxml");
            Parent tableViewParent = fxmlLoader.load();

            BookingController controller = fxmlLoader.getController();
            Button button = (Button) event.getSource();
            Room room = roomList.get(button);

            controller.initRoom(room);
            controller.initReservationTable(reservationRepository.getReservationList(room.getNumber()));
            return tableViewParent;
        }
    }

    class ClientPanel extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
            fxmlLoader.setLocation(getClass().getResource("/ClientPanel.fxml"));
            Parent tableViewParent = fxmlLoader.load();

            ClientPanelController controller = fxmlLoader.getController();
            controller.initReservationTable(reservationRepository.getReservationList(accountRepository.getLogin()));
            return tableViewParent;
        }
    }
}
