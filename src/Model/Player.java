package Model;

import javafx.scene.image.Image;

import java.util.ArrayList;

public class Player {
    private boolean isImmortal;
    private boolean isGhost;
    private int hp;
    private int power;
    private int bomb_amount;
    private int speed;
    private final ArrayList<Image> animation;

    public Player(int playerNumber) {
        setGhost(false);
        setImmortal(false);
        setHp(3);
        setPower(1);
        setSpeed(1);
        setBomb_amount(1);
        animation = new ArrayList<Image>();
        AnimationUtils.addAnimation(playerNumber, this);
    }

    public void placeBomb(){
        if(bomb_amount == 0) return;
        setBomb_amount(bomb_amount-1);
        // Not Done
    }

    public boolean isImmortal() {
        return isImmortal;
    }

    public void setImmortal(boolean immortal) {
        isImmortal = immortal;
    }

    public boolean isGhost() {
        return isGhost;
    }

    public void setGhost(boolean ghost) {
        isGhost = ghost;
    }

    public int getHp() {
        return hp;
    }

    public void setHp(int hp) {
        this.hp = Math.max(Math.min(3,hp), 0);
    }

    public int getPower() {
        return power;
    }

    public void setPower(int power) {
        this.power = Math.max(power, 1);
    }

    public int getBomb_amount() {
        return bomb_amount;
    }

    public void setBomb_amount(int bomb_amount) {
        this.bomb_amount = Math.max(bomb_amount, 1);
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = Math.max(speed, 1);
    }

    public ArrayList<Image> getAnimation() {
        return animation;
    }
}
