package plzmercy.paralian.parautils.command;

import org.apache.commons.lang.Validate;
import org.bukkit.command.CommandException;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class BukkitCommand extends org.bukkit.command.Command{
    private Plugin owningPlugin;
    private CommandExecutor ex;
    public BukkitCompleter completer;

    public BukkitCommand(String label, CommandExecutor executor, Plugin owner){
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

    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args)
            throws CommandException, IllegalArgumentException {
        Validate.notNull(sender, "Sender cannot be null");
        Validate.notNull(args, "Arguments cannot be null");
        Validate.notNull(alias, "Alias cannot be null");

        List<String> completions = null;
        try {
            if (completer != null) {
                completions = completer.onTabComplete(sender, this, alias, args);
            }
            if (completions == null && ex instanceof TabCompleter) {
                completions = ((TabCompleter) ex).onTabComplete(sender, this, alias, args);
            }
        } catch (Throwable ex) {
            StringBuilder message = new StringBuilder();
            message.append("Unhandled exception during tab completion for command '/").append(alias).append(' ');
            for (String arg : args) {
                message.append(arg).append(' ');
            }
            message.deleteCharAt(message.length() - 1).append("' in plugin ")
                    .append(owningPlugin.getDescription().getFullName());
            throw new CommandException(message.toString(), ex);
        }

        if (completions == null) {
            return super.tabComplete(sender, alias, args);
        }
        return completions;
    }
}
