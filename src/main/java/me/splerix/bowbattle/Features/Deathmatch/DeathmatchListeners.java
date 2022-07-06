package me.splerix.bowbattle.Features.Deathmatch;

import me.splerix.bowbattle.Objects.Area.Area;
import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerRespawnEvent;

import java.util.UUID;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class DeathmatchListeners implements Listener {

    Area area = new Area();

    @EventHandler
    public void deathmatchSafeZone(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player )e.getEntity();
        if (area.inDeathmatchSafezone(player)) e.setCancelled(true);
    }

    @EventHandler
    public void playerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        if(playerInfo.get(player.getUniqueId()).getPlayerState() != PlayerState.OUTGAME) return;
        if(!(area.inDeathmatch(player.getLocation()))) return;
        if (playerInfo.get(player.getUniqueId()).isDevMode()) return;
        e.setKeepInventory(false);
        e.getDrops().clear();
    }

    @EventHandler
    public void playerRespawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (playerInfo.get(uuid).getPlayerState() != PlayerState.OUTGAME || playerInfo.get(uuid).getPlayerState() == PlayerState.QUEUED) return;
        player.getInventory().clear();
        e.setRespawnLocation(area.getSpawnLocation());
        player.performCommand("spawn");
    }

}
