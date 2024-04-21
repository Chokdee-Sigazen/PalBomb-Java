package utils;

import javafx.scene.image.Image;
import model.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class AnimationUtils {
    public static void addAnimation(int playerNumber, Player player){
        player.getAnimation("Left").add(getImageByPath("res/P" + playerNumber + "_L_1.png"));
        player.getAnimation("Left").add(getImageByPath("res/P" + playerNumber + "_L_2.png"));
        player.getAnimation("Right").add(getImageByPath("res/P" + playerNumber + "_R_1.png"));
        player.getAnimation("Right").add(getImageByPath("res/P" + playerNumber + "_R_2.png"));
        player.getAnimation("Up").add(getImageByPath("res/P" + playerNumber + "_U_1.png"));
        player.getAnimation("Up").add(getImageByPath("res/P" + playerNumber + "_U_2.png"));
        player.getAnimation("Down").add(getImageByPath("res/P" + playerNumber + "_D_1.png"));
        player.getAnimation("Down").add(getImageByPath("res/P" + playerNumber + "_D_2.png"));
        System.out.println("res/P" + playerNumber + "_D_2.png");
    }

    public static Image getImageByPath(String imagePath) {
        try {
            return new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e1) {
            try {
                return new Image(new FileInputStream("res/error.png"));
            } catch (FileNotFoundException e2) {
                return null;
            }
        }
    }
}
