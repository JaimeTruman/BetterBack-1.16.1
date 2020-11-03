package back.listeners;


import back.objects.BackLocationsManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLeaveEvent implements Listener {
    private final BackLocationsManager locationsManager;

    public PlayerLeaveEvent(BackLocationsManager locationsManager) {
        this.locationsManager = locationsManager;
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        locationsManager.removeByPlayer(event.getPlayer());
    }
}
