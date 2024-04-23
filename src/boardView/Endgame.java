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
import model.Player;
import utils.Goto;

public class Endgame extends VBox {
    private static Endgame instance;

    public Endgame(Player player) {
        instance = this;
        instance.setAlignment(Pos.TOP_CENTER);
        instance.setSpacing(16);
        instance.setPadding(new Insets(500,0,0,0));

        //Show Win Player
        HBox hbox = new HBox();
        hbox.setSpacing(30);
        hbox.setAlignment(Pos.CENTER);

        Text winPlayer = new Text(player.toString());
        winPlayer.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        winPlayer.setFill(Color.DARKSEAGREEN);

        Text win = new Text("Win");
        win.setFont(Font.font("Arial", FontWeight.BOLD, 40));
        win.setFill(Color.GREEN);

        hbox.getChildren().addAll(winPlayer, win);
        instance.getChildren().add(hbox);
    }
}
