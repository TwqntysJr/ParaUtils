package plzmercy.paralian.parautils.schematic;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTCompressedStreamTools;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.Directional;
import org.bukkit.block.data.MultipleFacing;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import plzmercy.paralian.parautils.chat.Common;
import plzmercy.paralian.parautils.schematic.block.NBTBlock;
import plzmercy.paralian.parautils.schematic.exceptions.SchematicNotLoadedException;
import plzmercy.paralian.parautils.schematic.exceptions.WrongIdException;
import plzmercy.paralian.parautils.schematic.material.BlockDataMaterial;
import plzmercy.paralian.parautils.schematic.material.DirectionalBlockDataMaterial;
import plzmercy.paralian.parautils.schematic.material.MultipleFacingBlockDataMaterial;
import plzmercy.paralian.parautils.schematic.material.NBTMaterial;

import java.io.File;
import java.io.FileInputStream;
import java.util.*;


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
    private LinkedHashMap<Vector, NBTBlock> nbtBlocks = new LinkedHashMap<>();
    private LinkedHashMap<Integer, BlockData> blocks = new LinkedHashMap<>();

    // TODO: Add general pasting logic

    public Collection<Location> pasteSchematic(Location loc, Player paster, int time, Option... options) throws SchematicNotLoadedException{
        try {
            if(width == 0 || height == 0 || length == 0 || blocks.isEmpty()){
                throw new SchematicNotLoadedException("Data has not been loaded yet");
            }
            List<Option> optionsList = Arrays.asList(options);
            Data tracker = new Data();

            LinkedHashMap<Integer, Location> indexLocations = new LinkedHashMap<>();
            LinkedHashMap<Integer, Location> delayedIndexLocations = new LinkedHashMap<>();

            LinkedHashMap<Integer, NBTBlock> nbtData = new LinkedHashMap<>();

            BlockFace face = getDirection(paster);

            //Loop through all blocks in schematic file.
            for(int x = 0; x < width; ++x){
                for (int y = 0; y < height; ++y){
                    for (int z = 0; z < length; ++z){
                        int index = y * width * length + z * width + x;
                        Vector point = new Vector(x, y, z);
                        Location location = null;
                        int width2 = width / 2;
                        int length2 = length / 2;

                        if(width > 15){
                            switch (face){
                                case NORTH:
                                    location = new Location(loc.getWorld(), (x * -1 + loc.getBlockX()) + width2, y + loc.getBlockY(), (z + loc.getBlockZ()) + length2 - 35);
                                    break;
                                case EAST:
                                    location = new Location(loc.getWorld(), (-z + loc.getBlockX()) - length2 + 34, y + loc.getBlockY(), (-x - 1) + (width + loc.getBlockZ()) - width2);
                                    break;
                                case SOUTH:
                                    location = new Location(loc.getWorld(), (x + loc.getBlockX()) - width2, y + loc.getBlockZ(), (z * -1 + loc.getBlockZ()) - length2 + 35);
                                    break;
                                case WEST:
                                    location = new Location(loc.getWorld(), (z + loc.getBlockX()) + length2 - 35, y + loc.getBlockY(), (x + 1) - (width - loc.getBlockZ()) + width2);
                                    break;
                                default:
                                    break;
                            }
                        }else if(width < 15){
                            switch (face) {
                                case NORTH:
                                    location = new Location(loc.getWorld(), (x * - 1 + loc.getBlockX()) + width2, y + loc.getBlockY(), (z + loc.getBlockZ()) + length2 - 10);

                                    break;
                                case EAST:
                                    location = new Location(loc.getWorld(), (-z + loc.getBlockX()) - length2 + 10, y + loc.getBlockY(), (-x - 1) + (width + loc.getBlockZ()) - width2);

                                    break;
                                case SOUTH:
                                    location = new Location(loc.getWorld(), (x + loc.getBlockX()) - width2, y + loc.getBlockY(), (z * - 1 + loc.getBlockZ()) - length2 + 10);
                                    break;
                                case WEST:
                                    location = new Location(loc.getWorld(), (z + loc.getBlockX()) + length2 - 10, y + loc.getBlockY(), (x + 1) - (width - loc.getBlockZ()) + width2);
                                    break;
                                default:
                                    break;
                            }
                        }else{
                            switch (face) {
                                case NORTH:
                                    location = new Location(loc.getWorld(), (x * - 1 + loc.getBlockX()) + width2, y + loc.getBlockY(), (z + loc.getBlockZ()) + length2 - 10);
                                    break;
                                case EAST:
                                    location = new Location(loc.getWorld(), (-z + loc.getBlockX()) - length2 + 10, y + loc.getBlockY(), (-x - 1) + (width + loc.getBlockZ()) - width2);
                                    break;
                                case SOUTH:
                                    location = new Location(loc.getWorld(), (x + loc.getBlockX()) - width2, y + loc.getBlockY(), (z * - 1 + loc.getBlockZ()) - length2 + 10);
                                    break;
                                case WEST:
                                    location = new Location(loc.getWorld(), (z + loc.getBlockX()) + length2 - 10, y + loc.getBlockY(), (x + 1) - (width - loc.getBlockZ()) + width2);
                                    break;
                                default:
                                    break;
                            }
                        }
                        BlockData data = blocks.get((int) blockdatas[index]);
                        /**
                         * Ignore blocks that aren't air.
                         */
                        Material material = data.getMaterial();
                        if(material != Material.AIR){
                            if(NBTMaterial.fromBukkit(material) == null || !NBTMaterial.fromBukkit(material).isDelayed()){
                                indexLocations.put(index, location);
                            }else{
                                delayedIndexLocations.put(index, location);
                            }
                        }
                        if(nbtBlocks.containsKey(point)){
                            nbtData.put(index, nbtBlocks.get(point));
                        }
                    }
                }
            }
            indexLocations.putAll(delayedIndexLocations);
            delayedIndexLocations.clear();
            boolean validated = true;
            for(Location validate : indexLocations.values()){
                boolean isWater = validate.clone().subtract(0, 1, 0).getBlock().getType() == Material.WATER;
                boolean isAir = new Location(validate.getWorld(), validate.getX(), validate.getY() - 1, validate.getZ()).getBlock().getType() == Material.AIR;
                boolean isSolid = validate.getBlock().getType() != Material.AIR;
                boolean isTransparent = optionsList.contains(Option.IGNORE_TRANSPARANT) && validate.getBlock().isPassable() && validate.getBlock().getType() != Material.AIR;

                if(!optionsList.contains(Option.PLACE_ANYWHERE) && isWater || isAir || isSolid && !isTransparent){
                    // Show fake blocks where block is interfering with schematic
                    paster.sendBlockChange(validate.getBlock().getLocation(), Material.RED_STAINED_GLASS.createBlockData());
                    validated = false;
                }else{
                    // Show fake block for air
                    paster.sendBlockChange(validate.getBlock().getLocation(), Material.GREEN_STAINED_GLASS.createBlockData());
                }
                if(!optionsList.contains(Option.PREVIEW)){
                    Bukkit.getScheduler().runTaskLaterAsynchronously(plugin, () ->{
                        if(validate.getBlock().getType() == Material.AIR) paster.sendBlockChange(validate.getBlock().getLocation(), Material.AIR.createBlockData());
                    }, 60);
                }
                if(optionsList.contains(Option.PREVIEW)) return  indexLocations.values();
                if(!validated) return null;

                // Start pasting each block every tick
                Scheduler scheduler = new Scheduler();
                tracker.trackCurrentBlock = 0;
                // List of block faces to update after the schematic is done paster.
                List<Block> toUpdate = new ArrayList<>();
                indexLocations.forEach((index, location) ->{
                    Block block = location.getBlock();
                    BlockData data = blocks.get((int) blockdatas[index]);
                    if(Tag.STAIRS.getValues().contains(data.getMaterial()) || Tag.FENCES.getValues().contains(data.getMaterial())){
                        toUpdate.add(block);
                    }
                });
                scheduler.setTask(Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, ()->{
                    paster.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(ChatColor.RED.toString() + ChatColor.BOLD + "!! Building in progress.. Move out of the way !!").create());
                    // Get the block set the type, data and then update the state.
                    List<Location> locations = new ArrayList<>(indexLocations.values());
                    List<Integer> indexes = new ArrayList<>(indexLocations.keySet());

                    Block block = locations.get(tracker.trackCurrentBlock).getBlock();
                    BlockData data = blocks.get((int) blockdatas[indexes.get(tracker.trackCurrentBlock)]);
                    block.setType(data.getMaterial(), false);
                    block.setBlockData(data);

                    /**
                     * Check block data for certain key blocks
                     *
                     * @IRON_BLOCK = Begin position
                     * @GOLD_BLOCK = End Position
                     * @DIAMOND_BLOCK = Interaction point
                     *
                     */

                    if(block.getType() == Material.IRON_BLOCK){
                        beginPos = block.getLocation();
                        block.setType(Material.GRASS_BLOCK);
                    }
                    if(block.getType() == Material.GOLD_BLOCK){
                        endPos = block.getLocation();
                        block.setType(Material.AIR);
                    }
                    if(block.getType() == Material.DIAMOND_BLOCK){
                        // TODO: Make someway an customizable interaction point
                    }
                    // Udpate block states
                    if(nbtData.containsKey(indexes.get(tracker.trackCurrentBlock))){
                        NBTBlock nbtBlock = nbtData.get(indexes.get(tracker.trackCurrentBlock));
                        try{
                            BlockState state = block.getState();
                            nbtBlock.setData(state);
                            state.update();
                        }catch (WrongIdException ex){
                            ex.printStackTrace();
                        }
                    }
                    // Update block faces
                    BlockDataMaterial blockDataMaterial = null;
                    if (block.getState().getBlockData() instanceof Directional){
                        blockDataMaterial = new DirectionalBlockDataMaterial(block.getState());
                    }else if(block.getState().getBlockData() instanceof MultipleFacing){
                        blockDataMaterial = new MultipleFacingBlockDataMaterial(blockDataMaterial.getState());
                    }
                    if(blockDataMaterial != null) block.setBlockData(blockDataMaterial.update(face));
                    block.getState().update(true, false);

                    // Play block effects;
                    block.getLocation().getWorld().spawnParticle(Particle.CLOUD, block.getLocation(), 6);
                    block.getLocation().getWorld().playEffect(block.getLocation(), Effect.STEP_SOUND, block.getType());

                    tracker.trackCurrentBlock++;
                    paster.spigot().sendMessage(ChatMessageType.ACTION_BAR, new ComponentBuilder(ChatColor.RED.toString() + ChatColor.BOLD + "!! Building in progress... Move out of the way  !!").create());

                }, 20, 1));
                beginPos = null;
                endPos = null;
                return indexLocations.values();

            }
        } catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    /**
     * Paste a Schematic with the time. Defaulting to 1 block per second.
     * @param location
     * @param paste
     * @param options
     * @return
     * @throws SchematicNotLoadedException
     * @see #loadSchematic()
     */
    public Collection<Location> pasteSchematic(Location location, Player paste, Option... options) throws SchematicNotLoadedException{
        return pasteSchematic(location, paste, 20+15, options);
    }

    public Schematic loadSchematic(){
        try {
            // Read the file, get width; height length blocsk & data.
            FileInputStream fis = new FileInputStream(schematic);
            NBTTagCompound nbt = NBTCompressedStreamTools.a(fis);

            width = nbt.g("Width");
            height = nbt.g("Height");
            length = nbt.g("Length");
            blockdatas = nbt.m("BlockData");
            NBTTagCompound palette = nbt.p("Palette");
            NBTTagList tiles = (NBTTagList) nbt.c("BlockEntities");

            // Load NBT Data
            if (tiles != null) {
                for (NBTBase tile : tiles) {
                    if (tile instanceof NBTTagCompound) {
                        NBTTagCompound compound = (NBTTagCompound) tile;
                        if (!compound.d().isEmpty()) {
                            NBTMaterial nbtMaterial = NBTMaterial.fromTag(compound);
                            if (nbtMaterial != null) {
                                NBTBlock nbtBlock = nbtMaterial.getNbtBlock(compound);
                                if (!nbtBlock.isEmpty()) nbtBlocks.put(nbtBlock.getOffset(), nbtBlock);
                            }
                        }
                    }
                }
            }
            palette.d().forEach(rawState -> {
                int id = palette.h(rawState);
                BlockData blockData = Bukkit.createBlockData(rawState);
                blocks.put(id, blockData);
            });
            fis.close();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return this;
    }

    public Schematic(Plugin plugin, File schematic){
        this.plugin = plugin;
        this.schematic = schematic;
    }

    private BlockFace getDirection(Player p){
        float yaw = p.getLocation().getYaw();
        if(yaw < 0){
            yaw += 360;
        }
        if(yaw >= 315 || yaw < 45){
            return BlockFace.SOUTH;
        }else if(yaw > 135){
            return BlockFace.WEST;
        }else if(yaw > 225){
            return BlockFace.NORTH;
        }else if(yaw > 315){
            return BlockFace.EAST;
        }
        return BlockFace.NORTH;
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
