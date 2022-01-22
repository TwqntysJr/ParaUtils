package plzmercy.paralian.parautils.schematic.block;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import org.bukkit.Material;
import org.bukkit.block.BlockState;
import org.bukkit.block.Chest;
import org.bukkit.inventory.ItemStack;
import plzmercy.paralian.parautils.schematic.exceptions.WrongIdException;

import java.util.HashMap;
import java.util.Map;

public class NBTChestBlock extends NBTBlock{
    private Map<Integer, ItemStack> allItems = new HashMap<>();

    public NBTChestBlock(NBTTagCompound nbtTag){
        super(nbtTag);
    }
    @Override
    public void setData(BlockState state) throws WrongIdException {
        Chest chest = (Chest) state;
        for(Integer loc : allItems.keySet()){
            chest.getSnapshotInventory().setItem(loc, allItems.get(loc));
        }
    }

    @Override
    public boolean isEmpty() {

        try{

        } catch (Exception ex){}

        return false;
    }

    /**
     * @return a map, with the key as the slot and the value as the item
     * @throws WrongIdException if it's not a chest
     */
    public Map<Integer, ItemStack> getItem() throws WrongIdException{
        if(!allItems.isEmpty()) return allItems;
        NBTTagCompound compound = this.getNbtTag();
        if(compound.l("id").equals("minecraft:chest")){
            if(compound.l("Items") != null){
                NBTTagList items = (NBTTagList) compound.c("Items");
                for (int i = 0; i < items.size(); i++) {
                    NBTTagCompound anItem = items.a(i);
                    Material mat = Material.valueOf(anItem.l("id").replace("minecraft:", "").toUpperCase());
                    ItemStack item = new ItemStack(mat, anItem.h("Count"));
                    allItems.put(anItem.h("Slot"), item);
                }
            }
        }else{
            throw new WrongIdException("ID of NBT data was not a chest, it was instead a: " + compound.c());
        }
        return allItems;
    }
}
