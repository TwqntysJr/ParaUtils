package plzmercy.paralian.parautils.general;

import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

public class PingUtility {
    private static Method getHandleMethod;
    private static Field pingField;

    //Get ping method TODO: add javadoc
    public static int getPing(Player p){
        try{
            if(getHandleMethod == null){
                getHandleMethod = p.getClass().getDeclaredMethod("getHandle");
                getHandleMethod.setAccessible(true);
            }
            Object entityPlayer = getHandleMethod.invoke(p);
            if(pingField == null){
                pingField = entityPlayer.getClass().getDeclaredField("ping");
                pingField.setAccessible(true);
            }
            int ping = pingField.getInt(entityPlayer);

            return ping > 0 ? ping : 0;
        }catch(Exception e){
            System.out.println("API Error with ping method. Please contact the developer or make a issue on github. V0.1.4");
            return 0;
        }
    }

}
