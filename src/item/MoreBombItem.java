package item;

import model.Player;

public class MoreBombItem implements Item {
    public void effect(Player player){
        player.setBomb(player.getBomb() + 1);
    }
    public String toString(){
        return "use " + getClass().getSimpleName();
    }
}
