package model;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import utils.AnimationUtils;

public class Bomb {
    private ImageView bombVisual;
    private int remainingTime;
    private int x;
    private int y;

    public Bomb(int x, int y,int TILE_SIZE) {
        this.x = x;
        this.y = y;
        this.remainingTime = 4; // Assuming initial time is 4 seconds
        bombVisual = new ImageView(AnimationUtils.getImageByPath("res/Bomb.png"));
        bombVisual.setLayoutX(75 + x * TILE_SIZE);
        bombVisual.setLayoutY(y * TILE_SIZE);
        bombVisual.setFitHeight(TILE_SIZE);
        bombVisual.setFitWidth(TILE_SIZE);
        bombVisual.setPreserveRatio(true);
    }

    public ImageView getBombVisual() {
        return bombVisual;
    }
}
