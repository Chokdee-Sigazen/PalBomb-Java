package gameControl;

import boardView.PalBoard;
import config.Config;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Bomb;
import model.Player;

import java.util.*;


public class GameController {

    private final int HEIGHT = 15;
    private final int WIDTH = 15;
//    private Player player1;
//    private Player player2;
//    private Player player3;
//    private Player player4;
    private ArrayList<Player> players;
    private PalBoard palBoard;
    private static GameController instance;
    private boolean isMoving = false;
    private Thread movementThread;
    private Thread bombThread;

    private final Map<Integer, Map<KeyCode, Boolean>> playerKeyStates = new HashMap<>();
    private Map<String, Timeline> continuousMoveTimelines = new HashMap<>();
    private Map<Integer, Timeline> animationTimelines = new HashMap<>();

    private final int TILE_SIZE = Config.tileSize;

    public GameController() {
        this.palBoard = new PalBoard();
        this.players = new ArrayList<Player>(4);
        for (int i = 0; i < 4; i++) {
            this.players.add(null);
        }
    }

    public PalBoard getPalBoard() {
        return palBoard;
    }

    public void startGame(int number){
        if(number >= 2){
            //Player player1 = new Player(1,3, 3,1);
            Player player2 = new Player(2,HEIGHT-2,WIDTH-2,2);
            //palBoard.getChildren().add(player1.getBody());
            palBoard.getChildren().add(player2.getBody());
            //players.set(0, player1);
            players.set(1, player2);
        }
        if(number >= 3){
            Player player3 = new Player(3,HEIGHT-2,3,3);
            palBoard.getChildren().add(player3.getBody());
            players.set(2, player3);
        }
        if(number == 4){
            Player player4 = new Player(4,3,WIDTH-2,4);
            palBoard.getChildren().add(player4.getBody());
            players.set(3, player4);
        }
        System.out.println("hi");
        palBoard.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (getPlayer1() != null && (key == KeyCode.W || key == KeyCode.A || key == KeyCode.S || key == KeyCode.D || key == KeyCode.Q)) {
                startContinuousMove(getPlayer1(), getPlayer1().getDirection(key));
//                startAnimation();
            } else if (getPlayer2() != null && (key == KeyCode.Y || key == KeyCode.G || key == KeyCode.H || key == KeyCode.J || key == KeyCode.T)) {
                startContinuousMove(getPlayer2(), getPlayer2().getDirection(key));
//                startAnimation();
            } else if (getPlayer3() != null && (key == KeyCode.NUMPAD8 || key == KeyCode.NUMPAD5 || key == KeyCode.NUMPAD4 || key == KeyCode.NUMPAD6 ||key == KeyCode.NUMPAD7)) {
                startContinuousMove(getPlayer3(), getPlayer3().getDirection(key));
//                startAnimation();
            }else if (getPlayer4() != null && (key == KeyCode.P || key == KeyCode.SEMICOLON || key == KeyCode.L || key == KeyCode.QUOTE ||key == KeyCode.O)) {
                startContinuousMove(getPlayer4(), getPlayer4().getDirection(key));
//                startAnimation();
            }
        });
        palBoard.setOnKeyReleased(event -> {
            KeyCode key = event.getCode();
            if (getPlayer1() != null && (key == KeyCode.W || key == KeyCode.A || key == KeyCode.S || key == KeyCode.D || key == KeyCode.Q)) {
                stopContinuousMove(getPlayer1() + getPlayer1().getDirection(key));
            } else if (getPlayer2() != null && (key == KeyCode.Y || key == KeyCode.G || key == KeyCode.H || key == KeyCode.J || key == KeyCode.T)) {
                stopContinuousMove(getPlayer2() + getPlayer2().getDirection(key));
            } else if (getPlayer3() != null && (key == KeyCode.NUMPAD8 || key == KeyCode.NUMPAD5 || key == KeyCode.NUMPAD4 || key == KeyCode.NUMPAD6 ||key == KeyCode.NUMPAD7)) {
                stopContinuousMove(getPlayer3() + getPlayer3().getDirection(key));
            }else if (getPlayer4() != null && (key == KeyCode.P || key == KeyCode.SEMICOLON || key == KeyCode.L || key == KeyCode.QUOTE ||key == KeyCode.O)) {
                stopContinuousMove(getPlayer4() + getPlayer4().getDirection(key));
            }
        });
    }

    private void startContinuousMove(Player player, String direction) {

        movementThread = new Thread(() -> {
            if (Objects.equals(direction, "bomb")) {
                placeBomb(player);
            }
            else if (!continuousMoveTimelines.containsKey(player.toString() + direction)) {
//                switch (direction) {
//                    case "up":
//                        if(!isValidMove(player.getX(), player.getY()-1)) return;
//                        player.moveUp();
//                        break;
//                    case "down":
//                        if(!isValidMove(player.getX(), player.getY()+1)) return;
//                        player.moveDown();
//                        break;
//                    case "left":
//                        if(!isValidMove(player.getX()-1, player.getY())) return;
//                        player.moveLeft();
//                        break;
//                    case "right":
//                        if(!isValidMove(player.getX()+1, player.getY())) return;
//                        player.moveRight();
//                        break;
//                }
                Timeline timeline = new Timeline(
                        new KeyFrame(javafx.util.Duration.millis(300), e -> {
                            switch (direction) {
                                case "up":
                                    if(!isValidMove(player.getX(), player.getY()-1)) return;
                                    player.moveUp();
                                    break;
                                case "down":
                                    if(!isValidMove(player.getX(), player.getY()+1)) return;
                                    player.moveDown();
                                    break;
                                case "left":
                                    if(!isValidMove(player.getX()-1, player.getY())) return;
                                    player.moveLeft();
                                    break;
                                case "right":
                                    if(!isValidMove(player.getX()+1, player.getY())) return;
                                    player.moveRight();
                                    break;
                            }
                        })
                );
                timeline.setCycleCount(Timeline.INDEFINITE);
                timeline.play();
                continuousMoveTimelines.put(player + direction, timeline);
            }
        });
        movementThread.start();
    }

    private void stopContinuousMove(String index) {
        Timeline timeline = continuousMoveTimelines.get(index);
        if (timeline != null) {
            timeline.stop();
            continuousMoveTimelines.remove(index);
        }
    }

//    public void handlePlayer2Movement(KeyCode key) {
//        movementThread = new Thread(() -> {
//        });
//        movementThread.start();
//    }
//    public void handleplayer3Movement(KeyCode key) {
//        if (!isMoving) {
//            isMoving = true;
//            movementThread = new Thread(() -> {
//                int currentplayer3X = player3.getX();
//                int currentplayer3Y = player3.getY();
//                int newTileX = currentplayer3X;
//                int newTileY = currentplayer3Y;
//                if (key == KeyCode.NUMPAD8) {
//                    newTileY--;
//                    if(!isValidMove(newTileX, newTileY)) newTileY++;
//                } else if (key == KeyCode.NUMPAD5) {
//                    newTileY++;
//                    if(!isValidMove(newTileX, newTileY)) newTileY--;
//                } else if (key == KeyCode.NUMPAD4) {
//                    newTileX--;
//                    if(!isValidMove(newTileX, newTileY)) newTileX++;
//                } else if (key == KeyCode.NUMPAD6) {
//                    System.out.println("hi Movement");
//                    newTileX++;
//                    if(!isValidMove(newTileX, newTileY)) newTileX--;
//                } else if (key == KeyCode.NUMPAD7) {
//                    placeBomb(player3);
//                }
//                player3.setY(newTileY);
//                player3.setX(newTileX);
//                if (isValidMove(newTileX, newTileY)) {
//                    System.out.println(player3.getY());
//                    System.out.println(player3.getX());
//                    double incrementX = ((double) (newTileX - currentplayer3X));
//                    double incrementY = ((double) (newTileY - currentplayer3Y));
//                    System.out.println("Increment");
//                    System.out.println(incrementX);
//                    System.out.println(incrementY);
//                    System.out.println(player3.getBody().getLayoutX());
//                    Platform.runLater(() -> {
//                        player3.getBody().setLayoutX((player3.getBody().getLayoutX() + incrementX * TILE_SIZE));
//                        player3.getBody().setLayoutY((player3.getBody().getLayoutY() + incrementY * TILE_SIZE));
//                        System.out.println(player3.getBody().getLayoutX());
//                    });
//
//                }
//                isMoving = false;
//            });
//            movementThread.start();
//        }
//    }
//    public void handleplayer4Movement(KeyCode key) {
//        if (!isMoving) {
//            isMoving = true;
//            movementThread = new Thread(() -> {
//                int currentplayer4X = player4.getX();
//                int currentplayer4Y = player4.getY();
//                int newTileX = currentplayer4X;
//                int newTileY = currentplayer4Y;
//                if (key == KeyCode.P) {
//                    newTileY--;
//                    if(!isValidMove(newTileX, newTileY)) newTileY++;
//                } else if (key == KeyCode.SEMICOLON) {
//                    newTileY++;
//                    if(!isValidMove(newTileX, newTileY)) newTileY--;
//                } else if (key == KeyCode.L) {
//                    newTileX--;
//                    if(!isValidMove(newTileX, newTileY)) newTileX++;
//                } else if (key == KeyCode.QUOTE) {
//                    System.out.println("hi Movement");
//                    newTileX++;
//                    if(!isValidMove(newTileX, newTileY)) newTileX--;
//                } else if (key == KeyCode.O) {
//                    placeBomb(player4);
//                }
//                player4.setY(newTileY);
//                player4.setX(newTileX);
//                if (isValidMove(newTileX, newTileY)) {
//                    System.out.println(player4.getY());
//                    System.out.println(player4.getX());
//                    double incrementX = ((double) (newTileX - currentplayer4X));
//                    double incrementY = ((double) (newTileY - currentplayer4Y));
//                    System.out.println("Increment");
//                    System.out.println(incrementX);
//                    System.out.println(incrementY);
//                    System.out.println(player4.getBody().getLayoutX());
//                    Platform.runLater(() -> {
//                        player4.getBody().setLayoutX((player4.getBody().getLayoutX() + incrementX * TILE_SIZE));
//                        player4.getBody().setLayoutY((player4.getBody().getLayoutY() + incrementY * TILE_SIZE));
//                        System.out.println(player4.getBody().getLayoutX());
//                    });
//
//                }
//                isMoving = false;
//            });
//            movementThread.start();
//        }
//    }
//    public void handleplayer1Movement(KeyCode key) {
//        if (!isMoving) {
//            isMoving = true;
//            movementThread = new Thread(() -> {
//                int currentplayer1X = player1.getX();
//                int currentplayer1Y = player1.getY();
//                int newTileX = currentplayer1X;
//                int newTileY = currentplayer1Y;
//                if (key == KeyCode.W) {
//                    newTileY--;
//                    if(!isValidMove(newTileX, newTileY)) newTileY++;
//                } else if (key == KeyCode.S) {
//                    newTileY++;
//                    if(!isValidMove(newTileX, newTileY)) newTileY--;
//                } else if (key == KeyCode.A) {
//                    newTileX--;
//                    if(!isValidMove(newTileX, newTileY)) newTileX++;
//                } else if (key == KeyCode.D) {
//                    System.out.println("hi Movement");
//                    newTileX++;
//                    if(!isValidMove(newTileX, newTileY)) newTileX--;
//                } else if (key == KeyCode.Q) {
//                    placeBomb(player1);
//                }
//                player1.setY(newTileY);
//                player1.setX(newTileX);
//                if (isValidMove(newTileX, newTileY)) {
//                    System.out.println(player1.getY());
//                    System.out.println(player1.getX());
//                    double incrementX = ((double) (newTileX - currentplayer1X));
//                    double incrementY = ((double) (newTileY - currentplayer1Y));
//                    System.out.println("Increment");
//                    System.out.println(incrementX);
//                    System.out.println(incrementY);
//                    System.out.println(player1.getBody().getLayoutX());
//                    Platform.runLater(() -> {
//                        player1.getBody().setLayoutX((player1.getBody().getLayoutX() + incrementX * TILE_SIZE));
//                        player1.getBody().setLayoutY((player1.getBody().getLayoutY() + incrementY * TILE_SIZE));
//                        System.out.println(player1.getBody().getLayoutX());
//                    });
//
//                }
//                isMoving = false;
//            });
//            movementThread.start();
//        }
//    }

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

    private boolean isGameOver(){
        int count = 0;
        if (getPlayer1() != null) count++;
        if (getPlayer2() != null) count++;
        if (getPlayer3() != null) count++;
        if (getPlayer4() != null) count++;
        return count==1;
    }

    private void placeBomb(Player player) {
        if (player.getBomb() > 0 && palBoard.getMap()[player.getY()][player.getX()] == 0 || palBoard.getMap()[player.getY()][player.getX()] == 1 ) {
            System.out.println(player.toString() + "Bomb" + player.getBomb());
            player.setBomb(player.getBomb()-1);
            System.out.println(player.toString() + "Bomb" + player.getBomb());
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
                detonateBomb(bomb, player);
            });
            bombThread.start();
            System.out.println("pass3");
        }
    }

    private void detonateBomb(Bomb bomb, Player player) {
        player.setBomb(player.getBomb()+1);
        int bombX = (int) Math.floor((bomb.getBombVisual().getLayoutX() - 75) / TILE_SIZE);
        int bombY = (int) Math.floor(bomb.getBombVisual().getLayoutY() / TILE_SIZE);
        palBoard.getMap()[bombY][bombX] = 0; // Reset map value back to 0

        ArrayList<Circle> explosionVisuals = new ArrayList<>();

        int playerPower = player.getPower();

        for (int dx = 0; dx <= playerPower; dx++) { //right
            int newX = bombX + dx;
            int newY = bombY;

            for (int i = 0; i < 4; i++) {
                Player p = players.get(i);
                if (p != null && p.getX() == newX && p.getY() == newY) {
                    p.setHp(p.getHp()-1);
                    if (p.getHp() == 0) {
                        int finalI = i;
                        Platform.runLater(() -> {
                            palBoard.getChildren().remove(p.getBody());
                            players.set(finalI, null);
                        });
                    }
                }
            }

            if (isValidCoordinate(newX, newY) && palBoard.getMap()[newY][newX] == 3) break;

            if (isValidCoordinate(newX, newY) && palBoard.getMap()[newY][newX] != 3) {
                if (palBoard.getMap()[newY][newX] == 2) {
                    // Destroy breakable tile (map[y][x] == 2)
                    palBoard.getMap()[newY][newX] = 0;
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(findTile(newX, newY));
                    });
                }
                Circle explosionVisual = createExplosionVisual(newX * TILE_SIZE, newY * TILE_SIZE);
                explosionVisuals.add(explosionVisual);
            }
        }

        for (int dx = -1; dx >= -playerPower; dx--) { //left
            int newX = bombX + dx;
            int newY = bombY;

            for (int i = 0; i < 4; i++) {
                Player p = players.get(i);
                if (p != null && p.getX() == newX && p.getY() == newY) {
                    p.setHp(p.getHp()-1);
                    if (p.getHp() == 0) {
                        int finalI = i;
                        Platform.runLater(() -> {
                            palBoard.getChildren().remove(p.getBody());
                            players.set(finalI, null);
                        });
                    }
                }
            }

            if (isValidCoordinate(newX, newY) && palBoard.getMap()[newY][newX] == 3) break;

            if (isValidCoordinate(newX, newY) && palBoard.getMap()[newY][newX] != 3) {
                if (palBoard.getMap()[newY][newX] == 2) {
                    // Destroy breakable tile (map[y][x] == 2)
                    palBoard.getMap()[newY][newX] = 0;
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(findTile(newX, newY));
                    });
                }
                Circle explosionVisual = createExplosionVisual(newX * TILE_SIZE, newY * TILE_SIZE);
                explosionVisuals.add(explosionVisual);
            }
        }

        for (int dy = 1; dy <= playerPower; dy++) { //down
            int newX = bombX;
            int newY = bombY + dy;

            for (int i = 0; i < 4; i++) {
                Player p = players.get(i);
                if (p != null && p.getX() == newX && p.getY() == newY) {
                    p.setHp(p.getHp()-1);
                    if (p.getHp() == 0) {
                        int finalI = i;
                        Platform.runLater(() -> {
                            palBoard.getChildren().remove(p.getBody());
                            players.set(finalI, null);
                        });
                    }
                }
            }

            if (isValidCoordinate(newX, newY) && palBoard.getMap()[newY][newX] == 3) break;

            if (isValidCoordinate(newX, newY) && palBoard.getMap()[newY][newX] != 3) {
                if (palBoard.getMap()[newY][newX] == 2) {
                    // Destroy breakable tile (map[y][x] == 2)
                    palBoard.getMap()[newY][newX] = 0;
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(findTile(newX, newY));
                    });
                }
                Circle explosionVisual = createExplosionVisual(newX * TILE_SIZE, newY * TILE_SIZE);
                explosionVisuals.add(explosionVisual);
            }
        }

        for (int dy = -1; dy >= -playerPower; dy--) { //up
            int newX = bombX;
            int newY = bombY + dy;

            for (int i = 0; i < 4; i++) {
                Player p = players.get(i);
                if (p != null && p.getX() == newX && p.getY() == newY) {
                    p.setHp(p.getHp()-1);
                    if (p.getHp() == 0) {
                        int finalI = i;
                        Platform.runLater(() -> {
                            palBoard.getChildren().remove(p.getBody());
                            players.set(finalI, null);
                        });
                    }
                }
            }

            if (isValidCoordinate(newX, newY) && palBoard.getMap()[newY][newX] == 3) break;

            if (isValidCoordinate(newX, newY) && palBoard.getMap()[newY][newX] != 3) {
                if (palBoard.getMap()[newY][newX] == 2) {
                    // Destroy breakable tile (map[y][x] == 2)
                    palBoard.getMap()[newY][newX] = 0;
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(findTile(newX, newY));
                    });
                }
                Circle explosionVisual = createExplosionVisual(newX * TILE_SIZE, newY * TILE_SIZE);
                explosionVisuals.add(explosionVisual);
            }
        }

        // Remove the bomb visualization after detonation
        Platform.runLater(() -> {
            palBoard.getChildren().remove(bomb.getBombVisual()); // Remove the bomb Circle object
            palBoard.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("0")); // Remove the timer label (assuming initial text is "4")
        });

        Timeline removeExplosionsTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            for (Circle explosionVisual : explosionVisuals) {
                palBoard.getChildren().remove(explosionVisual);
            }
        }));
        removeExplosionsTimeline.play();
    }

    private Circle createExplosionVisual(double x, double y) {
        Circle explosionVisual = new Circle(75 + x + TILE_SIZE / 2, y + TILE_SIZE / 2, TILE_SIZE / 2, Color.ORANGE); // Adjust color as needed
        // Add explosion visual to the game board
        Platform.runLater(() -> {
            palBoard.getChildren().add(explosionVisual);
        });
        return explosionVisual;
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

    public int getHEIGHT() {
        return HEIGHT;
    }

    public void setPalBoard(PalBoard palBoard) {
        this.palBoard = palBoard;
    }

    public int getWIDTH() {
        return WIDTH;
    }

    public int getTILE_SIZE() {
        return TILE_SIZE;
    }

    public Player getPlayer1() {
        return players.get(0);
    }

    public Player getPlayer2() {
        return players.get(1);
    }

    public Player getPlayer3() {
        return players.get(2);
    }

    public Player getPlayer4() {
        return players.get(3);
    }

    public static GameController getInstance() {
        if(instance == null)
            instance = new GameController();
        return instance;
    }
}
