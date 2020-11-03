package back.main;

import back.listeners.*;
import back.objects.BackLocationsInventoryBuilder;
import back.objects.BackLocationsManager;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class
 */
public class Main extends JavaPlugin {
    private static final BackLocationsManager locationsManager = new BackLocationsManager();
    private static final BackLocationsInventoryBuilder inventoryBuilder = new BackLocationsInventoryBuilder(locationsManager);

    @Override
    public void onEnable() {
        setUpCommands();
        setUpListeners();
    }

    private void setUpListeners () {
        getServer().getPluginManager().registerEvents(new PlayerLeaveEvent(locationsManager), this);
        getServer().getPluginManager().registerEvents(new PlayerDeath(locationsManager), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleport(locationsManager), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryClick(), this);
    }

    private void setUpCommands() {
        getCommand("back").setExecutor(new PlayerCommand(inventoryBuilder));
    }

    public static BackLocationsManager getBackLocationManager () {
        return locationsManager;
    }
}
