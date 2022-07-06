package me.splerix.bowbattle.Features.Deathmatch;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class DeathmatchKit {
    
    public void giveKit(Player player) {
        player.getInventory().clear();
        EntityEquipment equipment = player.getEquipment();

        equipment.setItemInOffHand(getKitItems(4, player));
        //Boots
        equipment.setBoots(getKitItems(5, player));
        //Leggings
        equipment.setLeggings(getKitItems(6, player));
        //ChestPlate
        equipment.setChestplate(getKitItems(7, player));
        //Helmet
        equipment.setHelmet(getKitItems(8, player));

        for (int i = 0; i < 4; i++) {
            player.getInventory().addItem(getKitItems(i, player));
        }
    }

    private ItemStack getKitItems(int count, Player player) {
        ItemStack item = new ItemStack(Material.AIR);
        String name = player.getName();

        switch (count) {
            case 0:
                item = new ItemStack(Material.IRON_SWORD);
                ItemMeta meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + name + "'s  Sword");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
            case 1:
                item = new ItemStack(Material.GOLDEN_APPLE, 5);
                return item;
            case 2:
                item = new ItemStack(Material.COOKED_BEEF, 32);
                return item;
            case 3:
                item = new ItemStack(Material.WOODEN_AXE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + name + "'s  Axe");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
            case 4:
                item = new ItemStack(Material.SHIELD);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + name + "'s  Shield");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
            case 5:
                item = new ItemStack(Material.IRON_BOOTS);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + name + "'s  Boots");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
            case 6:
                item = new ItemStack(Material.CHAINMAIL_LEGGINGS);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + name + "'s  Leggings");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
            case 7:
                item = new ItemStack(Material.IRON_CHESTPLATE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + name + "'s  Chestplate");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
            case 8:
                item = new ItemStack(Material.CHAINMAIL_HELMET);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + name + "'s  Helmet");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
        }
        return item;

    }
}
