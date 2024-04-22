package boardView;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import utils.Goto;

public class SelectPlayerPane extends GridPane {
    public SelectPlayerPane(){
        this.setPadding(new Insets(150,12,12,12));
        this.setVgap(40);
        this.setHgap(20); // Add horizontal gap between buttons
        this.setAlignment(Pos.CENTER); // Center the GridPane

        // Create RowConstraints and set their height
        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(50); // Set height for row 1
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(50); // Set height for row 2
        RowConstraints row3 = new RowConstraints();
        row3.setPrefHeight(50); // Set height for row 3
        this.getRowConstraints().addAll(row1, row2, row3);

        Button button2 = buttonPlayer(2);
        Button button3 = buttonPlayer(3);
        Button button4 = buttonPlayer(4);

        setConstraints(button2, 0, 0);
        setConstraints(button3, 0, 1);
        setConstraints(button4, 0, 2);

        getChildren().addAll(button2, button3, button4);
    }

    private Button buttonPlayer (int countPlayer){
        Button button = new Button();
        if(countPlayer == 2){
            button.setText("2 Player");
            button.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
            button.setTextFill(Color.BLACK);
            button.setFont(Font.font("Verdana", 32));
            button.setPadding(new Insets(10, 50, 10, 50));
            button.setEffect(new DropShadow(BlurType.GAUSSIAN,Color.BLACK,4,0,0,4));
            button.setMaxWidth(300);

            button.setOnMouseEntered(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(15),null)));
            });
            button.setOnMouseClicked(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.web("00D5C8"),null,null)));
                Goto.palBoard(2);
                BackgroundImage backgroundImage = new BackgroundImage(
                        new Image("GamePlay.png"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
                );
                StartPane.getStartPane().setBackground(new Background(backgroundImage));
            });
            button.setOnMouseExited(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
            });
        }
        if(countPlayer == 3){
            button.setText("3 Player");
            button.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
            button.setTextFill(Color.BLACK);
            button.setFont(Font.font("Verdana", 32));
            button.setPadding(new Insets(10, 50, 10, 50));
            button.setEffect(new DropShadow(BlurType.GAUSSIAN,Color.BLACK,4,0,0,4));
            button.setMaxWidth(300);

            button.setOnMouseEntered(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(15),null)));
            });
            button.setOnMouseClicked(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.web("D50054"),null,null)));
                Goto.palBoard(3);
                BackgroundImage backgroundImage = new BackgroundImage(
                        new Image("GamePlay.png"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
                );
                StartPane.getStartPane().setBackground(new Background(backgroundImage));
            });
            button.setOnMouseExited(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
            });
        }
        if(countPlayer == 4){
            button.setText("4 Player");
            button.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
            button.setTextFill(Color.BLACK);
            button.setFont(Font.font("Verdana", 32));
            button.setPadding(new Insets(10, 50, 10, 50));
            button.setEffect(new DropShadow(BlurType.GAUSSIAN,Color.BLACK,4,0,0,4));
            button.setMaxWidth(300);

            button.setOnMouseEntered(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.LIGHTGREY, new CornerRadii(15),null)));
            });
            button.setOnMouseClicked(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.web("00D5C8"),null,null)));
                Goto.palBoard(4);
                BackgroundImage backgroundImage = new BackgroundImage(
                        new Image("GamePlay.png"),
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.DEFAULT,
                        new BackgroundSize(BackgroundSize.AUTO, BackgroundSize.AUTO, false, false, true, false)
                );
                StartPane.getStartPane().setBackground(new Background(backgroundImage));
            });
            button.setOnMouseExited(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.WHITE, new CornerRadii(15),null)));
            });
        }
        button.setPrefHeight(1000);
        button.setPrefWidth(3000);
        return button;
    }
}


