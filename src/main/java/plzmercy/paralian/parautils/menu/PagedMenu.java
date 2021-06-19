package plzmercy.paralian.parautils.menu;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import plzmercy.paralian.parautils.menu.menuutils.PlayerUtilManager;

public abstract class PagedMenu extends Menu{
    protected int page =0;
    protected  int maxItemsPerPage = 28;
    protected  int index = 0;

    public PagedMenu(PlayerUtilManager playerUtilManager){
        super(playerUtilManager);
    }

    public void addMenuBorder(){
        inv.setItem(48, makeItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Left"));
        inv.setItem(49, makeItem(Material.BARRIER, ChatColor.DARK_RED + "Close"));
        inv.setItem(50, makeItem(Material.DARK_OAK_BUTTON, ChatColor.GREEN + "Right"));

        for (int i = 0; i < 10; i++) {
            if(inv.getItem(i) == null){
                inv.setItem(i, super.FILLER_GLASS);
            }
        }

        inv.setItem(17, super.FILLER_GLASS);
        inv.setItem(18, super.FILLER_GLASS);
        inv.setItem(26, super.FILLER_GLASS);
        inv.setItem(26, super.FILLER_GLASS);
        inv.setItem(35, super.FILLER_GLASS);
        inv.setItem(36, super.FILLER_GLASS);
        for (int i = 44; i < 54; i++) {
            if(inv.getItem(i) == null){
                inv.setItem(i, super.FILLER_GLASS);
            }
        }
    }

    public int getMaxItemsPerPage(){
        return maxItemsPerPage;
    }
}
