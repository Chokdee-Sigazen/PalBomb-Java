package model;

import config.Config;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import utils.AnimationUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Player {
    private int hp;
    private int bomb;
    private int speed;
    private int power;
    private boolean immortal;
    private boolean ghost;
    private final ArrayList<Image> left, right, up, down;
    private ArrayList<Bomb> bombs;
    private ImageView body;
    private int x;
    private int y;
    private final int playerNumber;
    private final Map<KeyCode, String> keyDirection = new HashMap<>();

    public Player(int playerNumber,int x ,int y,int color){
        setHp(3);
        setPower(1);
        setBomb(3);
        setSpeed(1);
        setImmortal(false);
        setGhost(false);
        setX(x);
        setY(y);
        this.playerNumber = playerNumber;
        left = right = up = down = new ArrayList<>();
        AnimationUtils.addAnimation(this.playerNumber, this);
        setBody(down.get(1));
        body.setFitHeight(Config.tileSize);
        setLayoutXY();
        if(playerNumber == 1){
            keyDirection.put(KeyCode.W, "up");
            keyDirection.put(KeyCode.S, "down");
            keyDirection.put(KeyCode.A, "left");
            keyDirection.put(KeyCode.D, "right");
            keyDirection.put(KeyCode.Q, "bomb");
        }
        else if(playerNumber == 2){
            keyDirection.put(KeyCode.Y, "up");
            keyDirection.put(KeyCode.H, "down");
            keyDirection.put(KeyCode.G, "left");
            keyDirection.put(KeyCode.J, "right");
            keyDirection.put(KeyCode.T, "bomb");
        }
        else if(playerNumber == 3){
            keyDirection.put(KeyCode.NUMPAD8, "up");
            keyDirection.put(KeyCode.NUMPAD5, "down");
            keyDirection.put(KeyCode.NUMPAD4, "left");
            keyDirection.put(KeyCode.NUMPAD6, "right");
            keyDirection.put(KeyCode.NUMPAD7, "bomb");
        }
        else if(playerNumber == 4){
            keyDirection.put(KeyCode.P, "up");
            keyDirection.put(KeyCode.SEMICOLON, "down");
            keyDirection.put(KeyCode.L, "left");
            keyDirection.put(KeyCode.QUOTE, "right");
            keyDirection.put(KeyCode.O, "bomb");
        }
    }

    public ImageView getBody() {
        return body;
    }
    private Color getColorForPlayer(int value) {
        return switch (value) {
            case 4 -> Color.LIGHTPINK;
            case 3 -> Color.LIGHTGREEN;
            case 2 -> Color.LIGHTBLUE;
            case 1 -> Color.YELLOW;
            default -> Color.BLACK;
        };
    }

    public void moveUp(){
        if(y > 0) {
            setY(y-1);
            setLayoutXY();
            System.out.println("player" + playerNumber + " move to "+y+","+x);
        }
    }

    public void moveDown(){
        if(y < 15) {
            setY(y+1);
            setLayoutXY();
            System.out.println("player" + playerNumber + " move to "+y+","+x);
        }
    }

    public void moveLeft(){
        if(x > 0) {
            setX(x-1);
            setLayoutXY();
            System.out.println("player" + playerNumber + " move to "+y+","+x);
        }
    }

    public void moveRight(){
        if(x < 15) {
            setX(x+1);
            setLayoutXY();
            System.out.println("player" + playerNumber + " move to "+y+","+x);
        }
    }
    private void setLayoutXY(){
        body.setLayoutX(75 + x * Config.tileSize);
        body.setLayoutY(y * Config.tileSize);
    }
    public void setBody(Image body) {
        this.body = new ImageView(body);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public boolean isImmortal() {
        return immortal;
    }

    public void setImmortal(boolean immortal) {
        this.immortal = immortal;
    }

    public boolean isGhost() {
        return ghost;
    }

    public void setGhost(boolean ghost) {
        this.ghost = ghost;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(Math.min(hp,3),0);
    }

    public int getBomb() {
        return bomb;
    }

    public void setBomb(int bomb) {
        this.bomb = bomb;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = Math.max(speed,1);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = Math.max(power,1);
    }

    public ArrayList<Image> getAnimation(String s) {
        return switch (s) {
            case "Left" -> left;
            case "Right" -> right;
            case "Up" -> up;
            case "Down" -> down;
            default -> null;
        };
    }

    public String getDirection(KeyCode key) {
        return keyDirection.get(key);
    }

    @Override
    public String toString() {
        return "player"+playerNumber;
    }
}
