package plzmercy.paralian.parautils.menu.menuutils;

import org.bukkit.entity.Player;

public class PlayerUtilManager {
    private Player owner;

    public PlayerUtilManager(Player owner){
        this.owner = owner;
    }

    public Player getOwner(){
        return owner;
    }

    public void setOwner(Player owner){
        this.owner = owner;
    }
}
