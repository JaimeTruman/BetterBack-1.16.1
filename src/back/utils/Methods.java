package back.utils;

import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.inventory.ItemStack;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.stream.Stream;

public final class Methods {
    private Methods () {}

    public static boolean isOnTheOverlord (Location location) {
        return location.getWorld().getEnvironment() == World.Environment.NORMAL;
    }

    public static boolean isOnTheNether (Location location) {
        return location.getWorld().getEnvironment() == World.Environment.NETHER;
    }

    public static boolean isOnTheEnd (Location location) {
        return location.getWorld().getEnvironment() == World.Environment.THE_END;
    }

    public static boolean matchesItemsNames(ItemStack item, String... names) {
        String nameOfTheItem = item.getType().toString();

        return Stream.of(names).anyMatch(name -> name.equalsIgnoreCase(nameOfTheItem));
    }

    public static boolean areSameLocations(Location loc1, Location loc2) {
        return loc1.getX() == loc2.getX() &&
               loc1.getY() == loc2.getY() &&
               loc1.getZ() == loc2.getZ() &&
               loc1.getWorld() == loc2.getWorld();
    }

    public static double roundDecimals(double number, int numberOfDecimals) {
        BigDecimal rounded = new BigDecimal(number).setScale(numberOfDecimals, RoundingMode.HALF_EVEN);

        return rounded.doubleValue();
    }
}
