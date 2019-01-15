package sample;

import com.jfoenix.controls.JFXComboBox;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    @FXML public Canvas mainCanvas;
    @FXML public Canvas tilesetCanvas;
    @FXML public JFXComboBox tilesetCombo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Main.tileMapHandler = new TileMapHandler(mainCanvas, 20, 20);
        Main.tileSetHandler = new TileSetHandler(tilesetCanvas, "Tileset1.png");

        tilesetCombo.getItems().addAll(Main.tilesets);
        tilesetCombo.setValue("Tileset1.png");
    }

    @FXML
    private void canvasClicked(MouseEvent e){
        if(e.getButton() == MouseButton.PRIMARY) {
            Main.tileMapHandler.clicked(Main.tileSetHandler.getTileset(), Main.tileSetHandler.getSelectedPoint(), (int) e.getX()/32, (int) e.getY()/32);
        } else if(e.getButton() == MouseButton.SECONDARY){
            Main.tileMapHandler.clicked((int) e.getX()/32, (int) e.getY()/32);
        }
    }

    @FXML
    private void tilesetClicked(MouseEvent e){
        Main.tileSetHandler.clicked((int)e.getX()/32, (int)e.getY()/32);
    }

    @FXML
    private void tilesetChanged(){
        Main.tileSetHandler.setTileset(tilesetCombo.getSelectionModel().getSelectedItem().toString());
    }
}
