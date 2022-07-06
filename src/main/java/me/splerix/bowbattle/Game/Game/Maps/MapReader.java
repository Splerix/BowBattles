package me.splerix.bowbattle.Game.Game.Maps;

import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;

public class MapReader {
    private Plugin plugin;
    private String[] mapList;

    MapReader(Plugin plugin) {
        this.plugin = plugin;
        mapList = new String[plugin.getConfig().getStringList("maps.names").size()];

        int i = 0;
        for (String mapName : plugin.getConfig().getStringList("maps.names")) {
            mapList[i] = mapName;
            i++;
        }
    }

    public String[] getMapList() {
        return mapList;
    }

    public GameMap getMapInfo(String mapName) {
        //Read Map Area
        int[][] mapArea;
        mapArea = areaReader("maps." + mapName + ".");
        //Read Team Areas
        int[][] redArea;
        redArea = areaReader("maps." + mapName + ".teams.red.");
        int[][] greenArea;
        greenArea = areaReader("maps." + mapName + ".teams.green.");
        int[][] blueArea;
        blueArea = areaReader("maps." + mapName + ".teams.blue.");
        int[][] yellowArea;
        yellowArea = areaReader("maps." + mapName + ".teams.yellow.");
        //Read Warps
        double[] redWarp;
        redWarp = warpReader("maps." + mapName + ".teams.red");
        double[] greenWarp;
        greenWarp = warpReader("maps." + mapName + ".teams.green");
        double[] blueWarp;
        blueWarp = warpReader("maps." + mapName + ".teams.blue");
        double[] yellowWarp;
        yellowWarp = warpReader("maps." + mapName + ".teams.yellow");
        //Read Spectator Warp
        double[] spectatorWarp;
        spectatorWarp = warpReader("maps." + mapName + ".spectator");
        //Read Overtime activator
        int[] overtimeActivator;
        overtimeActivator = blockLocationRead("maps." + mapName + ".overtime");

        return new GameMap(mapName, mapArea, redArea, redWarp, greenArea, greenWarp, blueArea, blueWarp, yellowArea, yellowWarp, spectatorWarp, overtimeActivator);
    }

    private int[][] areaReader(String path) {
        //[0][0] is low nums, [0][1] is high nums
        int[][] area = new int[3][2];

        plugin.getConfig().getConfigurationSection(path).getKeys(false).forEach(key -> {
            if (key.equalsIgnoreCase("high"))
                if (plugin.getConfig().getStringList(path + "." + key).size() == 3) {
                    int[] cords = new int[3];
                    for (String cord : plugin.getConfig().getStringList(path + "." + key)) {
                        if (cord.split(" ")[0].equals("x")) cords[0] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("y")) cords[1] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("z")) cords[2] = parseInt(cord.split(" ")[1]);
                    }
                    area[0][1] = cords[0];
                    area[1][1] = cords[1];
                    area[2][1] = cords[2];
                }
            if (key.equalsIgnoreCase("low"))
                if (plugin.getConfig().getStringList(path + "." + key).size() == 3) {
                    int[] cords = new int[3];
                    for (String cord : plugin.getConfig().getStringList(path + "." + key)) {
                        if (cord.split(" ")[0].equals("x")) cords[0] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("y")) cords[1] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("z")) cords[2] = parseInt(cord.split(" ")[1]);
                    }
                    area[0][0] = cords[0];
                    area[1][0] = cords[1];
                    area[2][0] = cords[2];
                }
        });
        return area;
    }
    private double[] warpReader(String path) {

        double[] warp = new double[3];

        plugin.getConfig().getConfigurationSection(path).getKeys(false).forEach(key -> {
            if (key.equalsIgnoreCase("warp"))
                if (plugin.getConfig().getStringList(path + "." + key).size() == 3) {
                    double[] cords = new double[3];
                    for (String cord : plugin.getConfig().getStringList(path + "." + key)) {
                        if (cord.split(" ")[0].equals("x")) cords[0] = parseDouble(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("y")) cords[1] = parseDouble(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("z")) cords[2] = parseDouble(cord.split(" ")[1]);
                    }
                    warp[0] = cords[0];
                    warp[1] = cords[1];
                    warp[2] = cords[2];
                }
        });
        return warp;
    }
    private int[] blockLocationRead(String path) {
        int[] blockLocation = new int[3];

        plugin.getConfig().getConfigurationSection(path).getKeys(false).forEach(key -> {
            if (key.equalsIgnoreCase("blocklocation"))
                if (plugin.getConfig().getStringList(path + "." + key).size() == 3) {
                    int[] cords = new int[3];
                    for (String cord : plugin.getConfig().getStringList(path + "." + key)) {
                        if (cord.split(" ")[0].equals("x")) cords[0] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("y")) cords[1] = parseInt(cord.split(" ")[1]);
                        if (cord.split(" ")[0].equals("z")) cords[2] = parseInt(cord.split(" ")[1]);
                    }
                    blockLocation[0] = cords[0];
                    blockLocation[1] = cords[1];
                    blockLocation[2] = cords[2];
                }
        });
        return blockLocation;
    }

    public int parseInt(String  num) {
        try {
            return Integer.parseInt(num);
        } catch (Exception e) {
            return 0;
        }
    }
    public double parseDouble(String doub) {
        try {
            return Double.parseDouble(doub);
        } catch (Exception e) {
            return 0;
        }
    }
}
