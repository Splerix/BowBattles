package me.splerix.bowbattle.Game.Game.Scoreboard;

import me.splerix.bowbattle.Game.Game.GameManager;
import me.splerix.bowbattle.Game.Game.PlayerGameInfo.PlayerTeam;
import me.splerix.bowbattle.Objects.TimeFormat;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Score;
import org.bukkit.scoreboard.Scoreboard;

import java.util.UUID;

import static me.splerix.bowbattle.Game.Manager.queueManager;

public class ScoreboardInfo {
    private int gameID;
    private UUID uuid;
    private String mapFormalName;
    Scoreboard board;

    private Score row1;
    private Score row2;
    private Score row3;
    private Score row4;
    private Score row5;
    private Score row6;
    private Score row7;
    private Score row8;
    private Score row9;
    private Score row10;
    private Score row11;

    private TimeFormat timeFormat = new TimeFormat();

    ScoreboardInfo(int gameID, UUID uuid, String mapName) {
        gatherInfo(gameID,uuid,mapName);
    }

    public Scoreboard getGameBoard() {
        return updateRows();
    }

    private Scoreboard updateRows() {

        Objective obj = gatherInfo(gameID,uuid,mapFormalName);
        GameManager gameManager = queueManager.getGameManager(gameID);

        int redLifes = -1;
        int blueLifes = -1;
        int yellowLifes = -1;
        int greenLifes = -1;
        int kills = gameManager.getPlayerGameInfo().get(uuid).getKills();

        UUID uuid;
        int seconds = gameManager.getTimeLeft();
        String nextEvent = getNextEventFormal(gameManager);

        if (!(teamChecker(PlayerTeam.RED))) redLifes = 0;
        if (!(teamChecker(PlayerTeam.BLUE))) blueLifes = 0;
        if (!(teamChecker(PlayerTeam.GREEN))) greenLifes = 0;
        if (!(teamChecker(PlayerTeam.YELLOW))) yellowLifes = 0;
        if (redLifes == -1) {
            if (teamChecker(PlayerTeam.RED)) {
                uuid = gameManager.teamToUUID.get(PlayerTeam.RED);
                redLifes = gameManager.getPlayerGameInfo().get(uuid).getLifes();
            }
        }
        if (blueLifes == -1) {
            if (teamChecker(PlayerTeam.BLUE)) {
                uuid = gameManager.teamToUUID.get(PlayerTeam.BLUE);
                blueLifes = gameManager.getPlayerGameInfo().get(uuid).getLifes();
            }
        }
        if (greenLifes == -1) {
            if (teamChecker(PlayerTeam.GREEN)) {
                uuid = gameManager.teamToUUID.get(PlayerTeam.GREEN);
                greenLifes = gameManager.getPlayerGameInfo().get(uuid).getLifes();
            }
        }
        if (yellowLifes == -1) {
            if (teamChecker(PlayerTeam.YELLOW)) {
                uuid = gameManager.teamToUUID.get(PlayerTeam.YELLOW);
                yellowLifes = gameManager.getPlayerGameInfo().get(uuid).getLifes();
            }
        }

        row1 = obj.getScore(ChatColor.GRAY + "Game: " + gameID);
        row1.setScore(10);
        row2 = obj.getScore(ChatColor.GOLD + "Map: " + ChatColor.LIGHT_PURPLE + "" + ChatColor.BOLD + mapFormalName);
        row2.setScore(9);
        row3 = obj.getScore(ChatColor.DARK_AQUA + "Time Until " + nextEvent + ChatColor.LIGHT_PURPLE + timeFormat.getFormatedTime(seconds));
        row3.setScore(8);
        row4 = obj.getScore( ChatColor.GRAY + "" + ChatColor.MAGIC + "                  .");
        row4.setScore(7);
        row5 = obj.getScore(ChatColor.RED + "Red Lifes: " + ChatColor.AQUA + getFormalLifes(redLifes));
        row5.setScore(6);
        row6 = obj.getScore(ChatColor.GREEN + "Green Lifes: " + ChatColor.AQUA + getFormalLifes(greenLifes));
        row6.setScore(5);
        row7 = obj.getScore(ChatColor.BLUE + "Blue Lifes: " + ChatColor.AQUA + getFormalLifes(blueLifes));
        row7.setScore(4);
        row8 = obj.getScore(ChatColor.YELLOW + "Yellow Lifes: " + ChatColor.AQUA + getFormalLifes(yellowLifes));
        row8.setScore(3);
        row9 = obj.getScore( ChatColor.GRAY + "" + ChatColor.MAGIC + "                 .");
        row9.setScore(2);
        row10 = obj.getScore(ChatColor.GOLD + "Kills: " + ChatColor.LIGHT_PURPLE + kills);
        row10.setScore(1);

        return board;
    }

    private Objective gatherInfo(int gameID, UUID uuid, String mapName) {
        this.gameID = gameID;
        this.uuid = uuid;
        mapFormalName = mapName.substring(0, 1).toUpperCase() + mapName.substring(1);
        board = Bukkit.getScoreboardManager().getNewScoreboard();
        Objective obj;

        obj = board.registerNewObjective(gameID + "", "dummy", ChatColor.DARK_PURPLE + "BowBattle Game");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        return obj;
    }
    private String getFormalLifes(int lifes) {
        if (lifes == 0) {
            return "Eliminated";
        }
        return "" + lifes;
    }
    private Boolean teamChecker(PlayerTeam team) {
        return queueManager.getGameManager(gameID).teamToUUID.containsKey(team);
    }
    private String getNextEventFormal(GameManager gameManager) {
        if (!(gameManager.isOvertime())) {
            return ChatColor.RED + "Overtime ";
        } else {
            return ChatColor.DARK_RED + "Game End ";
        }
    }
}
