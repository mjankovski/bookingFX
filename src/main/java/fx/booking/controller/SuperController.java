package fx.booking.controller;

import javafx.animation.FadeTransition;
import javafx.collections.ObservableList;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;

import java.io.IOException;

abstract class SuperController {
    @FXML
    private VBox mainVBox;

    @FXML
    private HBox footerHBox;

    @FXML
    private Plan plan;

    @FXML
    private LogOut logOut;

    @FXML
    private ProgressIndicator progressIndicator;

    @Autowired
    private ConfigurableApplicationContext springContext;

    void makeFadeIn(VBox mainVBox) {
        FadeTransition fadeTransition = new FadeTransition();
        fadeTransition.setDuration((Duration.seconds(1)));
        fadeTransition.setNode(mainVBox);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);
        fadeTransition.play();
    }

    void showAlertInfo(String header){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Błąd!");
        alert.setHeaderText(header);
        alert.showAndWait();
    }

    void changeScene(Event event, Parent parent){
        Scene scene = new Scene(parent);
        Stage window = (Stage) ((Node) event.getSource()).getScene().getWindow();
        window.setScene(scene);
        window.show();
    }

    Parent loadScene(String path) throws Exception{
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
        fxmlLoader.setLocation(getClass().getResource(path));
        return fxmlLoader.load();
    }

    FXMLLoader setFxmlLoader(String path) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setControllerFactory(springContext::getBean);
        fxmlLoader.setLocation(getClass().getResource(path));
        return fxmlLoader;
    }

    void disableWhileProgressing(boolean isDisabled) {
        ObservableList<Node> children = mainVBox.getChildren();
        for(Node child: children) {
            if(!child.getId().equals("footerHBox")) {
                child.setDisable(isDisabled);
            }

        }
        ObservableList<Node> footerChildren = footerHBox.getChildren();
        for(Node child: footerChildren) {
            if(!child.getId().equals("progressIndicator")) {
                System.out.println(child.getId());
                child.setDisable(isDisabled);
            }

        }
    }

    @FXML
    public void planButtonClicked(ActionEvent event) throws IOException {
        disableWhileProgressing(true);
        plan = new Plan();
        progressIndicator.visibleProperty().bind(plan.runningProperty());
        plan.setOnSucceeded(e -> {
            disableWhileProgressing(false);
            Parent parent = plan.getValue();

            changeScene(event, parent);
        });

        plan.setOnFailed(e -> {
            plan.getException().printStackTrace();
        });

        Thread thread = new Thread(plan);
        thread.start();
    }

    @FXML
    public void menuButtonClicked(ActionEvent event) throws IOException {
        disableWhileProgressing(true);
        logOut = new LogOut();
        progressIndicator.visibleProperty().bind(logOut.runningProperty());
        logOut.setOnSucceeded(e -> {
            disableWhileProgressing(false);
            Parent parent = logOut.getValue();

            changeScene(event, parent);
        });

        logOut.setOnFailed(e -> {
            logOut.getException().printStackTrace();
        });

        Thread thread = new Thread(logOut);
        thread.start();
    }



    class Plan extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            return loadScene("/Plan.fxml");
        }
    }

    class LogOut extends Task<Parent> {
        @Override
        protected Parent call() throws Exception {
            return loadScene("/Welcome.fxml");
        }
    }
}
