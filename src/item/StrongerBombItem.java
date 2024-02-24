package item;

import model.Player;

public class StrongerBombItem implements Item {
    public void effect(Player player){
        player.setPower(player.getPower() + 1);
    }

    public String toString(){
        return "get " + getClass().getSimpleName();
    }
}
