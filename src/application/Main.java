package application;

import boardView.StartPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    private static Stage primaryStage;

    public static void closeStage() {
        if (primaryStage != null) {
            primaryStage.close();
        }
    }
    @Override
    public void start(Stage stage) throws Exception {

        // Set primaryStage
        primaryStage = stage;

        // Load the image
        Image backgroundImage = new Image("MainPage.png");

        // Create a background image
        BackgroundImage background = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
        );

        // Apply the background to the root node
        StartPane.getStartPane().setBackground(new Background(background));

        // Create the scene
        Scene scene = new Scene(StartPane.getStartPane(), 937.5, 750);

        // Set the scene
        stage.setScene(scene);
        stage.setTitle("PalBomb");
        stage.setResizable(false);
        stage.show();
    }
}
