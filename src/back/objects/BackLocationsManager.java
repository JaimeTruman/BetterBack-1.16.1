package back.objects;

import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.*;

import static back.utils.Methods.*;

public final class BackLocationsManager {
    private final Map<String, List<BackLocation>> backLocations = new HashMap<>(); // Player - List backlocatoins

    public void addBackLocation (BackLocation backLocation) {
        List<BackLocation> listBackLocationsPlayer = backLocations.get(backLocation.getPlayer().getName());
        String playerName = backLocation.getPlayer().getName();

        if(listBackLocationsPlayer == null){
            List<BackLocation> newBackLocationList = new ArrayList<>();
            newBackLocationList.add(backLocation);

            backLocations.put(playerName, newBackLocationList);
        }else{
            listBackLocationsPlayer.add(backLocation);

            backLocations.replace(playerName, listBackLocationsPlayer);
        }
    }

    public boolean isRegistered (Player player, Location location) {
        List<BackLocation> listBackLocationsPlayer = backLocations.get(player.getName());

        if(listBackLocationsPlayer == null){
            return false;
        }else{
            return listBackLocationsPlayer.stream()
                    .anyMatch(backLocationIte -> areSameLocations(backLocationIte.getLocation(), location));
        }
    }

    public void removeByPlayer (Player player) {
        backLocations.remove(player.getName());
    }

    public List<BackLocation> findAllLocationsByPlayer (Player player) {
        List<BackLocation> backLocationList = backLocations.get(player.getName());

        if(backLocationList != null){
            Collections.sort(backLocationList);

            return backLocationList;
        }else{
            return new ArrayList<>(); //Empty list
        }
    }
}
