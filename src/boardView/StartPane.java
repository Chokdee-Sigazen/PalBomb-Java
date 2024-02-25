package boardView;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import utils.Goto;

public class StartPane extends VBox {
    private static StartPane instance;

    private StartPane() {
        instance = this;
        instance.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        instance.setAlignment(Pos.TOP_CENTER);
        instance.setSpacing(16);
        instance.setPadding(new Insets(32,0,32,0));
        Text text = new Text("Pal Bomb");
        text.setFill(Color.DARKCYAN);
        text.setFont(Font.font("Verdana",FontWeight.BOLD,32));
        instance.getChildren().add(text);
        Goto.setRootPane(this);
        Goto.startPane();
    }



    public static StartPane getStartPane() {
        if (instance == null)
            instance = new StartPane();
        return instance;
    }
}
