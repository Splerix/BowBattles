package me.splerix.bowbattle.Game.Queue;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class QueueInventory {

    private final ItemStack leaveQueueItem = new ItemStack(Material.RED_DYE);

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
