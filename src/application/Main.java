package application;

import boardView.StartPane;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(StartPane.getStartPane(),750,750);
        stage.setScene(scene);
        stage.setTitle("PalBomb");
        stage.setResizable(false);
        stage.show();
    }
}
