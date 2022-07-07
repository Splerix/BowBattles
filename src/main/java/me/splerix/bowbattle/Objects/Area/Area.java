package me.splerix.bowbattle.Objects.Area;


import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Area extends AreaTemplate {

    Spawn spawn = new Spawn();
    Deathmatch deathmatch = new Deathmatch();


    public boolean inSpawn(Player player) {
        return inSpawn(player.getLocation());
    }
    public boolean inSpawn(Location loc) {
        return spawn.inSpawn(loc);
    }
    public Location getSpawnLocation() {
        return spawn.getLoc();
    }
    public boolean inDeathmatch(Location loc) {
        return deathmatch.inDeathmatch(loc);
    }
    public boolean inDeathmatchSafezone(Player player) {
        return deathmatch.inDeathmatchSafeZone(player.getLocation());
    }
    public boolean inDeathmatchSafezone(Location loc) {
        return deathmatch.inDeathmatchSafeZone(loc);
    }
    public Location getDeathmatchLocation() {
        return deathmatch.getLoc();
    }
}

