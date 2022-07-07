package me.splerix.bowbattle.Features.Spawn;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class SpawnInventory {
    private final ItemStack queueCompass = new ItemStack(Material.COMPASS);

    public SpawnInventory() {
        setQueueCompass();
    }


    public void setSpawnInventory(Player player) {
        player.getInventory().clear();
        player.getInventory().setItem(0, queueCompass);
    }

    public ItemStack getQueueCompass() {
        return queueCompass;
    }

    private void setQueueCompass() {
        ItemMeta meta = queueCompass.getItemMeta();

        meta.setUnbreakable(true);
        meta.setDisplayName(ChatColor.DARK_PURPLE + "Game Finder");

        queueCompass.setItemMeta(meta);
    }

}
