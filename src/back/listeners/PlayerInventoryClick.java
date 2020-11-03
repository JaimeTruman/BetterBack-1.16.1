package back.listeners;

import back.objects.BackLocationsInventoryBuilder;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

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

            event.setCancelled(true);

            ItemStack clickedItem = event.getCurrentItem();
            if(!matchesItemsNames(clickedItem, "NETHER_BRICK", "ENDER_PEARL", "GRASS_BLOCK")){
                return;
            }

            Player player = (Player) event.getWhoClicked();
            Optional<World> optionalWorld = getWorldByItem(clickedItem);

            if(optionalWorld.isPresent()){
                this.performBackTP(player, optionalWorld.get(), clickedItem);
            }else{
                player.sendMessage(ChatColor.DARK_RED + "There has been an error, world not found. Contact with the owner of the plugin");
            }

        }catch (NullPointerException ignored) {
            //When you click outside the inventory, it throws NullPointerException. That's why we need to capture it and ignore it
        }
    }

    private boolean hasSameTitleOfBackGUI (String title) {
        return title.equalsIgnoreCase(BackLocationsInventoryBuilder.titleInventoryBackGUI);
    }

    private Optional<World> getWorldByItem (ItemStack item) {
        World.Environment worldType;

        if (matchesItemsNames(item, "NETHER_BRICK")) {
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

    private void performBackTP (Player player, World world, ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        String[] cords = itemMeta.getLore().get(0).split(" ");

        int x = Integer.parseInt(cords[0]);
        int y = Integer.parseInt(cords[1]);
        int z = Integer.parseInt(cords[2]);

        Location toTp = new Location(world, x, y, z);
        player.teleport(toTp);
    }
}
