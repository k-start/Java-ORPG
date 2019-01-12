package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ChoiceBox;
import javafx.scene.image.Image;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.stream.Collectors;

public class Controller implements Initializable {
    @FXML
    public Canvas mainCanvas;
    @FXML
    AnchorPane anchorPane;
    public GraphicsContext gc;

    public Image tile;
    public TileMap tileMap;

    @FXML
    public Canvas tilemapCanvas;
    @FXML
    public AnchorPane tilesetPane;
    public GraphicsContext gcTM;

    @FXML public ChoiceBox tileset;

    public String currentTileset = "Tileset1.png";
    Image currentTilesetImg = new Image("File:tilesets/" + currentTileset);

    private int selectedX = 0, selectedY = 0;
    private int i = 0;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        gc = mainCanvas.getGraphicsContext2D();
        gcTM = tilemapCanvas.getGraphicsContext2D();

        anchorPane.widthProperty().addListener((ov, oldValue, newValue) -> {
            mainCanvas.setWidth(newValue.doubleValue());
        });

        anchorPane.heightProperty().addListener((ov, oldValue, newValue) -> {
            mainCanvas.setHeight(newValue.doubleValue());
        });

        new AnimationTimer() {

            public void handle(long currentNanoTime) {
                render(gc);
            }
        }.start();

        tileMap = new TileMap(20, 20);

        tilemapCanvas.setHeight(currentTilesetImg.getHeight());
        tilemapCanvas.setWidth(currentTilesetImg.getWidth());
        tilesetPane.setPrefHeight(currentTilesetImg.getHeight());
        tilesetPane.setPrefWidth(currentTilesetImg.getWidth());

        drawSelection(gcTM, selectedX, selectedY);

        try {
            Main.tilesets = Files.walk(Paths.get("tilesets/"))
                    .filter(Files::isRegularFile)
                    .collect(Collectors.toList());
        }catch (Exception e) {}

        ArrayList<String> test = new ArrayList<>();

        for(Path p: Main.tilesets){
            test.add(p.toString().split("\\\\")[1]);
        }

        tileset.getItems().addAll(test);
        tileset.setValue("Tileset1.png");


    }

    @FXML
    private void tilesetChanged(){
        i++;
        System.out.println("asd");
        if(i > 1) {
            currentTileset = tileset.getSelectionModel().getSelectedItem().toString();
            currentTilesetImg = new Image("File:tilesets/" + currentTileset);

            tilemapCanvas.setHeight(currentTilesetImg.getHeight());
            tilemapCanvas.setWidth(currentTilesetImg.getWidth());
            tilesetPane.setPrefHeight(currentTilesetImg.getHeight());
            tilesetPane.setPrefWidth(currentTilesetImg.getWidth());

            drawSelection(gcTM, selectedX, selectedY);
        }
    }


    @FXML
    private void canvasClicked(MouseEvent e){
        if(e.getButton() == MouseButton.PRIMARY){
        //if(e.isPrimaryButtonDown()) {
            tileMap.addTile(new Tile(currentTileset, selectedX, selectedY), (int) e.getX()/32 - tileMap.screenX/32, (int) e.getY()/32 - tileMap.screenY/32);
        }else if(e.getButton() == MouseButton.SECONDARY){
            tileMap.addTile(null, (int) e.getX()/32 - tileMap.screenX/32, (int) e.getY()/32 - tileMap.screenY/32);
        }
    }

    @FXML
    private void tilemapClicked(MouseEvent e){
        selectedX = (int)e.getX()/32;
        selectedY = (int)e.getY()/32;
        drawSelection(gcTM, selectedX, selectedY);
    }

    public void render(GraphicsContext gc){
        gc.clearRect(0,0, mainCanvas.getWidth(), mainCanvas.getHeight());
        gc.setFill(Color.BLACK);

        gc.fillRect(0,0, mainCanvas.getWidth(), mainCanvas.getHeight());

        int x = (int)mainCanvas.getWidth()/2 - tileMap.width*16;
        int y = (int)mainCanvas.getHeight()/2 - tileMap.height*16;

        gc.setStroke(Color.RED);
        gc.strokeLine(((int)x/32)*32, ((int)y/32)*32, ((int)x/32)*32+tileMap.width*32, ((int)y/32)*32);
        gc.strokeLine(((int)x/32)*32, ((int)y/32)*32, ((int)x/32)*32, ((int)y/32)*32+tileMap.height*32);

        gc.strokeLine(((int)x/32)*32, ((int)y/32)*32+tileMap.height*32, ((int)x/32)*32+tileMap.width*32, ((int)y/32)*32+tileMap.height*32);
        gc.strokeLine(((int)x/32)*32+tileMap.width*32, ((int)y/32)*32, ((int)x/32)*32+tileMap.width*32, ((int)y/32)*32+tileMap.height*32);

        tileMap.render(gc, x, y);
    }

    public void drawSelection(GraphicsContext gc, int x, int y){
        gc.clearRect(0, 0, tilemapCanvas.getWidth(), tilemapCanvas.getHeight());
        gc.drawImage(currentTilesetImg, 0,0 );
        gc.setStroke(Color.RED);

        gc.strokeRect(selectedX*32, selectedY*32, 32, 32);
    }
}
