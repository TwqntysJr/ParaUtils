package plzmercy.paralian.parautils.command;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class BukkitCommand extends org.bukkit.command.Command{
    private Plugin owningPlugin;
    private CommandExecutor ex;


    protected BukkitCommand(String label, CommandExecutor executor, Plugin owner){
        super(label);
        this.ex = executor;
        this.owningPlugin = owner;
        this.usageMessage = "";
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        boolean success = false;

        if(!owningPlugin.isEnabled()) return false;

        if(!testPermission(sender)) return true;

        try{
            success = ex.onCommand(sender, this, commandLabel, args);
        }catch (Throwable thr){
            throw new CommandException("Unhandled exception handling command " + commandLabel + " in plugin " + owningPlugin.getDescription().getName(), thr);
        }

        if(!success && usageMessage.length() > 0){
            for(String s : usageMessage.replace("<command>", commandLabel).split("\n")){
                sender.sendMessage(s);
            }
        }
        return success;
    }
    //TODO: Add the a tab completion
}
