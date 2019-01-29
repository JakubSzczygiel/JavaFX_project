package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;
import java.nio.file.Paths;

public class Main extends Application {

    static final String MAIN_WINDOW_PATH="C:\\Users\\Jakub\\IdeaProjects\\JavaFX_z_pom\\src\\main\\resources\\MainWindow.fxml";

    @Override
    public void start(Stage primaryStage) throws Exception{
        URL url= Paths.get(MAIN_WINDOW_PATH).toUri().toURL();
        Parent root=FXMLLoader.load(url);
        primaryStage.setTitle("TO DO");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        primaryStage.setOnCloseRequest(e -> System.out.println("event wykryty"));
    }

    @Override
    public void stop(){
        System.out.println("Stage is closing");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
