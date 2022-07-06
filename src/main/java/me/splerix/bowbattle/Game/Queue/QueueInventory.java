package me.splerix.bowbattle.Game.Queue;

import me.splerix.bowbattle.Objects.Area.Area;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QueueInventory {

    private ItemStack leaveQueueItem = new ItemStack(Material.RED_DYE);

    public QueueInventory() {
        setLeaveQueueItem();
    }

    public ItemStack getLeaveQueueItem() {
        return leaveQueueItem;
    }

    private void setLeaveQueueItem() {
        ItemMeta meta = leaveQueueItem.getItemMeta();

        meta.setDisplayName(ChatColor.RED + "Leave Queue");
        leaveQueueItem.setItemMeta(meta);
    }
}
