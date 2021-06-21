package plzmercy.paralian.parautils.menu.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.InventoryHolder;
import plzmercy.paralian.parautils.menu.Menu;

public class MenuListener implements Listener {



    @EventHandler
    public void onMenuClick(InventoryClickEvent e){
        Player p = (Player) e.getWhoClicked();
        if(e.getCurrentItem() == null) return;
        if(e.getClickedInventory() == null) return;

        InventoryHolder holder = e.getClickedInventory().getHolder();
        if(holder instanceof Menu){
            e.setCancelled(true);
            Menu menu = (Menu) holder;
            menu.handleMenu(e);
        }
    }
}
