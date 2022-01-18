package plzmercy.paralian.parautils.chat;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Common {
    /**
     * Color any message
     *
     * @Usage #color("&9Some &aColored &eMessage");
     * @param message
     * @return
     */

    public String color(String message){
        return ChatColor.translateAlternateColorCodes('&', message);
    }

    /**
     * Color list
     *
     * @param lines
     * @return
     */
    public static List<String> color(final List<String> lines) {
        final List<String> toReturn = new ArrayList<>();
        for (final String line : lines) toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        return toReturn;
    }

    public static List<String> color(final String[] lines) {
        final List<String> toReturn = new ArrayList<>();
        for (final String line : lines) if (line != null) toReturn.add(ChatColor.translateAlternateColorCodes('&', line));
        return toReturn;
    }

    /**
     * Send colored message to console
     *
     * @usage #console("&9Hello &eWorld!");
     * @param message
     * @return
     */
    public void console(String message){
        Bukkit.getServer().getConsoleSender().sendMessage(color(message));
    }

    /**
     * Easily Create items
     *
     * @usage #makeItem(Material.DIAMOND_BLOCK, "Hello world", "Test");
     * @param material
     * @param displayName
     * @param lore
     * @return
     */
    public ItemStack makeItem(Material material, String displayName, String... lore) {

        ItemStack item = new ItemStack(material);
        ItemMeta itemMeta = item.getItemMeta();
        itemMeta.setDisplayName(displayName);
        itemMeta.setLore(Arrays.asList(lore));
        item.setItemMeta(itemMeta);

        return item;
    }
}
