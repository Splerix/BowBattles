package me.splerix.bowbattle.Game.Game.Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.DisplaySlot;

import java.util.*;

public class ScoreboardManager {
    public Map<UUID, ScoreboardInfo> scoreboardInfo = new HashMap<>();
    int count = 0;
    private final List<UUID> displayBoardTo;
    private final int gameID;
    private final String mapName;
    public ScoreboardManager(int gameID, Set<UUID> uuids, String mapName) {
        displayBoardTo = new ArrayList<>();
        this.gameID = gameID;
        this.mapName = mapName;

        displayBoardTo.addAll(uuids);
        for (UUID uuid : displayBoardTo) {
            Player player  = Bukkit.getPlayer(uuid);
            scoreboardInfo.put(uuid, new ScoreboardInfo(gameID,  uuid, mapName));
            player.setScoreboard(scoreboardInfo.get(uuid).getGameBoard());
        }
        updateScoreboard();
    }

    public void updateScoreboard() {
        if (count == 4) count = 0;
        for (UUID uuid : displayBoardTo) {
            Player player = Bukkit.getPlayer(uuid);
            if (!(scoreboardInfo.containsKey(uuid))) {
                scoreboardInfo.put(uuid, new ScoreboardInfo(gameID,  uuid, mapName));
                player.setScoreboard(scoreboardInfo.get(uuid).getGameBoard());
            }
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
    public void onGameEnd() {
        for (UUID uuid : displayBoardTo) {
            removeBoardTo(uuid);
        }
    }

    public void onDisable() {
        onGameEnd();
    }

    public void removeBoardTo(UUID uuid) {
        displayBoardTo.remove(uuid);
        Player player = Bukkit.getPlayer(uuid);
        player.setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
    }
}
