package back.listeners;

import back.objects.BackLocation;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Optional;

import static back.utils.Methods.*;

public class PlayerInventoryClick implements Listener {

    @EventHandler
    public void onPlayerInventoryClick(InventoryClickEvent event) {
        try {
            String inventoryTitle = event.getView().getTitle();
            if (!hasSameTitleOfBackGUI(inventoryTitle)) {
                return;
            }
            ItemStack clickedItem = event.getCurrentItem();
            if(!matchesItemsNames(clickedItem, "NETHER_BRICKS", "ENDER_PEARL", "DIRT")){
                return;
            }
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();

            Optional<World> optionalWorld = getWorldByItem(clickedItem);

            if(optionalWorld.isPresent()){
                BackLocation.performBackTP(player, optionalWorld.get(), clickedItem);
            }else{
                player.sendMessage(ChatColor.DARK_RED + "There has been an error, world not found. Contact with the owner of the plugin");
            }

        }catch (NullPointerException ignored) {
            //When you click outside of the inventory, the event throws NullPointerException. That's why we need to capture it with try-catch block
        }
    }

    private boolean hasSameTitleOfBackGUI (String title) {
        return title.equalsIgnoreCase(BackLocation.titulo);
    }

    private Optional<World> getWorldByItem (ItemStack item) {
        World.Environment worldType;

        if (matchesItemsNames(item, "NETHER_BRICKS")) {
            worldType = World.Environment.NETHER;
        } else if (matchesItemsNames(item, "ENDER_PEARL")) {
            worldType = World.Environment.THE_END;
        } else {
            worldType = World.Environment.NORMAL;
        }

        return Bukkit.getServer().getWorlds().stream()
                .filter(world -> world.getEnvironment().equals(worldType))
                .findAny();
    }
}
