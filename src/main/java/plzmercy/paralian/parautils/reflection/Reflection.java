package plzmercy.paralian.parautils.reflection;

import com.sun.org.omg.SendingContext._CodeBaseImplBase;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

public class Reflection {

    /**
     * Get the current server NMS version
     *
     * @return String of version
     */
    public static String getVersion(){
        String name = Bukkit.getServer().getClass().getPackage().getName();
        String ver = name.substring(name.lastIndexOf('.') + 1) + ".";
        return ver;
    }

    public static Class<?> getNMSClass(String className){
        String fullName = "net.minecraft.server." + getVersion() + className;
        Class<?> _class = null;
        try{
            _class = Class.forName(fullName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return _class;
    }

    /**
     * Get a NMS Class
     *
     * @param className as a String
     * @return _class as a class
     * @throws Exception
     */
    public static Class<?> getNMSClassWithException(String className) throws Exception{
        String fullName = "net.minecraft.server." + getVersion() + className;
        Class<?> _class = Class.forName(fullName);
        return _class;
    }

    /**
     * Get a org.bukkit.craftbukkit class
     *
     * @param className as a String
     * @return _class as a class
     */
    public static Class<?> getOBCClass(String className){
        String fullName = "org.bukkit.craftbukkit." + getVersion() + className;
        Class<?> _class = null;
        try{
            _class = Class.forName(fullName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return _class;
    }

    /**
     * Get a specific field
     *
     * @param _class Class
     * @param name String
     * @return Field
     */
    public static Field getField(Class<?> _class, String name){
        try{
            Field field = _class.getDeclaredField(name);
            field.setAccessible(true);
            return field;
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    /**
     * Get a specific field with exception throwing
     *
     * @param _class Class
     * @param name String
     * @return
     * @throws Exception
     */
    public static Field getFieldWithException(Class<?> _class, String name)throws Exception{
        Field field = _class.getDeclaredField(name);
        field.setAccessible(true);
        return field;
    }

    public static boolean ClassListEqual(Class<?>[] a, Class<?>[] b){
        boolean equal = true;
        if(a.length != b.length) return false;
        for (int i = 0; i < a.length; i++) {
            if(a[i] != b[i]){
                equal = false;
                break;
            }
        }
        return equal;
    }

    public static Method getMethod(Class<?> _class, String name, Class<?>... args){
        for(Method m : _class.getMethods()){
            if(m.getName().equals(name) && (args.length == 0 || ClassListEqual(args, m.getParameterTypes()))){
                m.setAccessible(true);
                return m;
            }
        }
        return null;
    }
    public static Object getHandle(Object obj){
        try {
            return getMethod(obj.getClass(), "getHandle", new Class[0]).invoke(obj, new Object[0]);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public static void sendPacket(Player p, Object packet){
        try{
            Object handle = p.getClass().getMethod("getHandle").invoke(p);
            Object pconn = handle.getClass().getField("playerConnection").get(handle);
            pconn.getClass().getMethod("sendPacket", getNMSClass("Packet")).invoke(pconn, packet);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void setFinalStatic(Field f, Object newVal)throws Exception{
        f.setAccessible(true);

        Field modifyField = Field.class.getDeclaredField("modifiers");
        modifyField.setAccessible(true);
        modifyField.setInt(f, f.getModifiers() &~Modifier.FINAL);
        f.set(null, newVal);
    }
}
