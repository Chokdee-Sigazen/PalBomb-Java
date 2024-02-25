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
        this.setVgap(8);
        ColumnConstraints column1 = new ColumnConstraints();
        column1.setPercentWidth(20);
        column1.setHalignment(HPos.RIGHT);
        ColumnConstraints column2 = new ColumnConstraints();
        column2.setPercentWidth(80);
        this.getColumnConstraints().addAll(column1,column2);
        Button button2 = buttonPlayer(2);
        Button button3 = buttonPlayer(3);
        Button button4 = buttonPlayer(4);
        setConstraints(button2,0,0);
        setConstraints(button3,0,1);
        setConstraints(button4,0,2);

    }

    private Button buttonPlayer (int countPlayer){
        Button button = new Button();
        if(countPlayer == 2){
            button.setText("2 Player");
            button.setBorder(new Border(new BorderStroke(Color.DARKCYAN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
            button.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        }
        if(countPlayer == 3){
            button.setText("3 Player");
            button.setBorder(new Border(new BorderStroke(Color.DARKRED, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
            button.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        }
        if(countPlayer == 4){
            button.setText("4 Player");
            button.setBorder(new Border(new BorderStroke(Color.DARKGREEN, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, new BorderWidths(2))));
            button.setBackground(new Background(new BackgroundFill(Color.WHITE,null,null)));
        }
        button.setTextFill(Color.DARKCYAN);
        button.setPrefWidth(300);
        return button;
    }
}


