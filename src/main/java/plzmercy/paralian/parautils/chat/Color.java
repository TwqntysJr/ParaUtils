package plzmercy.paralian.parautils.chat;

import org.bukkit.ChatColor;

import java.util.Collections;

public class Color {


    public static final String BLANK_MESSAGE = String.join("", Collections.nCopies(150, "§8 §8 §1 §3 §3 §7 §8 §r\n"));

    public static String translate(String s){
        return ChatColor.translateAlternateColorCodes('&', s);
    }
}
