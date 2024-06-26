package gameControl;

import boardView.PalBoard;
import config.Config;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import model.Bomb;
import model.Player;
import utils.AnimationUtils;
import utils.Goto;

import java.util.*;


public class GameController {

    private final int HEIGHT = 15;
    private final int WIDTH = 15;
    public static ArrayList<Player> players;
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
            Player player1 = new Player(1,3, 3,1);
            Player player2 = new Player(2,HEIGHT-2,WIDTH-2,2);
            palBoard.getChildren().add(player1.getBody());
            palBoard.getChildren().add(player2.getBody());
            players.set(0, player1);
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
            System.out.println("pass1.5");
            Platform.runLater(() -> {
                palBoard.getChildren().addAll(bomb.getBombVisual());
            });
            System.out.println("pass2");
            bombThread = new Thread(() -> {
                for (int i = 3; i >= 0; i--) { // Countdown from 4 to 0
                    int finalI = i;
                    Platform.runLater(() -> {
                        ColorAdjust colorAdjust = new ColorAdjust();
                        if( finalI % 2 == 0) {
                            colorAdjust.setHue(100);
                        } else {
                            colorAdjust.setBrightness(0);
                        }
                        bomb.getBombVisual().setEffect(colorAdjust);
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

        ArrayList<ImageView> explosionVisuals = new ArrayList<>();

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
                ImageView explosionVisual = createExplosionVisual(newX, newY);
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
                ImageView explosionVisual = createExplosionVisual(newX, newY);
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
                ImageView explosionVisual = createExplosionVisual(newX, newY);
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
                ImageView explosionVisual = createExplosionVisual(newX, newY);
                explosionVisuals.add(explosionVisual);
            }
        }

        // Remove the bomb visualization after detonation
        Platform.runLater(() -> {
            palBoard.getChildren().remove(bomb.getBombVisual()); // Remove the bomb Circle object
            palBoard.getChildren().removeIf(node -> node instanceof Label && ((Label) node).getText().equals("0")); // Remove the timer label (assuming initial text is "4")
        });

        Timeline removeExplosionsTimeline = new Timeline(new KeyFrame(Duration.seconds(0.5), event -> {
            for (ImageView explosionVisual : explosionVisuals) {
                palBoard.getChildren().remove(explosionVisual);
            }
        }));
        removeExplosionsTimeline.play();

        //Game Over
        if(isGameOver()){
            for ( Player p : players) {
                if (p != null) {
                    Goto.endGame(p);
                    return;
                }
            }
        }
    }

    private ImageView createExplosionVisual(double x, double y) {
        ImageView explosionVisual = new ImageView(AnimationUtils.getImageByPath("res/FireEffect.png")); // Adjust color as needed
        explosionVisual.setFitWidth(TILE_SIZE);
        explosionVisual.setFitHeight(TILE_SIZE);
        explosionVisual.setPreserveRatio(true);
        explosionVisual.setLayoutX(75 + x * TILE_SIZE);
        explosionVisual.setLayoutY(y * TILE_SIZE);
        // Add explosion visual to the game board
        Platform.runLater(() -> {
            palBoard.getChildren().add(explosionVisual);
        });
        return explosionVisual;
    }

    private ImageView findTile(int x, int y) {
        for (Node tile : palBoard.getChildren()) {
            if (tile.getLayoutX() == x * TILE_SIZE + 75  && tile.getLayoutY() == y * TILE_SIZE) {
                return (ImageView) tile;
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

    public static void deleteAllPlayer(){
        GameController.players.set(0,null);
        GameController.players.set(1,null);
        GameController.players.set(2,null);
        GameController.players.set(3,null);
    }

    public static GameController getInstance() {
        if(instance == null)
            instance = new GameController();
        return instance;
    }
}
