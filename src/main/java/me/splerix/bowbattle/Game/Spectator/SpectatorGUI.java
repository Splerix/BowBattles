package me.splerix.bowbattle.Game.Spectator;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class SpectatorGUI {
    private Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Spectator Menu");
    List<String> lore = new ArrayList<String>();

    public SpectatorGUI() {
        fillInv();
    }

    public Inventory getInv() {
        updateGames();
        return inv;
    }

    private void updateGames() {

    }

    private void fillInv() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        for(int i=0;i<27;i++) {
            if (!(i > 9 && i < 17))
            inv.setItem(i, item);
        }
    }
}
