package back.listeners;

import back.objects.BackLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (isNotPlayer(sender)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "You have to be a player to execute this command");
            return true;
        }

        Player senderPlayer = (Player) sender;
        BackLocation.mostrarLocalizaciones(senderPlayer);

        return true;
    }

    private boolean isNotPlayer (CommandSender sender){
        return !(sender instanceof Player);
    }
}
