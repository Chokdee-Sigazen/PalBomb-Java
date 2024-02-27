package model;

import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class Bomb {
    private Circle bombVisual;
    private Label timerLabel;
    private int remainingTime;
    private int x;
    private int y;

    public Bomb(int x, int y,int TILE_SIZE) {
        this.x = x;
        this.y = y;
        this.remainingTime = 4; // Assuming initial time is 4 seconds
        bombVisual = new Circle(TILE_SIZE / 4, Color.RED);
        bombVisual.setLayoutX(75 + x * TILE_SIZE + TILE_SIZE / 2);
        bombVisual.setLayoutY(y * TILE_SIZE + TILE_SIZE / 2);
        timerLabel = new Label(String.valueOf(remainingTime));
        timerLabel.setLayoutX(bombVisual.getLayoutX() - timerLabel.prefWidth(0) / 2);
        timerLabel.setLayoutY(bombVisual.getLayoutY() - bombVisual.getRadius());
    }

    public Circle getBombVisual() {
        return bombVisual;
    }

    public Label getTimerLabel() {
        return timerLabel;
    }

    public int getRemainingTime() {
        return remainingTime;
    }

    public void decrementTime() {
        remainingTime--;
        timerLabel.setText(String.valueOf(remainingTime));
    }

}
