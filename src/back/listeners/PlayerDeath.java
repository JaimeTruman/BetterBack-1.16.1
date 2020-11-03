package back.listeners;

import back.objects.BackLocation;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerDeath implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        Location playerLocation = player.getLocation();

        if (BackLocation.estaRegistrada(playerLocation, player)) {
            return;
        }

        ItemStack icon = buildIconItem(player);

        BackLocation newBackLocation = new BackLocation(player.getName(), playerLocation, icon);
        BackLocation.localizaciones.add(newBackLocation);
    }

    private ItemStack buildIconItem (Player player) {
        ItemStack icon;
        if (isOnTheNether(player)) {
            icon = new ItemStack(Material.NETHER_BRICKS);
        } else if (isOnTheEnd(player)) {
            icon = new ItemStack(Material.ENDER_PEARL);
        } else {
            icon = new ItemStack(Material.GRASS_BLOCK);
        }

        return icon;
    }

    private boolean isOnTheNether (Player player) {
        return player.getWorld().getEnvironment() == World.Environment.NETHER;
    }

    private boolean isOnTheEnd (Player player) {
        return player.getWorld().getEnvironment() == World.Environment.THE_END;
    }
}
