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

    private final int HEIGHT = 15;
    private final int TILE = 35;
    private final int WIDTH = 15;
    private final int[][] map;
    public int[][] getMap() {
        return map;
    }


    public PalBoard() {

        map = generateMap();
        System.out.println("hi");
        setPrefSize(WIDTH * TILE, HEIGHT * TILE);
        for (int y = 0; y <= HEIGHT+1; y++) {
            for (int x = 0; x <= WIDTH+1; x++) {
                Rectangle tile = new Rectangle(TILE, TILE);
                tile.setFill(getColor(map[y][x]));
                tile.setLayoutX(75 + x * TILE);
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
                if(generatedMap[y][x] == 1){
                    generatedMap[y][x] = 2;
                }

            }
        }
        for(int y = 2; y <= HEIGHT; y+=2){
            for(int x = 2; x <= WIDTH; x+=2){
                generatedMap[y][x] = 3;
            }
        }
        for(int i = -1;i<=1;i++){
            generatedMap[3][3+i] = 1;
            generatedMap[3+i][3] = 1;
        }
        for(int i = -1;i<=1;i++){
            generatedMap[HEIGHT - 2 + i][WIDTH - 2] = 1;
            generatedMap[HEIGHT - 2][WIDTH - 2 + i] = 1;
        }
        for(int i = -1;i<=1;i++){
            generatedMap[3 + i][WIDTH - 2] = 1;
            generatedMap[3][WIDTH - 2 + i] = 1;
        }
        for(int i = -1;i<=1;i++){
            generatedMap[HEIGHT - 2 + i][3] = 1;
            generatedMap[HEIGHT - 2][3 + i] = 1;
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
