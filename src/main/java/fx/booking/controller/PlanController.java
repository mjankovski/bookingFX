package fx.booking.controller;


import fx.booking.repository.ReservationKeeper;
import fx.booking.repository.Room;
import fx.booking.repository.RoomKeeper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
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

    @Autowired
    private RoomKeeper roomKeeper;

    @Autowired
    private ReservationKeeper reservationKeeper;

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
    private CheckBox onePeopleCheckBox;

    @FXML
    private CheckBox twoPeopleCheckBox;

    @FXML
    private CheckBox fourPeopleCheckBox;

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
    private ObservableList<Button> allRoomButtons;

    @FXML
    private ObservableList<Button> floor1ButtonsList;

    @FXML
    private ObservableList<Button> floor2ButtonsList;

    @FXML
    private ObservableList<Button> onePersonButtonsList;

    @FXML
    private ObservableList<Button> twoPersonButtonsList;

    @FXML
    private ObservableList<Button> fourPersonButtonsList;


    @FXML
    private ObservableMap<Button, Room> roomList;

    @FXML
    private Room selectedRoom;


    @FXML
    public void initialize() {
        floorComboBox.getItems().addAll(1,2);
        mapPane.setStyle("-fx-background-image: url(img/Floor1.png);");

        //lista wszystkich przyciskow
        allRoomButtons = FXCollections.observableArrayList();
        allRoomButtons.addAll(room101Button, room102Button, room103Button, room104Button,
                room105Button, room106Button, room107Button, room108Button, room109Button,
                room110Button, room111Button, room112Button, room113Button, room114Button, room115Button,
                room116Button, room117Button, room118Button, room119Button, room120Button, room121Button,
                room201Button, room202Button, room203Button, room204Button,
                room205Button, room206Button, room207Button, room208Button, room209Button,
                room210Button, room211Button, room212Button, room213Button, room214Button, room215Button,
                room216Button, room217Button, room218Button, room219Button, room220Button, room221Button);

        //lista przyciskow dla pokoi na pierwszym pietrze
        floor1ButtonsList = FXCollections.observableArrayList();
        floor1ButtonsList.addAll(room101Button, room102Button, room103Button, room104Button,
                room105Button, room106Button, room107Button, room108Button, room109Button,
                room110Button, room111Button, room112Button, room113Button, room114Button, room115Button,
                room116Button, room117Button, room118Button, room119Button, room120Button, room121Button);

        //lista przyciskow dla pokoi na drugim pietrze
        floor2ButtonsList = FXCollections.observableArrayList();
        floor2ButtonsList.addAll(room201Button, room202Button, room203Button, room204Button,
                room205Button, room206Button, room207Button, room208Button, room209Button,
                room210Button, room211Button, room212Button, room213Button, room214Button, room215Button,
                room216Button, room217Button, room218Button, room219Button, room220Button, room221Button);

        //lista przyciskow dla pokoi jednoosobowych
        onePersonButtonsList = FXCollections.observableArrayList();
        onePersonButtonsList.addAll(room112Button, room113Button, room114Button, room115Button,
                room116Button, room117Button, room118Button, room119Button, room212Button, room213Button,
                room214Button, room215Button, room216Button, room217Button, room218Button, room219Button);

        //lista przyciskow dla pokoi dwuosobowych
        twoPersonButtonsList = FXCollections.observableArrayList();
        twoPersonButtonsList.addAll(room101Button, room102Button, room103Button, room104Button,
                room105Button, room106Button, room107Button, room108Button, room109Button,
                room110Button, room111Button, room201Button, room202Button, room203Button, room204Button,
                room205Button, room206Button, room207Button, room208Button, room209Button,
                room210Button, room211Button);

        //lista przyciskow dla pokoi czteroosobowych
        fourPersonButtonsList = FXCollections.observableArrayList();
        fourPersonButtonsList.addAll(room120Button, room121Button, room220Button, room221Button);

        //przypisanie przyciskow poszczegolnym pokojom
        roomList = FXCollections.observableHashMap();
        ObservableList<Room> rooms = roomKeeper.getRoomList();
        for(int i = 0; i < rooms.size(); ++i) {
            Button button = allRoomButtons.get(i);
            Room room = rooms.get(i);
            roomList.put(button, room);
        }

        setFloor2ButtonsInvisible();
    }

    @FXML
    public void setFloor1ButtonsVisible() {
        for(Button button: floor1ButtonsList) {
            button.setVisible(true);
        }
    }

    @FXML
    public void setFloor2ButtonsVisible() {
        for(Button button: floor2ButtonsList) {
            button.setVisible(true);
        }
    }

    @FXML
    public void setFloor1ButtonsInvisible() {
        for(Button button: floor1ButtonsList) {
            button.setVisible(false);
        }
    }

    @FXML
    public void setFloor2ButtonsInvisible() {
        for(Button button: floor2ButtonsList) {
            button.setVisible(false);
        }
    }

    @FXML
    public void setRedButtonCollor(ObservableList<Button> list) {
        for(Button b: list) {
            b.setStyle("-fx-background-color: red");
        }
    }


    @FXML
    public void setGreenButtonCollor(ObservableList<Button> list) {
        for (Button b : list) {
            b.setStyle("-fx-background-color: green");
        }
    }



    @FXML
    public void onePeopleCheckBoxEntered(ActionEvent event) throws IOException {
        if(onePeopleCheckBox.isSelected()) {
            setGreenButtonCollor(onePersonButtonsList);
        }

        else {
            setRedButtonCollor(onePersonButtonsList);
        }
    }

    @FXML
    public void twoPeopleCheckBoxEntered(ActionEvent event) throws IOException {
        if(twoPeopleCheckBox.isSelected()) {
            setGreenButtonCollor(twoPersonButtonsList);
        }

        else {
            setRedButtonCollor(twoPersonButtonsList);
        }
    }


    @FXML
    public void fourPeopleCheckBoxEntered(ActionEvent event) throws IOException {
        if(fourPeopleCheckBox.isSelected()) {
            setGreenButtonCollor(fourPersonButtonsList);
        }

        else {
            setRedButtonCollor(fourPersonButtonsList);
        }
    }

    @FXML
    public void fromPriceTextFieldEntered(ActionEvent event) throws IOException {
        if(fourPeopleCheckBox.isSelected()) {
            setGreenButtonCollor(fourPersonButtonsList);
        }

        else {
            setRedButtonCollor(fourPersonButtonsList);
        }
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

            //przekazywanie informacji o pokoju i jego rezerwacji
            BookingController controller = fxmlLoader.getController();
            Button button = (Button)event.getSource();
            Room room = roomList.get(button);

            controller.initRoom(room);
            controller.initReservationTable(reservationKeeper.getReservationList(room.getNumber()));

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
}
