package back.main;

import back.listeners.*;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Main class
 */
public class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        setUpCommands();
        setUpListeners();
    }

    private void setUpListeners () {
        getServer().getPluginManager().registerEvents(new PlayerDeath(), this);
        getServer().getPluginManager().registerEvents(new PlayerTeleport(), this);
        getServer().getPluginManager().registerEvents(new PlayerInventoryClick(), this);
        getServer().getPluginManager().registerEvents(new PlayerLeaveEvent(), this);
    }

    private void setUpCommands() {
        getCommand("back").setExecutor(new PlayerCommand());
    }
}
