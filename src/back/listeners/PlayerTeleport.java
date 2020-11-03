package back.listeners;

import back.objects.BackLocation;
import back.objects.BackLocationsManager;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerTeleport implements Listener {
    private final BackLocationsManager locationsManager;

    public PlayerTeleport(BackLocationsManager locationsManager) {
        this.locationsManager = locationsManager;
    }

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent event) {
        if (isUnknownCause(event)) {
            return;
        }
        Player player = event.getPlayer();

        if(locationsManager.isRegistered(player, player.getLocation())){
            return;
        }

        BackLocation newBackLocation = new BackLocation(player, player.getLocation());
    }

    private boolean isUnknownCause (PlayerTeleportEvent event){
        return event.getCause().name().equalsIgnoreCase("UNKNOWN"); // Unknown cases such as lag, bugs etc
    }
}
