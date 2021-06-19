package plzmercy.paralian.parautils.general;

import org.bukkit.Location;

import javax.swing.text.html.parser.Entity;
import java.util.concurrent.TimeUnit;

public class StringUtils {


    /**
     * Enchantment util
     * @param name
     * @return String
     */
    public static String getEnchantment(String name) {
        String enchant = name;

        if (name.equalsIgnoreCase("sharp") || name.equalsIgnoreCase("sharpness")) {
            enchant = "DAMAGE_ALL";
        }

        if (name.equalsIgnoreCase("ff") || name.equalsIgnoreCase("featherfalling")) {
            enchant = "FEATHER_FALLING";
        }

        if (name.equalsIgnoreCase("fire") || name.equalsIgnoreCase("fireaspect")) {
            enchant = "FIRE_ASPECT";
        }

        if (name.equalsIgnoreCase("kb") || name.equalsIgnoreCase("knock")) {
            enchant = "KNOCKBACK";
        }

        if (name.equalsIgnoreCase("smi") || name.equalsIgnoreCase("smite")) {
            enchant = "DAMAGE_UNDEAD";
        }

        if (name.equalsIgnoreCase("bane") || name.equalsIgnoreCase("baneof") || name.equalsIgnoreCase("baneofarthropods")) {
            enchant = "DAMAGE_ARTHROPODS";
        }

        if (name.equalsIgnoreCase("prot") || name.equalsIgnoreCase("protection")) {
            enchant = "PROTECTION_ENVIRONMENTAL";
        }

        if (name.equalsIgnoreCase("fire") || name.equalsIgnoreCase("fireprot") || name.equalsIgnoreCase("fireprotection")) {
            enchant = "PROTECTION_FIRE";
        }

        if (name.equalsIgnoreCase("blast") || name.equalsIgnoreCase("blastprot") || name.equalsIgnoreCase("blastprotection")) {
            enchant = "PROTECTION_EXPLOSIONS";
        }

        if (name.equalsIgnoreCase("proj") || name.equalsIgnoreCase("projprot") || name.equalsIgnoreCase("projectileprotection")) {
            enchant = "PROTECTION_PROJECTILE";
        }

        if (name.equalsIgnoreCase("loot") || name.equalsIgnoreCase("looting")) {
            enchant = "LOOT_BONUS_MOBS";
        }

        if (name.equalsIgnoreCase("fort") || name.equalsIgnoreCase("fortune")) {
            enchant = "LOOT_BONUS_BLOCKS";
        }

        if (name.equalsIgnoreCase("silk") || name.equalsIgnoreCase("silktouch")) {
            enchant = "SILK_TOUCH";
        }

        if (name.equalsIgnoreCase("pow") || name.equalsIgnoreCase("power")) {
            enchant = "ARROW_DAMAGE";
        }

        if (name.equalsIgnoreCase("pun") || name.equalsIgnoreCase("punch")) {
            enchant = "ARROW_KNOCKBACK";
        }

        if (name.equalsIgnoreCase("fla") || name.equalsIgnoreCase("flame")) {
            enchant = "ARROW_FIRE";
        }

        if (name.equalsIgnoreCase("inf") || name.equalsIgnoreCase("infinity")) {
            enchant = "ARROW_INFINITE";
        }

        if (name.equalsIgnoreCase("unb") || name.equalsIgnoreCase("unbreaking")) {
            enchant = "DURABILITY";
        }

        if (name.equalsIgnoreCase("eff") || name.equalsIgnoreCase("efficiency")) {
            enchant = "DIG_SPEED";
        }

        return enchant.toUpperCase();
    }

    /**
     * Get entity name correctly formatted.
     *
     * @param ent Entity
     * @return string
     */
    public static String getEntityName(Entity ent) {
        switch (ent.getName()) {
            case "BLAZE":
                return "Blaze";
            case "CAVE_SPIDER":
                return "Cave Spider";
            case "CREEPER":
                return "Creeper";
            case "ENDERMAN":
                return "Enderman";
            case "IRON_GOLEM":
                return "Iron Golem";
            case "MAGMA_CUBE":
                return "Magma Cube";
            case "PIG_ZOMBIE":
                return "Pig Zombie";
            case "PLAYER":
                return "Player";
            case "SILVERFISH":
                return "Silverfish";
            case "SKELETON":
                return "Skeleton";
            case "SLIME":
                return "Slime";
            case "SPIDER":
                return "Spider";
            case "VILLAGER":
                return "Villager";
            case "WITCH":
                return "Witch";
            case "WITHER":
                return "Wither";
            case "WOLF":
                return "Wolf";
            case "ZOMBIE":
                return "Zombie";
        }
        return "";
    }


    public static long parse(String input) {
        if (input == null || input.isEmpty()) {
            return -1L;
        }

        long result = 0L;

        StringBuilder number = new StringBuilder();

        for (int i = 0; i < input.length(); ++i) {
            char c = input.charAt(i);

            if (Character.isDigit(c)) {
                number.append(c);
            } else {
                String str;

                if (Character.isLetter(c) && !(str = number.toString()).isEmpty()) {
                    result += convert(Integer.parseInt(str), c);
                    number = new StringBuilder();
                }
            }
        }

        return result;
    }

    /**
     * Convert an int to a certain time unit
     *
     * @param v int value
     * @param u char select between <b>y M d h m s</b>
     * @return
     */
    private static long convert(int v, char u) {
        switch (u) {

            case 'y': {
                return v * TimeUnit.DAYS.toMillis(365L);
            }

            case 'M': {
                return v * TimeUnit.DAYS.toMillis(30L);
            }

            case 'd': {
                return v * TimeUnit.DAYS.toMillis(1L);
            }

            case 'h': {
                return v * TimeUnit.HOURS.toMillis(1L);
            }

            case 'm': {
                return v * TimeUnit.MINUTES.toMillis(1L);
            }

            case 's': {
                return v * TimeUnit.SECONDS.toMillis(1L);
            }

            default: {
                return -1L;
            }
        }
    }


    /**
     * Format a location to the following
     * '[world, 5, 41, 2, 20, 20]'
     *  First param is the world name
     *  Second param is the Location x value
     *  Third param is the Location Y value
     *  Fourth param is the Location Z value
     *  Filth param is the Location Yaw value
     *  Sixth  param is the Location Pitch value
     *
     * @param location
     * @return String formatted nicely
     */
    public static String stringifyLocation(Location location) {
        return "[" + location.getWorld().getName() + "," + location.getX() + "," + location.getY() + "," + location.getZ() + "," + location.getYaw() + "," + location.getPitch() + "]";
    }


    public static String formatInt(int i) {
        int r = i * 1000;
        int sec = r / 1000 % 60;
        int min = r / 60000 % 60;
        int h = r / 3600000 % 24;

        return (h > 0 ? h + ":" : "") + (min < 10 ? "0" + min : min) + ":" + (sec < 10 ? "0" + sec : sec);
    }


    /**
     * Convert the first letter to an Uppercase letter.
     *
     * @param s string
     * @return string with first letter in uppercase.
     */
    public static String convertFirstUpperCase(String s) {
        return s.substring(0, 1).toUpperCase() + s.substring(1);
    }
}
