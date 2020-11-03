package back.listeners;

import back.objects.BackLocationsInventoryBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class PlayerCommand implements CommandExecutor {
    private final BackLocationsInventoryBuilder backInventoryBuilder;

    public PlayerCommand(BackLocationsInventoryBuilder backInventoryBuilder) {
        this.backInventoryBuilder = backInventoryBuilder;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String s, String[] strings) {
        if (isNotPlayer(sender)) {
            Bukkit.getConsoleSender().sendMessage(ChatColor.DARK_RED + "You have to be a player to execute this command");
            return true;
        }

        Player player = (Player) sender;
        Inventory inventory = backInventoryBuilder.buildBackInventoryGUI(player);
        player.openInventory(inventory);

        return true;
    }

    private boolean isNotPlayer (CommandSender sender){
        return !(sender instanceof Player);
    }
}
