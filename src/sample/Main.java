package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Amb el main el que fem es crear la finestra de visualització del programa i carregar l'arxiu fxml on esta el disseny
 */

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Gran Prix Javier");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(root, 950, 650));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
    }
}
