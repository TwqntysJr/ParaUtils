package plzmercy.paralian.parautils.title;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class ActionbarEvent extends Event {

    private static final HandlerList handlers = new HandlerList();
    private final Player player;
    private String actionbar;

    private boolean cancelled = false;

    public ActionbarEvent(Player player, String actionbar){
        this.player = player;
        this.actionbar = actionbar;
    }

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public Player getPlayer() {
        return player;
    }

    public String getActionbar() {
        return actionbar;
    }

    public void setActionbar(String actionbar) {
        this.actionbar = actionbar;
    }

    public boolean isCancelled() {
        return cancelled;
    }

    public void setCancelled(boolean cancelled) {
        this.cancelled = cancelled;
    }
}
