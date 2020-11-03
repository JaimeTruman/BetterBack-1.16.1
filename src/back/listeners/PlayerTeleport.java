package back.listeners;

import back.objects.BackLocation;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class PlayerTeleport implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        if (e.getCause().name().equalsIgnoreCase("UNKNOWN")) {
            return;
        }
        Player player = e.getPlayer();
        ItemStack icono;

        if (BackLocation.estaRegistrada(player.getLocation(), player)) {
            return;
        }

        if (player.getWorld().getEnvironment() == World.Environment.NETHER) {
            icono = new ItemStack(Material.NETHER_BRICKS);
        } else if (player.getWorld().getEnvironment() == World.Environment.THE_END) {
            icono = new ItemStack(Material.ENDER_PEARL);
        } else {
            icono = new ItemStack(Material.GRASS_BLOCK);
        }
        BackLocation localizacion = new BackLocation(player.getName(), player.getLocation(), icono);
        BackLocation.localizaciones.add(localizacion);
    }
}
