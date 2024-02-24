package item;

import model.Player;

public class FeatherBootsItem implements  Item {
    public void effect(Player player){
        player.setSpeed(player.getSpeed() + 1);
    }
    public String toString(){
        return "use " + getClass().getSimpleName();
    }
}
