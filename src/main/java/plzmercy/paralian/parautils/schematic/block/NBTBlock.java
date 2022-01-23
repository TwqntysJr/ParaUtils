package plzmercy.paralian.parautils.schematic.block;

import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.block.BlockState;
import org.bukkit.util.Vector;
import plzmercy.paralian.parautils.schematic.exceptions.WrongIdException;


public abstract class NBTBlock {

    private final NBTTagCompound nbtTag;

    public NBTBlock(NBTTagCompound nbtTag){
        this.nbtTag = nbtTag;
    }

    public NBTTagCompound getNbtTag(){
        return nbtTag;
    }

    public org.bukkit.util.Vector getOffset(){
        NBTTagCompound compound = this.getNbtTag();
        int[] pos = compound.n("Pos");
        return new Vector(pos[0], pos[1], pos[2]);
    }

    public abstract void setData(BlockState state) throws WrongIdException;

    public abstract boolean isEmpty();
}
