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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;


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

    private final Map<Integer, Map<KeyCode, Boolean>> playerKeyStates = new HashMap<>();
    private Map<String, Timeline> continuousMoveTimelines = new HashMap<>();
    private Map<Integer, Timeline> animationTimelines = new HashMap<>();

    private final int TILE_SIZE = Config.tileSize;

    public GameController() {
        this.palBoard = new PalBoard();
    }

    public PalBoard getPalBoard() {
        return palBoard;
    }

    public void startGame(int number){
        if(number == 2){
            //this.player1 = new Player(1,3, 3,1);
            this.player2 = new Player(2,HEIGHT-2,WIDTH-2,2);
            //palBoard.getChildren().add(GameController.getInstance().getPlayer1().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer2().getBody());
        }
        if(number == 3){
            this.player1 = new Player(1,3, 3,1);
            this.player2 = new Player(2,HEIGHT-2,WIDTH-2,2);
            this.player3 = new Player(3,HEIGHT-2,3,3);
            palBoard.getChildren().add(GameController.getInstance().getPlayer1().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer2().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer3().getBody());
        }
        if(number == 4){
            this.player1 = new Player(1,3, 3,1);
            this.player2 = new Player(2,HEIGHT-2,WIDTH-2,2);
            this.player3 = new Player(3,HEIGHT-2,3,3);
            this.player4 = new Player(4,3,WIDTH-2,4);
            palBoard.getChildren().add(GameController.getInstance().getPlayer1().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer2().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer3().getBody());
            palBoard.getChildren().add(GameController.getInstance().getPlayer4().getBody());
        }
        System.out.println("hi");
        palBoard.setOnKeyPressed(event -> {
            KeyCode key = event.getCode();
            if (player1 != null && (key == KeyCode.W || key == KeyCode.A || key == KeyCode.S || key == KeyCode.D || key == KeyCode.Q)) {
                startContinuousMove(player1, player1.getDirection(key));
//                startAnimation();
            } else if (player2 != null && (key == KeyCode.Y || key == KeyCode.G || key == KeyCode.H || key == KeyCode.J || key == KeyCode.T)) {
                startContinuousMove(player2, player2.getDirection(key));
//                startAnimation();
            } else if (player3 != null && (key == KeyCode.NUMPAD8 || key == KeyCode.NUMPAD5 || key == KeyCode.NUMPAD4 || key == KeyCode.NUMPAD6 ||key == KeyCode.NUMPAD7)) {
                startContinuousMove(player3, player3.getDirection(key));
//                startAnimation();
            }else if (player4 != null && (key == KeyCode.P || key == KeyCode.SEMICOLON || key == KeyCode.L || key == KeyCode.QUOTE ||key == KeyCode.O)) {
                startContinuousMove(player4, player4.getDirection(key));
//                startAnimation();
            }
        });
        palBoard.setOnKeyReleased(event -> {
            KeyCode key = event.getCode();
            if (player1 != null && (key == KeyCode.W || key == KeyCode.A || key == KeyCode.S || key == KeyCode.D || key == KeyCode.Q)) {
                stopContinuousMove(player1 + player1.getDirection(key));
            } else if (player2 != null && (key == KeyCode.Y || key == KeyCode.G || key == KeyCode.H || key == KeyCode.J || key == KeyCode.T)) {
                stopContinuousMove(player2 + player2.getDirection(key));
            } else if (player3 != null && (key == KeyCode.NUMPAD8 || key == KeyCode.NUMPAD5 || key == KeyCode.NUMPAD4 || key == KeyCode.NUMPAD6 ||key == KeyCode.NUMPAD7)) {
                stopContinuousMove(player3 + player3.getDirection(key));
            }else if (player4 != null && (key == KeyCode.P || key == KeyCode.SEMICOLON || key == KeyCode.L || key == KeyCode.QUOTE ||key == KeyCode.O)) {
                stopContinuousMove(player4 + player4.getDirection(key));
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

    public void handlePlayer2Movement(KeyCode key) {
        movementThread = new Thread(() -> {
        });
        movementThread.start();
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

    private boolean isGameOver(){
        int count = 0;
        if (player1 != null) count++;
        if (player2 != null) count++;
        if (player3 != null) count++;
        if (player4 != null) count++;
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

        // Create explosions in four directions within the range of player's power
        // Check surrounding tiles within range 1 in the four directions
        for (int dx = -playerPower; dx <= playerPower; dx++) {
            // Check only horizontal movement
            int newX = bombX + dx;
            int newY = bombY;
            if(player1 != null && player1.getX() == newX && player1.getY() == newY) {
                player1.setHp(player1.getHp()-1);
                if (player1.getHp() == 0) {
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(player1.getBody());
                        player1 = null;
                    });
                }
            }
            if(player2 != null && player2.getX() == newX && player2.getY() == newY) {
                player2.setHp(player2.getHp()-1);
                if (player2.getHp() == 0) {
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(player2.getBody());
                        player2 = null;
                    });
                }
            }
            if(player3 != null && player3.getX() == newX && player3.getY() == newY) {
                player3.setHp(player3.getHp()-1);
                if (player3.getHp() == 0) {
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(player3.getBody());
                        player3 = null;
                    });
                }
            }
            if(player4 != null && player4.getX() == newX && player4.getY() == newY) {
                player4.setHp(player4.getHp()-1);
                if (player4.getHp() == 0) {
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(player4.getBody());
                        player4 = null;
                    });
                }
            }
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

        // Check surrounding tiles within range 1 vertically
        for (int dy = -playerPower; dy <= playerPower; dy++) {
            int newX = bombX;
            int newY = bombY + dy;
            if(player1 != null && player1.getX() == newX && player1.getY() == newY) {
                player1.setHp(player1.getHp()-1);
                if (player1.getHp() == 0) {
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(player1.getBody());
                        player1 = null;
                    });
                }
            }
            if(player2 != null && player2.getX() == newX && player2.getY() == newY) {
                player2.setHp(player2.getHp()-1);
                if (player2.getHp() == 0) {
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(player2.getBody());
                        player2 = null;
                    });
                }
            }
            if(player3 != null && player3.getX() == newX && player3.getY() == newY) {
                player3.setHp(player3.getHp()-1);
                if (player3.getHp() == 0) {
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(player3.getBody());
                        player3 = null;
                    });
                }
            }
            if(player4 != null && player4.getX() == newX && player4.getY() == newY) {
                player4.setHp(player4.getHp()-1);
                if (player4.getHp() == 0) {
                    Platform.runLater(() -> {
                        palBoard.getChildren().remove(player4.getBody());
                        player4 = null;
                    });
                }
            }
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

    public Player getPlayer1() {
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
