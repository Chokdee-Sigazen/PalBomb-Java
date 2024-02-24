package item;

import model.Player;

public class HealPotion implements Item{
    public void effect(Player player){
        player.setHp(player.getHp() + 1);
    }

    public String toString(){
        return "use" + getClass().getSimpleName();
    }
}
