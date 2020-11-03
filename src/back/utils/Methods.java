package back.utils;

import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.stream.Stream;

public final class Methods {
    private Methods () {}

    public static boolean isOnTheOverlord (Player player) {
        return player.getWorld().getEnvironment() == World.Environment.NORMAL;
    }

    public static boolean isOnTheNether (Player player) {
        return player.getWorld().getEnvironment() == World.Environment.NETHER;
    }

    public static boolean isOnTheEnd (Player player) {
        return player.getWorld().getEnvironment() == World.Environment.THE_END;
    }

    public static boolean matchesItemsNames(ItemStack item, String... names) {
        String nameOfTheItem = item.getType().toString();

        return Stream.of(names).anyMatch(name -> name.equalsIgnoreCase(nameOfTheItem));
    }
}
