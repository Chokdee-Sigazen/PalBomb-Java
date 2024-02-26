package model;

import gameControl.GameController;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;

public class Player {
    private int hp;
    private int bomb;
    private int speed;
    private int power;
    private boolean immortal;
    private boolean ghost;
    private final ArrayList<Image> animation;

    private Rectangle body;
    private int x;
    private int y;

    public Player(int playerNumber,int x ,int y){
        setHp(3);
        setPower(1);
        setBomb(1);
        setSpeed(1);
        setImmortal(false);
        setGhost(false);
        animation = new ArrayList<>();
        //AnimationUtils.addAnimation(playerNumber, animation);
        this.body = new Rectangle(70, 70, Color.YELLOW);
        this.x = x;
        this.y = y;
        body.setLayoutX(x * 70);
        body.setLayoutY(y * 70);
    }

    public Rectangle getBody() {
        return body;
    }

    public void setBody(Rectangle body) {
        this.body = body;
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
        this.bomb = Math.max(bomb,1);
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

    public ArrayList<Image> getAnimation() {
        return animation;
    }
}
