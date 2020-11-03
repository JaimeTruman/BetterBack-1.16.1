package back.objects;

import back.main.Main;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;


public class BackLocation implements Comparable<BackLocation> {
    private static final BackLocationsManager locationManager = Main.getBackLocationManager();
    private static final BackLocationsInventoryBuilder backLocationInvBuilder = new BackLocationsInventoryBuilder(locationManager);

    private final Location location;
    private final Player player;
    private final ItemStack icon;
    private final Date date;

    public BackLocation(Player jugador, Location location) {
        this.player = jugador;
        this.location = location;
        this.date = new Date();

        this.icon = backLocationInvBuilder.buildBackLocationIcon(this);
        locationManager.addBackLocation(this);
    }

    @Override
    public int compareTo(BackLocation o) {
        return o.getDate().compareTo(this.getDate());
    }

    public Location getLocation() {
        return location;
    }

    public Player getPlayer() {
        return player;
    }

    public ItemStack getIcon() {
        return icon;
    }

    public Date getDate() {
        return date;
    }
}
