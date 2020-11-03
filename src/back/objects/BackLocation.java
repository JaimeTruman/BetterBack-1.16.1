package back.objects;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

public class BackLocation implements Comparable<BackLocation> {
    public static ArrayList<BackLocation> localizaciones = new ArrayList<>();
    public static String titulo = ChatColor.DARK_RED + "" + ChatColor.BOLD + "        PUNTOS DE TP";
    private static SimpleDateFormat sdf = new java.text.SimpleDateFormat("HH:mm:ss");
    private Location location;
    private String jugador;
    private ItemStack icono;
    private Date hora;

    public BackLocation(String jugador, Location location, ItemStack icono) {
        hora = new Date();

        this.jugador = jugador;
        this.location = location;
        this.icono = icono;
    }

    public void contruirItem() {
        Player player = Bukkit.getPlayer(jugador);

        ItemMeta itemMeta = icono.getItemMeta();
        itemMeta.setDisplayName(ChatColor.GREEN + "" + ChatColor.BOLD + "" + sdf.format(hora));
        ArrayList<String> lore = new ArrayList<>();

        World mundoActual = player.getWorld();
        World mundoTp = location.getWorld();

        if (mundoActual == mundoTp) {
            lore.add(ChatColor.GOLD + "Distancia: " + redondeoDecimales(Bukkit.getPlayer(jugador).getLocation().distance(location), 0) + " bloques");
        }
        lore.add(((int) (location.getX())) + " " + ((int) (location.getY()) + " " + ((int) location.getZ())));
        itemMeta.setLore(lore);
        icono.setItemMeta(itemMeta);
    }

    @Override
    public int compareTo(BackLocation o) {
        return o.getHora().compareTo(this.getHora());
    }

    public static boolean estaRegistrada(Location location, Player player) {
        Location playerLoc = player.getLocation();
        Location loc;

        for (BackLocation localizacion : localizaciones) {
            loc = localizacion.getLocation();
            if (localizacion.getJugador().equalsIgnoreCase(player.getName()) && loc.getWorld() == player.getWorld()) {
                if (loc.getX() == playerLoc.getX() && loc.getY() == playerLoc.getY() && loc.getZ() == playerLoc.getZ()) {
                    return true;
                }
            }
        }
        return false;
    }

    public static void borrarLocalizaciones (Player player) {
        localizaciones.removeIf( (jug) -> jug.getJugador().equalsIgnoreCase(player.getName()) );
    }

    public static void mostrarLocalizaciones(Player player) {
        Inventory inventario = Bukkit.createInventory(null, 54, titulo);
        Set<BackLocation> locOrdenada = new TreeSet<>();

        for (BackLocation localizacion : localizaciones) {
            if (localizacion.getJugador().equalsIgnoreCase(player.getName())) {
                localizacion.contruirItem();
                locOrdenada.add(localizacion);
            }
        }
        int pos = 0;
        for (BackLocation localizacion : locOrdenada) {
            if (pos <= 54) {
                inventario.addItem(localizacion.getIcono());
            } else {
                localizaciones.remove(localizacion);
            }
            pos++;
        }
        player.openInventory(inventario);
    }

    private double redondeoDecimales(double numero, int numeroDecimales) {
        BigDecimal redondeado = new BigDecimal(numero).setScale(numeroDecimales, RoundingMode.HALF_EVEN);
        return redondeado.doubleValue();
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public void setJugador(String jugador) {
        this.jugador = jugador;
    }

    public void setIcono(ItemStack icono) {
        this.icono = icono;
    }

    public Location getLocation() {
        return location;
    }

    public String getJugador() {
        return jugador;
    }

    public ItemStack getIcono() {
        return icono;
    }

    public Date getHora() {
        return hora;
    }

    public static void performBackTP (Player player, World world, ItemStack item) {
        ItemMeta itemMeta = item.getItemMeta();
        String[] cords;

        if (world == player.getWorld()) {
            cords = itemMeta.getLore().get(1).split(" ");
        } else {
            cords = itemMeta.getLore().get(0).split(" ");
        }
        int x = Integer.parseInt(cords[0]);
        int y = Integer.parseInt(cords[1]);
        int z = Integer.parseInt(cords[2]);

        Location toTp = new Location(world, x, y, z);
        player.teleport(toTp);
    }
}
