package utils;

import application.Main;
import boardView.PalBoard;
import boardView.SelectPlayerPane;
import boardView.StartPane;
import gameControl.GameController;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.BlurType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import model.Player;
import javafx.scene.effect.DropShadow;
import java.awt.*;

public class Goto {

    private static StartPane startPane;
    public static void setRootPane(StartPane startPane){
        Goto.startPane = startPane;
    }

    public static void clear(){
        if(startPane.getChildren().size() <= 0){
            return;
        }
        for(int i = startPane.getChildren().size()-1 ;i>=0;i--){
            startPane.getChildren().remove(startPane.getChildren().get(i));
        }
    }

    public static void startPane(){
        clear();
        startPane.getChildren().add(startGameButton());
        startPane.getChildren().add(endGameButton());
    }

    public static Button backToStartPane(){
        Button button = new Button("Back");
        button.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
        button.setTextFill(Color.BLACK);
        button.setFont(Font.font("Verdana", 32));
        button.setPadding(new javafx.geometry.Insets(10, 50, 10, 50));
        button.setEffect(new DropShadow(BlurType.THREE_PASS_BOX,Color.BLACK,4,0,0,4));
        button.setOnMouseEntered(event -> {
            button.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(15),null)));
            button.setTextFill(Color.BLACK);
        });
        button.setOnMouseClicked(event ->{
            button.setBackground(new Background(new BackgroundFill(Color.web("025800"), new CornerRadii(15),null)));
            BackgroundImage backgroundImage = new BackgroundImage(
                    new Image("MainPage.png"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
            );
            StartPane.getStartPane().setBackground(new Background(backgroundImage));
            startPane();
        });
        button.setOnMouseExited(event -> {
            button.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
            button.setTextFill(Color.BLACK);
        });
        return button;
    }

    private static Button startGameButton(){
        Button startGameButton = new Button("Start");
        startGameButton.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
        startGameButton.setTextFill(Color.BLACK);
        startGameButton.setFont(Font.font("Verdana", 32));
        startGameButton.setPadding(new javafx.geometry.Insets(10, 50, 10, 50));
        startGameButton.setEffect(new DropShadow(BlurType.THREE_PASS_BOX,Color.BLACK,4,0,0,4));
        VBox.setMargin(startGameButton,new javafx.geometry.Insets(350,0,0,0));

        startGameButton.setOnMouseEntered(event -> {
            startGameButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(15),null)));
            startGameButton.setTextFill(Color.BLACK);
        });
        startGameButton.setOnMouseClicked(event -> {
            startGameButton.setBackground(new Background(new BackgroundFill(Color.web("025800"), new CornerRadii(15),null)));
            BackgroundImage backgroundImage = new BackgroundImage(
                    new Image("SelectPlayer.png"),
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundRepeat.NO_REPEAT,
                    BackgroundPosition.DEFAULT,
                    new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
            );
            StartPane.getStartPane().setBackground(new Background(backgroundImage));
            selectPlayerPane();
        });
        startGameButton.setOnMouseExited(event -> {
            startGameButton.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
            startGameButton.setTextFill(Color.BLACK);
        });
        return startGameButton;
    }

    private static Button endGameButton(){
        Button endGameButton = new Button("Exit");
        endGameButton.setOnMouseClicked(event -> Main.closeStage());
        endGameButton.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
        endGameButton.setTextFill(Color.BLACK);
        endGameButton.setFont(Font.font("Verdana", 32));
        endGameButton.setPadding(new javafx.geometry.Insets(10, 60, 10, 60));
        endGameButton.setEffect(new DropShadow(BlurType.THREE_PASS_BOX,Color.BLACK,4,0,0,4));

        endGameButton.setOnMouseEntered(event -> {
            endGameButton.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(15),null)));
            endGameButton.setTextFill(Color.BLACK);
        });
        endGameButton.setOnMouseExited(event -> {
            endGameButton.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
            endGameButton.setTextFill(Color.BLACK);
        });
        return endGameButton;
    }

    private static void selectPlayerPane(){
        clear();
        startPane.getChildren().add(new SelectPlayerPane());
        startPane.getChildren().add(backToStartPane());
    }

    public static void palBoard(int number){
        clear();
        PalBoard palBoard = new PalBoard();
        GameController.getInstance().setPalBoard(palBoard);
        GameController.getInstance().startGame(number);
        startPane.getChildren().add(palBoard);
        palBoard.setFocusTraversable(true);
        startPane.getChildren().add(backToStartPane());
    }
}
