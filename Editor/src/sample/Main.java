package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Path;
import java.util.Collection;

public class Main extends Application {

    private Scene scene;
    public static Collection<Path> tilesets;

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));

        scene = new Scene(root);

        primaryStage.setTitle("Artifact Editor");
        primaryStage.setScene(scene);

        primaryStage.show();


    }



    public static void main(String[] args) {
        launch(args);
    }
}
