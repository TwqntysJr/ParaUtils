package plzmercy.paralian.parautils.menu;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import plzmercy.paralian.parautils.menu.menuutils.PlayerUtilManager;

import java.util.Arrays;

public abstract class Menu implements InventoryHolder {
    protected Inventory inv;
    protected PlayerUtilManager playerUtilManager;
    protected ItemStack FILLER_GLASS = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
    protected ItemMeta FILLER_GLASS_META = FILLER_GLASS.getItemMeta();

    public Menu(PlayerUtilManager playerUtilManager){
        this.playerUtilManager = playerUtilManager;
    }
    public abstract String getMenuName();

    public abstract int getSlots();

    public abstract void handleMenu(InventoryClickEvent e);

    public abstract void setMenuItems();

    public void open(){

        inv = Bukkit.createInventory(this, getSlots(), getMenuName());

        this.setMenuItems();
    }
    
    
    public Inventory getInventory(){
        return inv;
    }

    /**
     * fills the unfilled slots in a inventory.
     */
    public void setFillerGlass(){
        FILLER_GLASS_META.setDisplayName("");
        for (int i = 0; i < getSlots(); i++) {

            if(inv.getItem(i) == null){
                inv.setItem(i, FILLER_GLASS);
            }
        }
    }


    /**
     * Create an item easily.
     * 
     * @param mat Material
     * @param displayName String
     * @param lore String as array
     * @return ItemStack
     */
    public ItemStack makeItem(Material mat, String displayName, String... lore){
        ItemStack item = new ItemStack( mat);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);
        return item;
    }
}
