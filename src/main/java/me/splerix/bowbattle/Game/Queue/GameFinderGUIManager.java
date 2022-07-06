package me.splerix.bowbattle.Game.Queue;

import me.splerix.bowbattle.Features.Spawn.SpawnInventory;
import me.splerix.bowbattle.Objects.Area.Area;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.UUID;

public class GameFinderGUIManager implements Listener {

    SpawnInventory spawnInventory = new SpawnInventory();
    Area area = new Area();

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
        Player player = e.getPlayer();
        if(!(area.inSpawn(player))) return;
        if(e.getItem() == null) return;
        if(!(e.getItem().equals(spawnInventory.getQueueCompass()))) return;

        player.performCommand("gamefinder");
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        UUID uuid = player.getUniqueId();
        if (!(ChatColor.stripColor(e.getView().getTitle().toString()).equalsIgnoreCase("Bow Battles Game Finder"))) return;
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;
        if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("deathmatch")) {
            player.performCommand("deathmatch");
        } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("bow battles")) {
            player.performCommand("queue");
        } else if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("spectator")) {
            player.performCommand("spectator");
        }
            e.setCancelled(true);
    }
}
