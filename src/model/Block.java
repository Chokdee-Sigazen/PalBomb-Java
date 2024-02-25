package model;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Block {
    private ImageView imageView;

    public Block(String imagePath) {
        imageView = new ImageView(AnimationUtils.getImageByPath(imagePath));
    }

    public ImageView getImageView() {
        return imageView;
    }

    public abstract void handleHit();
}
