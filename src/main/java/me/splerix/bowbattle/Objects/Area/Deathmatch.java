package me.splerix.bowbattle.Objects.Area;

import org.bukkit.Bukkit;
import org.bukkit.Location;

public class Deathmatch extends AreaTemplate{

    private Location loc;


    private double deathmatchTPX;
    private double deathmatchTPY;
    private double deathmatchTPZ;

    private double deathmatchSafeZoneHighX;
    private double deathmatchSafeZoneHighY;
    private double deathmatchSafeZoneHighZ;

    private double deathmatchSafeZoneLowX;
    private double deathmatchSafeZoneLowY;
    private double deathmatchSafeZoneLowZ;

    Deathmatch() {
        readAreaConfig("deathmatch");
        readExtraDeathmatchCords();
        loc  = new Location((Bukkit.getWorld("world")), deathmatchTPX, deathmatchTPY, deathmatchTPZ, 0, 0);
    }
    public boolean inDeathmatch(Location loc) {
        return inArea(loc);
    }
    public boolean inDeathmatchSafeZone(Location loc) {
        return inArea(loc, deathmatchSafeZoneHighX, deathmatchSafeZoneHighY, deathmatchSafeZoneHighZ,
                deathmatchSafeZoneLowX, deathmatchSafeZoneLowY, deathmatchSafeZoneLowZ);
    }
    public Location getLoc() {
        return loc;
    }

    private void readExtraDeathmatchCords() {
        plugin.getConfig().getConfigurationSection("deathmatch").getKeys(false).forEach(key -> {
            if (key.equalsIgnoreCase("warp"))
                if (plugin.getConfig().getStringList("deathmatch." + key).size() == 3) {
                    double[] cords = new double[3];
                    for (String cord : plugin.getConfig().getStringList("deathmatch." + key)) {
                        if (cord.split(" ")[0].equals("x")) cords[0] = parseDouble(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("y")) cords[1] = parseDouble(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("z")) cords[2] = parseDouble(cord.split(" ")[1]);
                    }
                    deathmatchTPX = cords[0];
                    deathmatchTPY = cords[1];
                    deathmatchTPZ = cords[2];
                }
            if (key.equalsIgnoreCase("safezonehigh"))
                if (plugin.getConfig().getStringList("deathmatch." + key).size() == 3) {
                    int[] cords = new int[3];
                    for (String cord : plugin.getConfig().getStringList("deathmatch." + key)) {
                        if (cord.split(" ")[0].equals("x")) cords[0] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("y")) cords[1] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("z")) cords[2] = parseInt(cord.split(" ")[1]);
                    }
                    deathmatchSafeZoneHighX = cords[0];
                    deathmatchSafeZoneHighY = cords[1];
                    deathmatchSafeZoneHighZ = cords[2];
                }
            if (key.equalsIgnoreCase("safezonelow"))
                if (plugin.getConfig().getStringList("deathmatch." + key).size() == 3) {
                    int[] cords = new int[3];
                    for (String cord : plugin.getConfig().getStringList("deathmatch." + key)) {
                        if (cord.split(" ")[0].equals("x")) cords[0] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("y")) cords[1] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("z")) cords[2] = parseInt(cord.split(" ")[1]);
                    }
                    deathmatchSafeZoneLowX = cords[0];
                    deathmatchSafeZoneLowY = cords[1];
                    deathmatchSafeZoneLowZ = cords[2];
                }
        });
    }
}
