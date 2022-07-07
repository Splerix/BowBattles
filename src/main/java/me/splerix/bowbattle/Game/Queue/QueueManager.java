package me.splerix.bowbattle.Game.Queue;

import me.splerix.bowbattle.Game.Game.GameManager;
import me.splerix.bowbattle.Objects.Area.Area;
import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import javax.annotation.Nullable;
import java.util.*;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class QueueManager {
    private final Plugin plugin;
    private final Area area = new Area();
    private final List<UUID> queueLine;
    private final QueueInventory queueInventory = new QueueInventory();
    private final Map<Integer, GameManager> games;
    public Map<UUID, Integer> getGameID;
    private final List<String> currentMaps;

    public QueueManager(Plugin plugin) {
        queueLine = new ArrayList<>();
        this.plugin = plugin;
        games = new HashMap<>();
        getGameID = new HashMap<>();
        currentMaps = new ArrayList<>();
    }
    public boolean addPlayerToQueue(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        if (player == null) return false;
        PlayerState playerState= playerInfo.get(uuid).getPlayerState();
        if (!(playerState == PlayerState.OUTGAME)) return false;
        if (playerInfo.get(uuid).isDevMode()) return false;
        if (!(area.inSpawn(player))) return false;
        if (queueLine.size() >= 4) return false;
        playerInfo.get(uuid).setPlayerState(PlayerState.QUEUED);
        queueLine.add(uuid);
        player.getInventory().setItem(8, queueInventory.getLeaveQueueItem());
        return true;
    }
    public boolean removePlayerFromQueue(Player player) {
        return removePlayerFromQueue(player.getUniqueId());
    }
    public boolean removePlayerFromQueue(UUID uuid) {
        if (!(playerInfo.get(uuid).getPlayerState()  == PlayerState.QUEUED)) return false;
        resetQueuedPlayer(uuid);
        queueLine.remove(uuid);
        return true;
    }

    private void resetQueuedPlayer(UUID uuid) {
        Player player = Bukkit.getPlayer(uuid);
        playerInfo.get(uuid).setPlayerState(PlayerState.OUTGAME);
        player.getInventory().remove(queueInventory.getLeaveQueueItem());
    }
    public void startGame(@Nullable String forceMap) {
        if (!(this.queueLine.size() > 0 && this.queueLine.size() <= 4)) return;
        int gameId = getGameId();
        List<UUID> queueLine = this.queueLine;
        for (UUID uuid : queueLine) {
            resetQueuedPlayer(uuid);
            getGameID.put(uuid, gameId);
        }
        games.put(gameId, new GameManager(plugin, gameId));
        games.get(gameId).startGame(forceMap, (ArrayList<UUID>) queueLine);
    }

    public void gameEnd(int gameId) {
        GameManager gameManager = games.get(gameId);
        games.remove(gameId);
        currentMaps.remove(gameManager.getMapManager().getMapName());
    }

    public void removePlayerFromGames(UUID uuid) {
        games.remove(uuid);
    }

    public void onDisable() {
        for(UUID uuid : queueLine) {
            resetQueuedPlayer(uuid);
        }
        currentMaps.clear();
        queueLine.clear();
        getGameID.clear();
        for (int gameId : games.keySet()) {
            getGameManager(gameId).onDisable();
        }
    }

    public GameManager getGameManager(int gameId) {
        return games.get(gameId);
    }
    public List<UUID> getQueueLine() {
        return queueLine;
    }
    public boolean checkGameId(int gameId) {
        return games.containsKey(gameId);
    }

    public void clearQueueLine() {
        queueLine.clear();
    }
    public void addCurrentMap(String mapName) {
        currentMaps.add(mapName);
    }

    private int getGameId() {
        Random r = new Random();
        int gameId = r.nextInt(4);
        if (games.containsKey(gameId)) gameId = getGameId();
        return gameId;
    }


}
