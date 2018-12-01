package fx.booking;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import javafx.application.Application;

    @SpringBootApplication
    public class BookingApp extends Application{
    private ConfigurableApplicationContext springContext;
    private Parent rootNode;
    private FXMLLoader fxmlLoader;

    public static void main(String[] args) {
            launch(args);
        }

        @Override
        public void init(){
            springContext = SpringApplication.run(BookingApp.class);
            fxmlLoader = new FXMLLoader();
            fxmlLoader.setControllerFactory(springContext::getBean);
        }

        @Override
        public void start(Stage primaryStage) throws Exception{
            rootNode = fxmlLoader.load(getClass().getResource("/Booking.fxml"));
            primaryStage.setTitle("bookingFX");
            primaryStage.setScene(new Scene(rootNode));
            primaryStage.setResizable(false);
            primaryStage.show();
        }

        @Override
        public void stop() {
            springContext.stop();
        }
    }