package me.splerix.bowbattle.Game.Game.Maps;

import me.splerix.bowbattle.Game.Game.PlayerGameInfo.PlayerGameInfo;
import me.splerix.bowbattle.Game.Game.PlayerGameInfo.PlayerTeam;
import me.splerix.bowbattle.Objects.Area.AreaCalculator;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class GameMap extends AreaCalculator {

    //Map
    final private String gameMapName;

    //Overtime
    final private Location overtimeActivatorLoc;

    //Areas
    final private int[][] mapArea;
    final private int[][] redArea;
    final private int[][] greenArea;
    final private int[][] blueArea;
    final private int[][] yellowArea;

    //Spawns
    final private Location redPlayerSpawn;
    final private Location bluePlayerSpawn;
    final private Location greenPlayerSpawn;
    final private Location yellowPlayerSpawn;
    final private Location spectatorSpawn;

    GameMap(String gameMapName, int[][] mapArea, int[][] redArea, double[] redWarp, int[][] greenArea, double[] greenWarp, int[][] blueArea,
            double[] blueWarp, int[][] yellowArea, double[] yellowWarp, double[] spectatorWarp,int[] overtimeActivatorLocArray){
        this.gameMapName = gameMapName;
        this.mapArea = mapArea;
        this.redArea = redArea;
        this.greenArea = greenArea;
        this.blueArea = blueArea;
        this.yellowArea = yellowArea;

        overtimeActivatorLoc = arrayToWarpLoc(overtimeActivatorLocArray);

        redPlayerSpawn = arrayToWarpLoc(redWarp);
        bluePlayerSpawn = arrayToWarpLoc(blueWarp);
        greenPlayerSpawn = arrayToWarpLoc(greenWarp);
        yellowPlayerSpawn = arrayToWarpLoc(yellowWarp);
        spectatorSpawn = arrayToWarpLoc(spectatorWarp);
    }

    //Area Checkers

    public boolean inOwnArea(Player player, PlayerGameInfo playerGameInfo) {
        PlayerTeam playerTeam = playerGameInfo.getTeam();
        switch (playerTeam) {
            case RED:
                return inRedArea(player);
            case BLUE:
                return inBlueArea(player);
            case GREEN:
                return inGreenArea(player);
            case YELLOW:
                return inYellowArea(player);
        }
        return false;
    }
    public boolean inMap(Location loc) {
        return inCordArea(loc, mapArea);
    }
    public boolean inRedArea(Player player) {
        return inRedArea(player.getLocation());
    }
    public boolean inRedArea(Location loc) {
        return inCordArea(loc, redArea);
    }
    public boolean inBlueArea(Player player) {
        return inBlueArea(player.getLocation());
    }
    public boolean inBlueArea(Location loc) {
        return inCordArea(loc, blueArea);
    }
    public boolean inGreenArea(Player player) {
        return inGreenArea(player.getLocation());
    }
    public boolean inGreenArea(Location loc) {
        return inCordArea(loc, greenArea);
    }
    public boolean inYellowArea(Player player) {
        return inYellowArea(player.getLocation());
    }
    public boolean inYellowArea(Location loc) {
        return inCordArea(loc, yellowArea);
    }

    //Getters
    public String getGameMapName() {
        return gameMapName;
    }
    public Location getOvertimeActivatorLoc() {
        return overtimeActivatorLoc;
    }
    public Location getRedPlayerSpawn() {
        return redPlayerSpawn;
    }
    public Location getBluePlayerSpawn() {
        return bluePlayerSpawn;
    }
    public Location getGreenPlayerSpawn() {
        return greenPlayerSpawn;
    }
    public Location getYellowPlayerSpawn() {
        return yellowPlayerSpawn;
    }
    public Location getSpectatorSpawn() {
        return spectatorSpawn;
    }

    //Maths
    private boolean inCordArea(Location loc, int[][] cords) {
        //[0][0] is low nums, [0][1] is high nums
        int lowX;
        int lowY;
        int lowZ;
        int highX;
        int highY;
        int highZ;

        lowX = cords[0][0];
        lowY = cords[1][0];
        lowZ = cords[2][0];

        highX = cords[0][1];
        highY = cords[1][1];
        highZ = cords[2][1];

        return inArea(loc, highX, highY, highZ, lowX, lowY, lowZ);
    }
    private Location arrayToWarpLoc(int[] cord) {
        Location loc;
        int x;
        int y;
        int z;

        x = cord[0];
        y = cord[1];
        z = cord[2];

        loc  = new Location((Bukkit.getWorld("world")), x, y, z);
        return loc;
    }
    private Location arrayToWarpLoc(double[] cord) {
        Location loc;
        double x;
        double y;
        double z;

        x = cord[0];
        y = cord[1];
        z = cord[2];

        loc  = new Location((Bukkit.getWorld("world")), x, y, z);
        return loc;
    }
}