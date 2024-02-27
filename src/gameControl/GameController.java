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

    private final int HEIGHT = 15;
    private final int WIDTH = 15;
    private Player player1;
    private Player player2;

    private Player player3;
    private Player player4;
    private PalBoard palBoard;
    private static GameController instance;
    private boolean isMoving = false;
    private Thread movementThread;
    private Thread bombThread;



    private final int TILE_SIZE = 35;

    public GameController() {
        this.palBoard = new PalBoard();
    }

    public PalBoard getPalBoard() {
        return palBoard;
    }

    public void startGame(int number){
        if(number == 2){
            this.player1 = new Player(1,3, 3,1);
            this.player2 = new Player(2,HEIGHT-2,WIDTH-2,2);
            palBoard.getChildren().add(GameController.getInstance().getplayer1().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer2().getBody());
        }
        if(number == 3){
            this.player1 = new Player(1,3, 3,1);
            this.player2 = new Player(2,HEIGHT-2,WIDTH-2,2);
            this.player3 = new Player(3,HEIGHT-2,3,3);
            palBoard.getChildren().add(GameController.getInstance().getplayer1().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer2().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer3().getBody());
        }
        if(number == 4){
            this.player1 = new Player(1,3, 3,1);
            this.player2 = new Player(2,HEIGHT-2,WIDTH-2,2);
            this.player3 = new Player(3,HEIGHT-2,3,3);
            this.player4 = new Player(4,3,WIDTH-2,4);
            palBoard.getChildren().add(GameController.getInstance().getplayer1().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer2().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer3().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer4().getBody());
        }
        System.out.println("hi");
        palBoard.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (key == KeyCode.W || key == KeyCode.A || key == KeyCode.S || key == KeyCode.D || key == KeyCode.Q) {
                GameController.getInstance().handleplayer1Movement(key);
            } else if (key == KeyCode.Y || key == KeyCode.G || key == KeyCode.H || key == KeyCode.J || key == KeyCode.T) {
                GameController.getInstance().handlePlayer2Movement(key);
            } else if (key == KeyCode.NUMPAD8 || key == KeyCode.NUMPAD5 || key == KeyCode.NUMPAD4 || key == KeyCode.NUMPAD6 ||key == KeyCode.NUMPAD7 ) {
                GameController.getInstance().handleplayer3Movement(key);
            }else if (key == KeyCode.P || key == KeyCode.SEMICOLON || key == KeyCode.L || key == KeyCode.QUOTE ||key == KeyCode.O ) {
                GameController.getInstance().handleplayer4Movement(key);
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
                if (key == KeyCode.Y) {
                    newTileY--;
                    if(!isValidMove(newTileX, newTileY)) newTileY++;
                } else if (key == KeyCode.H) {
                    newTileY++;
                    if(!isValidMove(newTileX, newTileY)) newTileY--;
                } else if (key == KeyCode.G) {
                    newTileX--;
                    if(!isValidMove(newTileX, newTileY)) newTileX++;
                } else if (key == KeyCode.J) {
                    System.out.println("hi Movement");
                    newTileX++;
                    if(!isValidMove(newTileX, newTileY)) newTileX--;
                } else if (key == KeyCode.T) {
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
    public void handleplayer3Movement(KeyCode key) {
        if (!isMoving) {
            isMoving = true;
            movementThread = new Thread(() -> {
                int currentplayer3X = player3.getX();
                int currentplayer3Y = player3.getY();
                int newTileX = currentplayer3X;
                int newTileY = currentplayer3Y;
                if (key == KeyCode.NUMPAD8) {
                    newTileY--;
                    if(!isValidMove(newTileX, newTileY)) newTileY++;
                } else if (key == KeyCode.NUMPAD5) {
                    newTileY++;
                    if(!isValidMove(newTileX, newTileY)) newTileY--;
                } else if (key == KeyCode.NUMPAD4) {
                    newTileX--;
                    if(!isValidMove(newTileX, newTileY)) newTileX++;
                } else if (key == KeyCode.NUMPAD6) {
                    System.out.println("hi Movement");
                    newTileX++;
                    if(!isValidMove(newTileX, newTileY)) newTileX--;
                } else if (key == KeyCode.NUMPAD7) {
                    placeBomb(player3);
                }
                player3.setY(newTileY);
                player3.setX(newTileX);
                if (isValidMove(newTileX, newTileY)) {
                    System.out.println(player3.getY());
                    System.out.println(player3.getX());
                    double incrementX = ((double) (newTileX - currentplayer3X));
                    double incrementY = ((double) (newTileY - currentplayer3Y));
                    System.out.println("Increment");
                    System.out.println(incrementX);
                    System.out.println(incrementY);
                    System.out.println(player3.getBody().getLayoutX());
                    Platform.runLater(() -> {
                        player3.getBody().setLayoutX((player3.getBody().getLayoutX() + incrementX * TILE_SIZE));
                        player3.getBody().setLayoutY((player3.getBody().getLayoutY() + incrementY * TILE_SIZE));
                        System.out.println(player3.getBody().getLayoutX());
                    });

                }
                isMoving = false;
            });
            movementThread.start();
        }
    }
    public void handleplayer4Movement(KeyCode key) {
        if (!isMoving) {
            isMoving = true;
            movementThread = new Thread(() -> {
                int currentplayer4X = player4.getX();
                int currentplayer4Y = player4.getY();
                int newTileX = currentplayer4X;
                int newTileY = currentplayer4Y;
                if (key == KeyCode.P) {
                    newTileY--;
                    if(!isValidMove(newTileX, newTileY)) newTileY++;
                } else if (key == KeyCode.SEMICOLON) {
                    newTileY++;
                    if(!isValidMove(newTileX, newTileY)) newTileY--;
                } else if (key == KeyCode.L) {
                    newTileX--;
                    if(!isValidMove(newTileX, newTileY)) newTileX++;
                } else if (key == KeyCode.QUOTE) {
                    System.out.println("hi Movement");
                    newTileX++;
                    if(!isValidMove(newTileX, newTileY)) newTileX--;
                } else if (key == KeyCode.O) {
                    placeBomb(player4);
                }
                player4.setY(newTileY);
                player4.setX(newTileX);
                if (isValidMove(newTileX, newTileY)) {
                    System.out.println(player4.getY());
                    System.out.println(player4.getX());
                    double incrementX = ((double) (newTileX - currentplayer4X));
                    double incrementY = ((double) (newTileY - currentplayer4Y));
                    System.out.println("Increment");
                    System.out.println(incrementX);
                    System.out.println(incrementY);
                    System.out.println(player4.getBody().getLayoutX());
                    Platform.runLater(() -> {
                        player4.getBody().setLayoutX((player4.getBody().getLayoutX() + incrementX * TILE_SIZE));
                        player4.getBody().setLayoutY((player4.getBody().getLayoutY() + incrementY * TILE_SIZE));
                        System.out.println(player4.getBody().getLayoutX());
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
        int bombX = (int) Math.floor((bomb.getBombVisual().getLayoutX()-75) / TILE_SIZE);
        int bombY = (int) Math.floor(bomb.getBombVisual().getLayoutY() / TILE_SIZE);
        palBoard.getMap()[bombY][bombX] = 0; // Reset map value back to 0

        // Check surrounding tiles within range 1 in the four directions
        for (int dx = -1; dx <= 1; dx++) {
            // Check only horizontal movement
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
            if (tile.getLayoutX() == x * TILE_SIZE + 75  && tile.getLayoutY() == y * TILE_SIZE) {
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

    public void setPalBoard(PalBoard palBoard) {
        this.palBoard = palBoard;
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

    public Player getPlayer3() {
        return player3;
    }

    public Player getPlayer4() {
        return player4;
    }

    public static GameController getInstance() {
        if(instance == null)
            instance = new GameController();
        return instance;
    }
}
