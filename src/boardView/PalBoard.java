package boardView;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class PalBoard extends Application {
    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        Scene scene = new Scene(Root,1024,1024);
        stage.setScene(scene);
        stage.setTitle("PalBomb");
        stage.setResizable(false);
        stage.show();
    }
}
