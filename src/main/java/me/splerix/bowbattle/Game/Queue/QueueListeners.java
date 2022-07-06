package me.splerix.bowbattle.Game.Queue;

import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import static me.splerix.bowbattle.Game.Manager.queueManager;
import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class QueueListeners implements Listener {

    QueueInventory queueInventory = new QueueInventory();

    @EventHandler
    public void onRightClick(PlayerInteractEvent e) {
        if (!(e.getAction().equals(Action.RIGHT_CLICK_BLOCK) || e.getAction().equals(Action.RIGHT_CLICK_AIR))) return;
        Player player = e.getPlayer();
        if (e.getItem() == null) return;
        if (!(e.getItem().equals(queueInventory.getLeaveQueueItem()))) return;
        if (!(queueManager.removePlayerFromQueue(player))) {
            player.getInventory().remove(queueInventory.getLeaveQueueItem());
            e.setCancelled(true);
            return;
        }
        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY,1, 1);
        player.sendMessage(ChatColor.DARK_PURPLE + "You have been removed from the queue!");
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        if (!(playerInfo.get(player.getUniqueId()).getPlayerState() == PlayerState.QUEUED)) return;

        queueManager.removePlayerFromQueue(player);
    }
}