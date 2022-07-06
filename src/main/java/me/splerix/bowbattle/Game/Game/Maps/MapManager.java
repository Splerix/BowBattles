package me.splerix.bowbattle.Game.Game.Maps;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.Random;

public class MapManager {
    Plugin plugin;
    MapReader mapReader;
    Random random = new Random();
    private String mapName = "NONE";
    GameMap gameMap;
    boolean overtime;
    boolean gameStarted;


    public MapManager (Plugin plugin) {
        this.plugin = plugin;
        mapReader = new MapReader(plugin);
        overtime = false;
        gameStarted = false;
    }

    public void onGameStart(@Nullable String forceMapName) {
        String[] mapList = mapReader.getMapList();
        try {
            gameMap = mapReader.getMapInfo(forceMapName);
            mapName = gameMap.getGameMapName();
        } catch (Exception e){
            gameMap = mapReader.getMapInfo(mapReader.getMapList()[(random.nextInt(mapList.length))]);

            mapName = gameMap.getGameMapName();
        }
        gameStarted = true;
    }
    public void initiateOvertime() {
        overtime = true;
        gameMap.getOvertimeActivatorLoc().getWorld().getBlockAt(gameMap.getOvertimeActivatorLoc()).setType(Material.REDSTONE_BLOCK);
    }
    public void onGameEnd() {
        if (overtime) gameMap.getOvertimeActivatorLoc().getWorld().getBlockAt(gameMap.getOvertimeActivatorLoc()).setType(Material.AIR);
    }

    public void onDisable() {
        if (gameStarted) onGameEnd();
    }

    public GameMap getGameMap() {
        return gameMap;
    }
    public String getMapName() {
        return mapName;
    }
}
