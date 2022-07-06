package me.splerix.bowbattle.Objects.Area;


import org.bukkit.Location;
import org.bukkit.entity.Player;

public class AreaTemplate extends AreaCalculator{

    public me.splerix.bowbattle.BowBattle plugin = me.splerix.bowbattle.BowBattle.getPlugin(me.splerix.bowbattle.BowBattle.class);
    private double highX;
    private double highY;
    private double highZ;

    private double lowX;
    private double lowY;
    private double lowZ;

    public void readAreaConfig(String areaName) {
        plugin.getConfig().getConfigurationSection(areaName).getKeys(false).forEach(key -> {
            if (key.equalsIgnoreCase("high"))
                if (plugin.getConfig().getStringList(areaName + "." + key).size() == 3) {
                    int[] cords = new int[3];
                    for (String cord : plugin.getConfig().getStringList(areaName + "." + key)) {
                        if (cord.split(" ")[0].equals("x")) cords[0] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("y")) cords[1] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("z")) cords[2] = parseInt(cord.split(" ")[1]);
                    }
                    highX = cords[0];
                    highY = cords[1];
                    highZ = cords[2];
                }
            if (key.equalsIgnoreCase("low"))
                if (plugin.getConfig().getStringList(areaName + "." + key).size() == 3) {
                    int[] cords = new int[3];
                    for (String cord : plugin.getConfig().getStringList(areaName + "." + key)) {
                        if (cord.split(" ")[0].equals("x")) cords[0] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("y")) cords[1] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("z")) cords[2] = parseInt(cord.split(" ")[1]);
                    }
                    lowX = cords[0];
                    lowY = cords[1];
                    lowZ = cords[2];
                }
        });
    }

    public boolean inArea(Location loc) {
        return inArea(loc, highX, highY, highZ, lowX, lowY, lowZ);
    }
}

