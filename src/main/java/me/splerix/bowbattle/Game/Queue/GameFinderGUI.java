package me.splerix.bowbattle.Game.Queue;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class GameFinderGUI {
    private final Inventory inv = Bukkit.createInventory(null, 27, ChatColor.DARK_PURPLE + "Bow Battles Game Finder");
    List<String> lore = new ArrayList<>();
    ItemMeta meta;
    ItemStack bow = new ItemStack(Material.BOW);
    ItemStack sword = new ItemStack(Material.DIAMOND_SWORD);
    ItemStack enderEye = new ItemStack(Material.ENDER_EYE);

    public GameFinderGUI() {
        fillInv();
        setSwordMeta();
        setBowMeta();
        setEnderEyeMeta();
        inv.setItem(11, sword);
        inv.setItem(13, bow);
        inv.setItem(15, enderEye);
    }

    public Inventory getGameFinderGUI() {
        return inv;
    }

    private void fillInv() {
        ItemStack item = new ItemStack(Material.BLACK_STAINED_GLASS_PANE);
        meta = item.getItemMeta();
        meta.setDisplayName(" ");
        item.setItemMeta(meta);
        for(int i=0;i<27;i++) {
            inv.setItem(i, item);
        }
    }

    private void setSwordMeta() {
        meta = sword.getItemMeta();
        meta.setUnbreakable(true);

        meta.setDisplayName(ChatColor.DARK_AQUA + "Deathmatch");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        lore.add("");
        lore.add(ChatColor.LIGHT_PURPLE + "PvP While Waiting For Queue");
        meta.setLore(lore);
        sword.setItemMeta(meta);
        lore.clear();
    }

    private void setBowMeta() {
        meta = bow.getItemMeta();
        meta.setUnbreakable(true);

        meta.setDisplayName(ChatColor.AQUA + "Bow Battles");
        meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
        meta.addEnchant(Enchantment.DURABILITY, 1, true);
        lore.add("");
        lore.add(ChatColor.LIGHT_PURPLE + "Testing!");
        meta.setLore(lore);
        bow.setItemMeta(meta);
        lore.clear();
    }

    private void setEnderEyeMeta() {
        meta = enderEye.getItemMeta();

        meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Spectator");
        lore.add("");
        lore.add(ChatColor.LIGHT_PURPLE + "Coming Soon");
        meta.setLore(lore);
        enderEye.setItemMeta(meta);
        lore.clear();
    }

}
