package me.splerix.bowbattle.Game.Spectator;

import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SpectatorGUIManager implements Listener {
    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (!(ChatColor.stripColor(e.getView().getTitle()).equalsIgnoreCase("spectator menu"))) return;
        if (e.getCurrentItem() == null) return;
        if (e.getCurrentItem().getItemMeta() == null) return;
        if (e.getCurrentItem().getItemMeta().getDisplayName() == null) return;

        //if (ChatColor.stripColor(e.getCurrentItem().getItemMeta().getDisplayName()).equalsIgnoreCase("h"))
        e.setCancelled(true);
    }
}
