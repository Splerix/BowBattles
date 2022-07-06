package me.splerix.bowbattle.Features.Spawn;

import me.splerix.bowbattle.Objects.Area.Area;
import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;

import java.util.UUID;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class SpawnListeners implements Listener {
    Area area = new Area();
    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player)) return;
        Player player = (Player )e.getEntity();
        if (area.inSpawn(player)) e.setCancelled(true);
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (playerInfo.get(uuid).getPlayerState() != PlayerState.OUTGAME) return;
        if (playerInfo.get(uuid).isDevMode()) return;
        player.getLocation().setYaw(270);
        player.performCommand("spawn");
    }

    @EventHandler
    public void onDrop(PlayerDropItemEvent e) {
        Player player = e.getPlayer();
        if (!(area.inSpawn(player))) return;
        if (playerInfo.get(player.getUniqueId()).isDevMode()) e.getItemDrop().remove();
        else e.setCancelled(true);
    }
}
