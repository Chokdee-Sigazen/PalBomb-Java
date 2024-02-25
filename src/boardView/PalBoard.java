package boardView;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;


public class PalBoard extends Pane {
    private final int WIDTH = 5;
    private final int HEIGHT = 5;
    private final int TILE_SIZE = 70;
    private final int[][] map;

    private int playerX = 0;
    private int playerY = 0;
    private boolean isMoving = false;

    private Thread movementThread;
    private Thread bombThread;

    public PalBoard() {
        map = generateMap();

        setPrefSize(WIDTH * TILE_SIZE, HEIGHT * TILE_SIZE);

        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                Rectangle tile = new Rectangle(TILE_SIZE, TILE_SIZE);
                tile.setFill(getColor(map[y][x]));
                tile.setLayoutX(x * TILE_SIZE);
                tile.setLayoutY(y * TILE_SIZE);
                getChildren().add(tile);
            }
        }

        Rectangle player = new Rectangle(TILE_SIZE, TILE_SIZE, Color.YELLOW);
        player.setLayoutX(playerX * TILE_SIZE);
        player.setLayoutY(playerY * TILE_SIZE);
        getChildren().add(player);
        System.out.println("hi");
        setOnKeyPressed(event -> {
            System.out.println("hi");
            if (!isMoving) {
                isMoving = true;
                movementThread = new Thread(() -> {
                    int newX = playerX;
                    int newY = playerY;

                    if (event.getCode() == KeyCode.W) {
                        newY--;
                        if(!isValidMove(newX, newY)) newY++;
                    } else if (event.getCode() == KeyCode.S) {
                        newY++;
                        if(!isValidMove(newX, newY)) newY--;
                    } else if (event.getCode() == KeyCode.A) {
                        newX--;
                        if(!isValidMove(newX, newY)) newX++;
                    } else if (event.getCode() == KeyCode.D) {
                        newX++;
                        if(!isValidMove(newX, newY)) newX--;
                    } else if (event.getCode() == KeyCode.Q) {
                        placeBomb();
                    }


                    if (isValidMove(newX, newY)) {
                        for (int i = 0; i < TILE_SIZE; i++) {
                            int finalNewX = newX;
                            int finalNewY = newY;
                            int finalI = i;
                            Platform.runLater(() -> {
                                // Update player position on UI using calculated values
                                player.setLayoutX(player.getLayoutX() + (finalNewX - playerX) * (finalI + 1.0) / TILE_SIZE * 2);
                                player.setLayoutY(player.getLayoutY() + (finalNewY - playerY) * (finalI + 1.0) / TILE_SIZE * 2);
                            });
                            try {
                                Thread.sleep(10); // Adjust speed as needed
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                        playerX = newX;
                        playerY = newY;
                    }
                    isMoving = false;
                });
                movementThread.start();
            }
        });
    }

    private void placeBomb() {
        if (map[playerY][playerX] == 0 || map[playerY][playerX] == 1 ) {
            map[playerY][playerX] = 3; // Set map value to 3 to indicate a bomb
            Circle bomb = new Circle( TILE_SIZE / 4, Color.RED);
            bomb.setLayoutX(playerX * TILE_SIZE +  TILE_SIZE / 2);
            bomb.setLayoutY(playerY * TILE_SIZE +  TILE_SIZE / 2);
            System.out.println("pass1");
            Label timerLabel = new Label("4"); // Countdown timer label
            timerLabel.setLayoutX(bomb.getLayoutX() - timerLabel.prefWidth(0) / 2);
            timerLabel.setLayoutY(bomb.getLayoutY() - bomb.getRadius());
            System.out.println("pass1.5");
            Platform.runLater(() -> {
                getChildren().addAll(bomb, timerLabel);
            });
            System.out.println("pass2");
            bombThread = new Thread(() -> {
                for (int i = 3; i >= 0; i--) { // Countdown from 4 to 0
                    final int count = i;
                    Platform.runLater(() -> {
                        timerLabel.setText(String.valueOf(count));
                        bomb.setFill(getColorForCountdown(count)); // Change color based on countdown
                    });
                    try {
                        Thread.sleep(1000); // Delay between color changes (1 second)
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                detonateBomb(bomb);
            });
            bombThread.start();
            System.out.println("pass3");
        }
    }
    private void detonateBomb(Circle bomb) {
        // Get bomb coordinates from the Circle object
        int bombX = (int) Math.floor(bomb.getLayoutX() / TILE_SIZE);
        int bombY = (int) Math.floor(bomb.getLayoutY() / TILE_SIZE);

        map[bombY][bombX] = 0; // Reset map value back to 0

        // Check surrounding tiles within range 1 in the four directions
        for (int dx = -1; dx <= 1; dx++) {
            if (Math.abs(dx) == 1) { // Check only horizontal movement
                int newX = bombX + dx;
                int newY = bombY;

                if (isValidCoordinate(newX, newY) && map[newY][newX] == 2) {
                    // Destroy breakable tile (map[y][x] == 2)
                    map[newY][newX] = 0;
                    Platform.runLater(() -> {
                        getChildren().remove(findTile(newX, newY));
                    });
                }
            }
        }

        // Check surrounding tiles within range 1 vertically
        for (int dy = -1; dy <= 1; dy++) {
            if (Math.abs(dy) == 1) { // Check only vertical movement
                int newX = bombX;
                int newY = bombY + dy;

                if (isValidCoordinate(newX, newY) && map[newY][newX] == 2) {
                    // Destroy breakable tile (map[y][x] == 2)
                    map[newY][newX] = 0;
                    Platform.runLater(() -> {
                        getChildren().remove(findTile(newX, newY));
                    });
                }
            }
        }

        // Remove the bomb visualization after detonation
        Platform.runLater(() -> {
            getChildren().remove(bomb); // Remove the bomb Circle object
            getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("0")); // Remove the timer label (assuming initial text is "4")
        });
    }


    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    private Rectangle findTile(int x, int y) {
        for (Node tile : getChildren()) {
            if (tile.getLayoutX() == x * TILE_SIZE && tile.getLayoutY() == y * TILE_SIZE) {
                return (Rectangle) tile;
            }
        }
        return null;
    }

    private Color getColorForCountdown(int count) {
        return switch (count) {
            case 3 -> Color.RED;
            case 2 -> Color.ORANGE;
            case 1 -> Color.YELLOW;
            default -> Color.WHITE;
        };
    }

    private int[][] generateMap(){
        int[][] generatedMap = new int[HEIGHT][WIDTH];
        for (int y = 0; y < HEIGHT; y++) {
            for (int x = 0; x < WIDTH; x++) {
                // Generate random value between 0 and 2
                generatedMap[y][x] = (int) (Math.random() * 4);
            }
        }
        return generatedMap;
    }

    private Color getColor(int value){
        if(value == 0 || value == 1){
            return Color.WHITE;
        }
        if(value == 2){
            return Color.GREY; //Breakable Block
        }
        if(value == 3){
            return Color.BLACK;  //UnBreakable Block
        }
        return null;
    }


    private boolean isValidMove(int x, int y) {
        // Replace this with your collision detection logic
        if((map[y][x] == 0 || map[y][x] == 1) && x < WIDTH && y < WIDTH && x >= 0 && y >= 0){
            return true;// True for empty space, false for blocks
        }
        return false;
    }
}
