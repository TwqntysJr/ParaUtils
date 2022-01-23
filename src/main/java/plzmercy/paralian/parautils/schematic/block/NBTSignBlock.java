package plzmercy.paralian.parautils.schematic.block;

import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import net.minecraft.nbt.NBTTagCompound;
import org.bukkit.block.BlockState;
import org.bukkit.block.Sign;
import plzmercy.paralian.parautils.schematic.exceptions.WrongIdException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NBTSignBlock extends NBTBlock{
    private Map<Position, String> lines = new HashMap<>();

    public NBTSignBlock(NBTTagCompound nbtTag){
        super(nbtTag);
    }

    @Override
    public void setData(BlockState state) throws WrongIdException {
        Sign sign = (Sign) state;
        int current = 0;
        for(String line : this.getLines()){
            sign.setLine(current, line);
            current++;
        }
    }

    @Override
    public boolean isEmpty() {
        try{
            return getLines().isEmpty();
        }catch(WrongIdException ex){
            ex.printStackTrace();
        }
        return false;
    }

    public String getLine(Position position) throws WrongIdException{
        if(lines.containsKey(position)){
            return lines.get(position);
        }
        NBTTagCompound compound = this.getNbtTag();
        if(compound.l("Id").equals("minecraft:sign")){
            String s1 = compound.l(position.getId());
            JsonObject jsonObject = new Gson().fromJson(s1, JsonObject.class);
            if(jsonObject.get("extra") != null){
                JsonArray array = jsonObject.get("extra").getAsJsonArray();
                return array.get(0).getAsJsonObject().get("text").getAsString();
            }
        }else{
            throw new WrongIdException("ID of NBT Item was not a sign, it was instead a: " + compound.c("Id"));
        }
        return null;
    }

    public List<String> getLines() throws WrongIdException{
        List<String> lines = new ArrayList<>();
        for(Position pos : Position.values()){
            lines.add(getLine(pos));
        }
        return lines;
    }

    public enum Position {
        TEXT_ONE("Text1"),
        TEXT_TWO("Text2"),
        TEXT_THREE("Text3"),
        TEXT_FOUR("Text4");

        public String getId() {
            return id;
        }

        private String id;

        Position(String id) {
            this.id = id;
        }
    }
}
