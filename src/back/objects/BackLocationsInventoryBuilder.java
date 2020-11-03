package back.objects;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static back.utils.Methods.*;
import static back.utils.Methods.roundDecimals;

public final class BackLocationsInventoryBuilder {
    public static String titleInventoryBackGUI = ChatColor.DARK_RED + "" + ChatColor.BOLD + "        BACK TP POINTS";
    private static final SimpleDateFormat dateFormatter = new java.text.SimpleDateFormat("HH:mm:ss");

    private final BackLocationsManager locationsManager;

    public BackLocationsInventoryBuilder(BackLocationsManager locationsManager) {
        this.locationsManager = locationsManager;
    }

    public Inventory buildBackInventoryGUI (Player player) {
        Inventory inventory = Bukkit.createInventory(null, 54, titleInventoryBackGUI);
        List<BackLocation> backLocations = locationsManager.findAllLocationsByPlayer(player);

        if(backLocations == null){
            return inventory;
        }

        int numberOfItemsAdded = 0;
        for (BackLocation backLocation : backLocations) {
            if (numberOfItemsAdded <= 54) {
                updateBackLocationIcon(backLocation); //Setup the distance
                inventory.addItem(backLocation.getIcon());
            }
            numberOfItemsAdded++;
        }

        return inventory;
    }

    private void updateBackLocationIcon (BackLocation backLocation) {
        ItemStack icon = backLocation.getIcon();
        Player player = backLocation.getPlayer();
        Location backTPLocation = backLocation.getLocation();
        Location playerLocation = player.getLocation();

        ItemMeta meta = backLocation.getIcon().getItemMeta();
        List<String> lore = meta.getLore();

        if(player.getWorld() == backTPLocation.getWorld()){
            lore.add(1, ChatColor.GOLD + "Distance: " + roundDecimals(playerLocation.distance(backTPLocation), 0) + " blocks");
        }else{ // Not the same world, so we can remove the line of the distance
            if(lore.size() > 1){
                lore.remove(1);
            }
        }

        meta.setLore(lore);
        icon.setItemMeta(meta);
    }

    public ItemStack buildBackLocationIcon (BackLocation backLocation) {
        Location backTPLocation = backLocation.getLocation();
        ItemStack icon;

        if(isOnTheEnd(backTPLocation)){
            icon = new ItemStack(Material.ENDER_PEARL);
        }else if (isOnTheNether(backTPLocation)) {
            icon = new ItemStack(Material.NETHER_BRICK);
        }else{
            icon = new ItemStack(Material.GRASS_BLOCK);
        }

        ItemMeta itemMeta = icon.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "" + dateFormatter.format(backLocation.getDate()));
        List<String> lore = new ArrayList<>();

        lore.add(((int) backTPLocation.getX()) + " " + ((int) (backTPLocation.getY()) + " " + ((int) backTPLocation.getZ())));
        itemMeta.setLore(lore);
        icon.setItemMeta(itemMeta);

        return icon;
    }
}
