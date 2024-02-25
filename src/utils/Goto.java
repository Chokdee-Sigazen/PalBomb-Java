package utils;

import boardView.SelectPlayerPane;
import boardView.StartPane;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class Goto {
    private static StartPane startPane;

    public static void setRootPane(StartPane startPane){
        Goto.startPane = startPane;
    }

    public static void clear(){
        if(startPane.getChildren().size() <= 1){
            return;
        }
        for(int i = startPane.getChildren().size()-1 ;i>=1;i--){
            startPane.getChildren().remove(startPane.getChildren().get(i));
        }
    }

    public static void startPane(){
        clear();
        startPane.getChildren().add(startGameButton());
        startPane.getChildren().add(backToStartPane());
    }

    public static Button backToStartPane(){
        Button button = new Button("Back");
        button.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
        button.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        button.setTextFill(Color.DARKCYAN);
        button.setPrefWidth(300);
        button.setOnMouseClicked(event -> startPane());
        return button;
    }



    private static Button startGameButton(){
        Button startGameButton = new Button("Start Game");
        startGameButton.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
        startGameButton.setTextFill(Color.WHITE);
        startGameButton.setFont(Font.font("Arial", 18));
        startGameButton.setPadding(new javafx.geometry.Insets(10, 20, 10, 20));
        startGameButton.setOnMouseEntered(event -> {
            startGameButton.setBackground(new Background(new BackgroundFill(Color.DARKGREEN,null,null)));
        });
        startGameButton.setOnMouseClicked(event -> {
            startGameButton.setBackground(new Background(new BackgroundFill(Color.web("025800"),null,null)));
            selectPlayerPane();
        });
        startGameButton.setOnMouseExited(event -> {
            startGameButton.setBackground(new Background(new BackgroundFill(Color.GREEN,null,null)));
        });
        return startGameButton;
    }

    private static void selectPlayerPane(){
        clear();
        startPane.getChildren().add(backToStartPane());
        startPane.getChildren().add(new SelectPlayerPane());
    }


}
