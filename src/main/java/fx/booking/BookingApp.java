package fx.booking;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.application.Application;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class BookingApp extends Application{

    private ConfigurableApplicationContext springContext;
    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void init(){
            springContext = new AnnotationConfigApplicationContext(SpringJDBCConfiguration.class);
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
        }

        @Override
        public void start(Stage primaryStage) throws Exception{
            fxmlLoader.setLocation(getClass().getResource("/Welcome.fxml"));
            Parent rootNode = fxmlLoader.load();

            primaryStage.setTitle("bookingFX");
            primaryStage.setScene(new Scene(rootNode));
            primaryStage.centerOnScreen();
            primaryStage.show();
        }

        @Override
        public void stop() {
            springContext.stop();
        }
    }