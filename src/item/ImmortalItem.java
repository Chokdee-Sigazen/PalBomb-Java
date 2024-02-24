package item;

import model.Player;

public class ImmortalItem implements Item {
    private final int IMMORTAL_DURATION = 10;

    public void effect(Player player){
        player.setImmortal(true);
        Thread effectThread = new Thread(() ->{
            try{
                Thread.sleep(IMMORTAL_DURATION * 1000);
                player.setImmortal(false);
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
