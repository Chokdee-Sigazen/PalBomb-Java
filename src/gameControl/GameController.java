package gameControl;

import boardView.PalBoard;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import model.Bomb;
import model.Player;


public class GameController {

    private final int HEIGHT = 7;
    private final int WIDTH = 7;
    private Player player1;
    private Player player2;
    private PalBoard palBoard;
    private static GameController instance;
    private boolean isMoving = false;
    private Thread movementThread;
    private Thread bombThread;



    private final int TILE_SIZE = 50;

    public GameController() {
        this.player1 = new Player(1,1, 1,1); // Initial player1 position
        this.player2 = new Player(2,HEIGHT,WIDTH,2);
        this.palBoard = new PalBoard();
    }

    public PalBoard getPalBoard() {
        return palBoard;
    }

    public void startGame(){
        palBoard.getChildren().add(GameController.getInstance().getplayer1().getBody());
        palBoard.getChildren().add(GameController.getInstance().getPlayer2().getBody());
        System.out.println("hi");
        palBoard.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.W || key == KeyCode.A || key == KeyCode.S || key == KeyCode.D || key == KeyCode.Q) {
                GameController.getInstance().handleplayer1Movement(key);
            } else if (key == KeyCode.U || key == KeyCode.K || key == KeyCode.J || key == KeyCode.H || key == KeyCode.Y) {
                GameController.getInstance().handlePlayer2Movement(key);
            }
        });

    }

    public void handlePlayer2Movement(KeyCode key) {
        if (!isMoving) {
            isMoving = true;
            movementThread = new Thread(() -> {
                int currentplayer2X = player2.getX();
                int currentplayer2Y = player2.getY();
                int newTileX = currentplayer2X;
                int newTileY = currentplayer2Y;
                if (key == KeyCode.U) {
                    newTileY--;
                    if(!isValidMove(newTileX, newTileY)) newTileY++;
                } else if (key == KeyCode.J) {
                    newTileY++;
                    if(!isValidMove(newTileX, newTileY)) newTileY--;
                } else if (key == KeyCode.H) {
                    newTileX--;
                    if(!isValidMove(newTileX, newTileY)) newTileX++;
                } else if (key == KeyCode.K) {
                    System.out.println("hi Movement");
                    newTileX++;
                    if(!isValidMove(newTileX, newTileY)) newTileX--;
                } else if (key == KeyCode.Y) {
                    placeBomb(player2);
                }
                player2.setY(newTileY);
                player2.setX(newTileX);
                if (isValidMove(newTileX, newTileY)) {
                    System.out.println(player2.getY());
                    System.out.println(player2.getX());
                    double incrementX = ((double) (newTileX - currentplayer2X));
                    double incrementY = ((double) (newTileY - currentplayer2Y));
                    System.out.println("Increment");
                    System.out.println(incrementX);
                    System.out.println(incrementY);
                    System.out.println(player2.getBody().getLayoutX());
                    Platform.runLater(() -> {
                        player2.getBody().setLayoutX((player2.getBody().getLayoutX() + incrementX * TILE_SIZE));
                        player2.getBody().setLayoutY((player2.getBody().getLayoutY() + incrementY * TILE_SIZE));
                        System.out.println(player2.getBody().getLayoutX());
                    });

                }
                isMoving = false;
            });
            movementThread.start();
        }
    }

    public void handleplayer1Movement(KeyCode key) {
        if (!isMoving) {
            isMoving = true;
            movementThread = new Thread(() -> {
                int currentplayer1X = player1.getX();
                int currentplayer1Y = player1.getY();
                int newTileX = currentplayer1X;
                int newTileY = currentplayer1Y;
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
                    placeBomb(player1);
                }
                player1.setY(newTileY);
                player1.setX(newTileX);
                if (isValidMove(newTileX, newTileY)) {
                    System.out.println(player1.getY());
                    System.out.println(player1.getX());
                    double incrementX = ((double) (newTileX - currentplayer1X));
                    double incrementY = ((double) (newTileY - currentplayer1Y));
                    System.out.println("Increment");
                    System.out.println(incrementX);
                    System.out.println(incrementY);
                    System.out.println(player1.getBody().getLayoutX());
                    Platform.runLater(() -> {
                        player1.getBody().setLayoutX((player1.getBody().getLayoutX() + incrementX * TILE_SIZE));
                        player1.getBody().setLayoutY((player1.getBody().getLayoutY() + incrementY * TILE_SIZE));
                        System.out.println(player1.getBody().getLayoutX());
                    });

                }
                isMoving = false;
            });
            movementThread.start();
        }
    }

    private boolean isValidCoordinate(int x, int y) {
        return x >= 0 && x <= WIDTH + 1 && y >= 0 && y <= HEIGHT + 1;
    }

    private boolean isValidMove(int x, int y) {
        // Replace this with your collision detection logic
        if((palBoard.getMap()[y][x] == 0 || palBoard.getMap()[y][x] == 1)){
            return true;// True for empty space, false for blocks
        }
        return false;
    }

    private void placeBomb(Player player) {
        if (palBoard.getMap()[player.getY()][player.getX()] == 0 || palBoard.getMap()[player.getY()][player.getX()] == 1 ) {
            palBoard.getMap()[player.getY()][player.getX()] = 3; // Set map value to 3 to indicate a bomb
            Bomb bomb = new Bomb(player.getX(),player.getY(),TILE_SIZE);
            System.out.println("pass1");
            Label timerLabel = new Label(Integer.toString(bomb.getRemainingTime())); // Countdown timer label
            timerLabel.setLayoutX(bomb.getBombVisual().getLayoutX() - timerLabel.prefWidth(0) / 2);
            timerLabel.setLayoutY(bomb.getBombVisual().getLayoutY() - bomb.getBombVisual().getRadius());
            System.out.println("pass1.5");
            Platform.runLater(() -> {
                palBoard.getChildren().addAll(bomb.getBombVisual(), timerLabel);
            });
            System.out.println("pass2");
            bombThread = new Thread(() -> {
                for (int i = 3; i >= 0; i--) { // Countdown from 4 to 0
                    final int count = i;
                    Platform.runLater(() -> {
                        timerLabel.setText(String.valueOf(count));
                        bomb.getBombVisual().setFill(getColorForCountdown(count)); // Change color based on countdown
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

    private void detonateBomb(Bomb bomb) {
        // Get bomb coordinates from the Circle object
        int bombX = (int) Math.floor(bomb.getBombVisual().getLayoutX() / TILE_SIZE);
        int bombY = (int) Math.floor(bomb.getBombVisual().getLayoutY() / TILE_SIZE);

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
            palBoard.getChildren().remove(bomb.getBombVisual()); // Remove the bomb Circle object
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

    public Player getPlayer2() {
        return player2;
    }

    public int getHEIGHT() {
        return HEIGHT;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public Player getplayer1() {
        return player1;
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
