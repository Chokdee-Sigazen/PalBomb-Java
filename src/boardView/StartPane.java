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
        instance.setAlignment(Pos.TOP_CENTER);
        instance.setSpacing(16);
        Goto.setRootPane(this);
        Goto.startPane();
    }

    public static StartPane getStartPane() {
        if (instance == null)
            instance = new StartPane();
        return instance;
    }
}
