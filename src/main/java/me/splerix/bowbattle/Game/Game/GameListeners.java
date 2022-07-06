package me.splerix.bowbattle.Game.Game;

import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

import static me.splerix.bowbattle.Game.Manager.queueManager;
import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class GameListeners implements Listener {
    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        UUID uuid = player.getUniqueId();
        if (!(playerInfo.get(uuid).getPlayerState() == PlayerState.INGAME)) return;
        if (!(queueManager.getGameID.containsKey(uuid))) return;
        int gameId = queueManager.getGameID.get(uuid);
        queueManager.getGameManager(gameId).playerDeath(e);
        e.getDrops().clear();
        e.setDroppedExp(0);
    }

    @EventHandler
    public void onPlayerSpawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!(playerInfo.get(uuid).getPlayerState() == PlayerState.INGAME)) return;
        if (!(queueManager.getGameID.containsKey(uuid))) return;
        int gameId = queueManager.getGameID.get(uuid);

        Location loc = queueManager.getGameManager(gameId).getPlayerGameInfo().get(uuid).getPlayerSpawn();
        if (!(queueManager.getGameManager(gameId).getPlayerGameInfo().get(uuid).getAlive())) {
            loc = queueManager.getGameManager(gameId).mapManager.getGameMap().getSpectatorSpawn();
        }
        loc.setPitch(player.getLocation().getPitch());
        loc.setYaw(player.getLocation().getYaw());
        e.setRespawnLocation(loc);
        queueManager.getGameManager(gameId).playerSpawn(e);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!(playerInfo.get(uuid).getPlayerState() == PlayerState.INGAME)) return;
        if (!(queueManager.getGameID.containsKey(uuid))) return;
        int gameId = queueManager.getGameID.get(uuid);

        queueManager.getGameManager(gameId).removePlayerFromGame(uuid);

        if (queueManager.getGameManager(gameId).getPlayerGameInfo().size() <= 1) {
            queueManager.getGameManager(gameId).gameEnd(false);
            queueManager.gameEnd(gameId);
        }

    }

    @EventHandler
    public void onPlayerDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!(playerInfo.get(uuid).getPlayerState() == PlayerState.INGAME)) return;
        if (!(queueManager.getGameID.containsKey(uuid))) return;
        e.setCancelled(true);
    }
}
