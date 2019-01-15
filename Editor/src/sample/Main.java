package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class Main extends Application {

    private Scene scene;
    public static ArrayList<String> tilesets;

    public static TileMapHandler tileMapHandler;
    public static TileSetHandler tileSetHandler;

    @Override
    public void start(Stage primaryStage) throws Exception{
        //load tilesets to list, then sort
        try {
            List<String> tilesetsPaths = Files.walk(Paths.get("tilesets/"))
                    .filter(Files::isRegularFile)
                    .map(path -> path.getFileName().toString())
                    .collect(Collectors.toList());

            tilesets = new ArrayList<>(tilesetsPaths);

        }catch (Exception e) {}

        Collections.sort(tilesets, new StringComparator());

        //create the scene
        Parent root = FXMLLoader.load(getClass().getResource("mapEditor.fxml"));

        scene = new Scene(root);

        primaryStage.setTitle("Artifact Editor");
        primaryStage.setScene(scene);

        primaryStage.show();
    }



    public static void main(String[] args) {
        launch(args);
    }
}
