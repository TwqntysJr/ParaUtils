package plzmercy.paralian.parautils.player;

import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.Server;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import plzmercy.paralian.parautils.chat.Color;

import java.util.List;

public class PlayerHeadUtility {
    private Material mat = Material.PLAYER_HEAD;
    private JavaPlugin plugin;

    private int amount = 1;

    private String title;
    private String owner = "Steve";
    private List<String> lores;

    public PlayerHeadUtility title(String title){
        this.title = title;
        return this;
    }

    public PlayerHeadUtility owner(String owner){
        this.owner = owner;
        return this;
    }

    public PlayerHeadUtility lores(List<String> lores){
        this.lores = lores;
        return this;
    }

    public ItemStack build(){

        ItemStack is = new ItemStack(Material.PLAYER_HEAD);
        SkullMeta meta = (SkullMeta) is.getItemMeta();
        is.setAmount(amount);
        if(this.title!=null){
            meta.setDisplayName(Color.translate(this.title));
        }
        if(this.lores != null && this.lores.size()>0){
            meta.setLore(this.lores);
        }
        meta.setOwningPlayer(plugin.getConfig().getOfflinePlayer(owner));
        is.setItemMeta(meta);
        return is;
    }

}
