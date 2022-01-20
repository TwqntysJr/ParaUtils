package plzmercy.paralian.parautils.schematic;

import org.bukkit.Location;
import org.bukkit.plugin.Plugin;
import plzmercy.paralian.parautils.chat.Common;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.Vector;

public class Schematic {

    private Common common = new Common();
    private Plugin plugin;
    private File schematic;

    private Location beginPos;
    private Location endPos;

    private short width = 0;
    private short height = 0;
    private short length = 0;

    private byte[] blockdatas;

    // TODO: Add LinkedHashMap for NBTBlock & Blockdata
    //private LinkedHashMap<Vector>

    // TODO: Add general pasting logic

    public Schematic(Plugin plugin, File schematic){
        this.plugin = plugin;
        this.schematic = schematic;
    }


    protected static class Data{
        int trackCurrentBlock;
    }

    public enum Option{
        /**
         * Shows the preview of the schematic that you are about to build.
         */
        PREVIEW,
        /**
         * Ignores blocks & replaces them with schematic blocks
         *
         * @Note Do not use this
         */
        PLACE_ANYWHERE,
        /**
         * Ignore transparant blocks like water, lava & air in pasting the schematic
         */
        IGNORE_TRANSPARANT
    }
}
