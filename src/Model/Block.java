package Model;

import javafx.scene.image.Image;

public class Block {
    private Image image;

    public Block(String imagePath) {
        image = AnimationUtils.getImageByPath(imagePath);
    }

    public Image getImage() {
        return image;
    }


}
