package me.splerix.bowbattle.Game.Game.Scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.*;

public class ScoreboardManager {
    public Map<UUID, ScoreboardInfo> scoreboardInfo = new HashMap<>();
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
        for (UUID uuid : displayBoardTo) {
            Player player = Bukkit.getPlayer(uuid);
            if (!(scoreboardInfo.containsKey(uuid))) {
                scoreboardInfo.put(uuid, new ScoreboardInfo(gameID,  uuid, mapName));
                player.setScoreboard(scoreboardInfo.get(uuid).getGameBoard());
            }
            player.setScoreboard(scoreboardInfo.get(uuid).getGameBoard());
        }
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
