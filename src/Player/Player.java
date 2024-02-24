package Player;

public class Player {
    private boolean isImmortal;

    private boolean isGhost;

    private Status status;

    public Player() {
        this.isImmortal = false;
        this.isGhost = false;
        this.status = new Status(1,1,1);
    }
}
