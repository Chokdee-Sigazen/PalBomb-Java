package boardView;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import utils.Goto;

public class SelectPlayerPane extends GridPane {
    public SelectPlayerPane(){
        this.setPadding(new Insets(12));
        this.setVgap(10);
        // Create RowConstraints and set their height
        RowConstraints row1 = new RowConstraints();
        row1.setPrefHeight(50); // Set height for row 1
        RowConstraints row2 = new RowConstraints();
        row2.setPrefHeight(50); // Set height for row 2
        RowConstraints row3 = new RowConstraints();
        row3.setPrefHeight(50); // Set height for row 3
        RowConstraints row4 = new RowConstraints();
        row4.setPrefHeight(50); // Set height for row 4
        this.getRowConstraints().addAll(row1, row2, row3, row4);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        column1.setHalignment(HPos.RIGHT);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(80);
        this.getColumnConstraints().addAll(column1,column2);
        Button button2 = buttonPlayer(2);
        Button button3 = buttonPlayer(3);
        Button button4 = buttonPlayer(4);
        setConstraints(button2,1,0);
        setColumnSpan(button2,2);
        setConstraints(button3,1,1);
        setConstraints(button4,1,2);
        getChildren().add(button2);
        getChildren().add(button3);
        getChildren().add(button4);

    }

    private Button buttonPlayer (int countPlayer){
        Button button = new Button();
        if(countPlayer == 2){
            button.setText("2 Player");
            button.setTextFill(Color.LIGHTCYAN);
            button.setBorder(new Border(new BorderStroke(Color.CYAN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
            button.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,null,null)));
            button.setOnMouseEntered(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.LIGHTCYAN,null,null)));
                button.setTextFill(Color.DARKCYAN);
            });
            button.setOnMouseClicked(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.web("00D5C8"),null,null)));
                Goto.palBoard();
            });
            button.setOnMouseExited(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.DARKCYAN,null,null)));
                button.setTextFill(Color.LIGHTCYAN);
            });
        }
        if(countPlayer == 3){
            button.setText("3 Player");
            button.setTextFill(Color.LIGHTPINK);
            button.setBorder(new Border(new BorderStroke(Color.RED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
            button.setBackground(new Background(new BackgroundFill(Color.DARKRED,null,null)));
            button.setOnMouseEntered(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.PINK,null,null)));
                button.setTextFill(Color.DARKRED);
            });
            button.setOnMouseClicked(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.web("D50054"),null,null)));
            });
            button.setOnMouseExited(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.DARKRED,null,null)));
                button.setTextFill(Color.PINK);
            });
        }
        if(countPlayer == 4){
            button.setText("4 Player");
            button.setTextFill(Color.LIGHTGREEN);
            button.setBorder(new Border(new BorderStroke(Color.GREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
            button.setBackground(new Background(new BackgroundFill(Color.DARKGREEN,null,null)));
            button.setOnMouseEntered(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.LIGHTGREEN,null,null)));
                button.setTextFill(Color.DARKGREEN);
            });
            button.setOnMouseClicked(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.web("00D5C8"),null,null)));
            });
            button.setOnMouseExited(event -> {
                button.setBackground(new Background(new BackgroundFill(Color.DARKGREEN,null,null)));
                button.setTextFill(Color.LIGHTGREEN);
            });
        }
        button.setPrefHeight(1000);
        button.setPrefWidth(3000);
        return button;
    }
}


