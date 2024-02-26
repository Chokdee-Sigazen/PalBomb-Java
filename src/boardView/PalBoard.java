package boardView;

import gameControl.GameController;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class PalBoard extends Pane {

    private final int HEIGHT = 7;
    private final int TILE = 50;
    private final int WIDTH = 7;
    private final int[][] map;
    public int[][] getMap() {
        return map;
    }


    public PalBoard() {
        map = generateMap();
        System.out.println("hi");
        setPrefSize(WIDTH * TILE, HEIGHT * TILE);
        for (int y = 1; y <= HEIGHT; y++) {
            for (int x = 1; x <= WIDTH; x++) {
                Rectangle tile = new Rectangle(TILE, TILE);
                tile.setFill(getColor(map[y][x]));
                tile.setLayoutX(x * TILE);
                tile.setLayoutY(y * TILE);
                getChildren().add(tile);
            }
        }
    }


    private int[][] generateMap(){
        int[][] generatedMap = new int[HEIGHT + 2][WIDTH + 2];
        for (int y = 0; y <= HEIGHT+1; y++) {
            for (int x = 0; x <= WIDTH+1; x++) {
                // Generate random value between 0 and 2
                if (x == 0 || y == 0 || x == WIDTH + 1 || y == HEIGHT + 1) {
                    generatedMap[y][x] = 3;
                    continue;
                }
                generatedMap[y][x] = (int) (Math.random() * 3);
            }
        }
        return generatedMap;
    }

    private Color getColor(int value){
        if(value == 0 || value == 1){
            return Color.WHITE;
        }
        if(value == 2){
            return Color.GREY; //Breakable Block
        }
        if(value == 3){
            return Color.BLACK;  //UnBreakable Block
        }
        return null;
    }



}
