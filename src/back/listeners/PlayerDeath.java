package back.listeners;

import back.objects.BackLocation;
import back.objects.BackLocationsManager;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeath implements Listener {
    private BackLocationsManager locationsManager;

    public PlayerDeath(BackLocationsManager locationsManager) {
        this.locationsManager = locationsManager;
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Location playerLocation = player.getLocation();

        if (locationsManager.isRegistered(player, playerLocation)) {
            return;
        }

        BackLocation newBackLocation = new BackLocation(player, playerLocation);
    }
}
