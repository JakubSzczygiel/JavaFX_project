package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.net.URL;
import java.nio.file.Paths;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;

public class Main extends Application {
    private static final Logger logger = Logger.getLogger(sample.Main.class.getName());

    @Override
    public void start(Stage primaryStage) throws Exception {
        LogManager.getLogManager().readConfiguration(new FileInputStream(sample.Paths.LOGGER_CONFIGURATION_FILE_PATH));
        logger.log(Level.INFO, "Program starts");
        logger.log(Level.INFO, "Loading MainWindow");
        URL url = Paths.get(sample.Paths.MAIN_WINDOW_PATH).toUri().toURL();
        Parent root = FXMLLoader.load(url);
        logger.log(Level.INFO, "MainWindow load");
        primaryStage.setTitle("TO DO");
        primaryStage.setScene(new Scene(root));
        primaryStage.show();
        logger.log(Level.INFO, "MainWindow shown");
        primaryStage.setOnCloseRequest(e -> System.out.println("event wykryty"));
    }

    @Override
    public void stop() {
        System.out.println("Stage is closing");
    }

    public static void main(String[] args) {
        launch(args);
    }
}
