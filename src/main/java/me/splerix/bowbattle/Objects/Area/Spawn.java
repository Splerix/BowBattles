package me.splerix.bowbattle.Objects.Area;


import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Spawn extends AreaTemplate {
    private final Location loc;
    private double spawnX;
    private double spawnY;
    private double spawnZ;

    public Spawn() {
        readAreaConfig("spawn");
        readSpawnCords();
        loc  = new Location((Bukkit.getWorld("world")), spawnX, spawnY, spawnZ, 270, 0);
    }
    public boolean inSpawn(Location loc) {
        return inArea(loc);
    }

    public Location getLoc() {
        return loc;
    }

    private void readSpawnCords() {
        plugin.getConfig().getConfigurationSection("spawn").getKeys(false).forEach(key -> {
            if (key.equalsIgnoreCase("warp"))
                if (plugin.getConfig().getStringList("spawn." + key).size() == 3) {
                    double[] cords = new double[3];
                    for (String cord : plugin.getConfig().getStringList("spawn." + key)) {
                        if (cord.split(" ")[0].equals("x")) cords[0] = parseDouble(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("y")) cords[1] = parseDouble(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("z")) cords[2] = parseDouble(cord.split(" ")[1]);
                    }
                    spawnX = cords[0];
                    spawnY = cords[1];
                    spawnZ = cords[2];
                }
        });
    }
}