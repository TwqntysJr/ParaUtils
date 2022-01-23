package plzmercy.paralian.parautils.schematic.material;

import org.bukkit.Material;
import org.bukkit.Tag;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.BlockState;
import org.bukkit.block.data.BlockData;
import org.bukkit.block.data.MultipleFacing;

public class MultipleFacingBlockDataMaterial extends BlockDataMaterial{
    public MultipleFacingBlockDataMaterial(BlockState state) {
        super(state);
    }

    @Override
    public BlockData update(BlockFace face) {
        BlockState state = this.getState();
        MultipleFacing facing = (MultipleFacing) state.getBlockData();
        facing.getAllowedFaces().forEach(bf ->{
            Block rl = state.getBlock().getRelative(bf);
            if(rl.getType() == Material.AIR || rl.getType().toString().contains("SLAB") || rl.getType().toString().contains("STAIRS")){
                if(facing.hasFace(bf)) facing.setFace(bf, false);
            }else{
                if(!rl.getType().toString().contains("SLAB") && !rl.getType().toString().contains("STAIRS") && !Tag.ANVIL.getValues().contains(rl.getType()) && rl.getType().isSolid() && rl.getType().isBlock()){
                    if(!facing.hasFace(bf)) facing.setFace(bf, true);
                }
            }
        });
        return facing;
    }
}
