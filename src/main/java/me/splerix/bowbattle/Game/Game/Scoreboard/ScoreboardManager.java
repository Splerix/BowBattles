package me.splerix.bowbattle.Game.Game.Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.*;

public class ScoreboardManager {
    public Map<UUID, ScoreboardInfo> scoreboardInfo = new HashMap<UUID, ScoreboardInfo>();
    private int runnableID;
    private Plugin plugin;
    private List<UUID> displayBoardTo;
    private int gameID;
    private String mapName;
    public ScoreboardManager(int gameID, Set<UUID> uuids, String mapName, Plugin plugin) {
        displayBoardTo = new ArrayList<UUID>();
        this.plugin = plugin;
        this.gameID = gameID;
        this.mapName = mapName;

        for (UUID uuid : uuids) {
            displayBoardTo.add(uuid);
        }
        for (UUID uuid : displayBoardTo) {
            scoreboardInfo.put(uuid, new ScoreboardInfo(gameID,  uuid, mapName));
            Bukkit.getPlayer(uuid).setScoreboard(scoreboardInfo.get(uuid).getGameBoard());
        }
        start();
    }

    private void start() {
        runnableID = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
            int count = 0;

            @Override
            public void run() {
                if (count == 4) count = 0;
                for (UUID uuid : displayBoardTo) {
                    if (!(scoreboardInfo.containsKey(uuid))) {
                        scoreboardInfo.put(uuid, new ScoreboardInfo(gameID,  uuid, mapName));
                        Bukkit.getPlayer(uuid).setScoreboard(scoreboardInfo.get(uuid).getGameBoard());
                    }
                    Player player = Bukkit.getPlayer(uuid);
                    switch (count) {
                        case 0:
                            player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.DARK_PURPLE + "BowBattle Game");
                            break;
                        case 1:
                            player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.AQUA + "BowBattle Game");
                            break;
                        case 2:
                            player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.DARK_BLUE + "BowBattle Game");
                            break;
                        case 3:
                            player.getScoreboard().getObjective(DisplaySlot.SIDEBAR).setDisplayName(ChatColor.GOLD + "BowBattle Game");
                            player.setScoreboard(scoreboardInfo.get(uuid).getGameBoard());
                            break;
                    }
                }
                count++;
            }
        }, 1, 5);
    }


    public void onGameEnd() {
        Bukkit.getScheduler().cancelTask(runnableID);
        try {
            for (UUID uuid : displayBoardTo) {
                try{
                    removeBoardTo(uuid);
                } catch (Exception e){

                }
            }
        } catch (Exception e){}
    }

    public void onDisable() {
        onGameEnd();
    }

    public void removeBoardTo(UUID uuid) {
        if (displayBoardTo.contains(uuid)) displayBoardTo.remove(uuid);
        Player player = Bukkit.getPlayer(uuid);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
