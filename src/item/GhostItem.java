package item;

import model.Player;

public class GhostItem {
    private final int GHOST_DURATION = 10;

    public void effect(Player player){
        player.setGhost(true);
        Thread effectThread = new Thread(() ->{
            try{
                Thread.sleep(GHOST_DURATION * 1000);
                player.setGhost(false);
            }
            catch(InterruptedException e) {
                e.printStackTrace();
            }
        });
        effectThread.start();
    }
    public String toString(){
        return "use " + getClass().getSimpleName();
    }
}
