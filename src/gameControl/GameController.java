package gameControl;

import boardView.PalBoard;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import model.Bomb;
import model.Player;

import java.util.ArrayList;

public class GameController {

    private final int HEIGHT = 7;
    private final int WIDTH = 7;
    private Player player;
    private PalBoard palBoard;
    private static GameController instance;
    private boolean isMoving = false;
    private Thread movementThread;

    private Thread bombThread;



    private final int TILE_SIZE = 70;

    public GameController() {
        this.player = new Player(1,1, 1); // Initial player position
        this.palBoard = new PalBoard();
    }

    public PalBoard getPalBoard() {
        return palBoard;
    }

    public void startGame(){
        palBoard.getChildren().add(GameController.getInstance().getPlayer().getBody());
        System.out.println("hi");
        palBoard.setOnKeyPressed(event -> {
            GameController.getInstance().handlePlayerMovement(event.getCode());
        });
    }

    public void handlePlayerMovement(KeyCode key) {
        if (!isMoving) {
            isMoving = true;
            movementThread = new Thread(() -> {
                int currentPlayerX = player.getX();
                int currentPlayerY = player.getY();
                int newTileX = currentPlayerX;
                int newTileY = currentPlayerY;
                if (key == KeyCode.W) {
                    newTileY--;
                    if(!isValidMove(newTileX, newTileY)) newTileY++;
                } else if (key == KeyCode.S) {
                    newTileY++;
                    if(!isValidMove(newTileX, newTileY)) newTileY--;
                } else if (key == KeyCode.A) {
                    newTileX--;
                    if(!isValidMove(newTileX, newTileY)) newTileX++;
                } else if (key == KeyCode.D) {
                    System.out.println("hi Movement");
                    newTileX++;
                    if(!isValidMove(newTileX, newTileY)) newTileX--;
                } else if (key == KeyCode.Q) {
                    placeBomb();
                }
                player.setY(newTileY);
                player.setX(newTileX);
                if (isValidMove(newTileX, newTileY)) {
                    System.out.println(player.getY());
                    System.out.println(player.getX());
                    double incrementX = ((double) (newTileX - currentPlayerX));
                    double incrementY = ((double) (newTileY - currentPlayerY));
                    System.out.println("Increment");
                    System.out.println(incrementX);
                    System.out.println(incrementY);
                    System.out.println(player.getBody().getLayoutX());
                    Platform.runLater(() -> {
                        player.getBody().setLayoutX((player.getBody().getLayoutX() + incrementX * TILE_SIZE));
                        player.getBody().setLayoutY((player.getBody().getLayoutY() + incrementY * TILE_SIZE));
                        System.out.println(player.getBody().getLayoutX());
                    });

                }
                isMoving = false;
            });
            movementThread.start();
        }
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x < WIDTH && y >= 0 && y < HEIGHT;
    }

    private boolean isValidMove(int x, int y) {
        // Replace this with your collision detection logic
        if((palBoard.getMap()[y][x] == 0 || palBoard.getMap()[y][x] == 1) && x < WIDTH && y < WIDTH && x >= 0 && y >= 0){
            return true;// True for empty space, false for blocks
        }
        return false;
    }

    private void placeBomb() {
        if (palBoard.getMap()[player.getY()][player.getX()] == 0 || palBoard.getMap()[player.getY()][player.getX()] == 1 ) {
            palBoard.getMap()[player.getY()][player.getX()] = 3; // Set map value to 3 to indicate a bomb
            Circle bomb = new Circle( TILE_SIZE / 4, Color.RED);
            bomb.setLayoutX(player.getX() * TILE_SIZE +  TILE_SIZE / 2);
            bomb.setLayoutY(player.getY() * TILE_SIZE +  TILE_SIZE / 2);
            System.out.println("pass1");
            Label timerLabel = new Label("4"); // Countdown timer label
            timerLabel.setLayoutX(bomb.getLayoutX() - timerLabel.prefWidth(0) / 2);
            timerLabel.setLayoutY(bomb.getLayoutY() - bomb.getRadius());
            System.out.println("pass1.5");
            Platform.runLater(() -> {
                palBoard.getChildren().addAll(bomb, timerLabel);
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

        palBoard.getMap()[bombY][bombX] = 0; // Reset map value back to 0

        // Check surrounding tiles within range 1 in the four directions
        for (int dx = -1; dx <= 1; dx++) {
            if (Math.abs(dx) == 1) { // Check only horizontal movement
                int newX = bombX + dx;
                int newY = bombY;

                if (isValidCoordinate(newX, newY) && palBoard.getMap()[newY][newX] == 2) {
                    // Destroy breakable tile (map[y][x] == 2)
                    palBoard.getMap()[newY][newX] = 0;
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(findTile(newX, newY));
                    });
                }
            }
        }

        // Check surrounding tiles within range 1 vertically
        for (int dy = -1; dy <= 1; dy++) {
            if (Math.abs(dy) == 1) { // Check only vertical movement
                int newX = bombX;
                int newY = bombY + dy;

                if (isValidCoordinate(newX, newY) && palBoard.getMap()[newY][newX] == 2) {
                    // Destroy breakable tile (map[y][x] == 2)
                    palBoard.getMap()[newY][newX] = 0;
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(findTile(newX, newY));
                    });
                }
            }
        }

        // Remove the bomb visualization after detonation
        Platform.runLater(() -> {
            palBoard.getChildren().remove(bomb); // Remove the bomb Circle object
            palBoard.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("0")); // Remove the timer label (assuming initial text is "4")
        });
    }

    private Rectangle findTile(int x, int y) {
        for (Node tile : palBoard.getChildren()) {
            if (tile.getLayoutX() == x * TILE_SIZE && tile.getLayoutY() == y * TILE_SIZE) {
                return (Rectangle) tile;
            }
        }
        return null;
    }

    private Color getColorForCountdown(int count) {
        return switch (count) {
            case 3 -> Color.DARKRED;
            case 2 -> Color.RED;
            case 1 -> Color.ORANGE;
            default -> Color.YELLOW;
        };
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public Player getPlayer() {
        return player;
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public static GameController getInstance() {
        if(instance == null)
            instance = new GameController();
        return instance;
    }
}
