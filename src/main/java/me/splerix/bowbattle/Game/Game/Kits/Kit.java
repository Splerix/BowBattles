package me.splerix.bowbattle.Game.Game.Kits;

import me.splerix.bowbattle.Game.Game.PlayerGameInfo.PlayerGameInfo;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EntityEquipment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;

import java.util.UUID;

import static me.splerix.bowbattle.Game.Manager.queueManager;

public class Kit {
    public void giveKit(Player player) {
        player.getInventory().clear();
        EntityEquipment equipment = player.getEquipment();

        //Boots
        equipment.setBoots(getKitItems(5, player));
        //Leggings
        equipment.setLeggings(getKitItems(6, player));
        //ChestPlate
        equipment.setChestplate(getKitItems(7, player));
        //Helmet
        equipment.setHelmet(getKitItems(8, player));

        for (int i = 0; i < 5; i++) {
            player.getInventory().addItem(getKitItems(i, player));
        }
    }

    private ItemStack getKitItems(int count, Player player) {
        ItemStack item;
        ItemMeta meta;
        switch (count) {
            case 0:
                item = new ItemStack(Material.WOODEN_SWORD);
                meta = item.getItemMeta();
                meta.addEnchant(Enchantment.KNOCKBACK, 2, true);
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Wooden Sword");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
            case 1:
                item = new ItemStack(Material.BOW);
                meta = item.getItemMeta();
                meta.addEnchant(Enchantment.ARROW_INFINITE, 1, true);
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Bow");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
            case 2:
                item = new ItemStack(Material.GOLDEN_APPLE, 5);
                return item;
            case 3:
                item  = new ItemStack(Material.COOKED_BEEF, 32);
                return item;
            case 4:
                item = new ItemStack(Material.ARROW);
                return item;
            case 5:
                item = new ItemStack(Material.CHAINMAIL_BOOTS);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Chainmail Boots");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
            case 6:
                item = new ItemStack(Material.IRON_LEGGINGS);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Iron Leggings");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
            case 7:
                item = new ItemStack(Material.IRON_CHESTPLATE);
                meta = item.getItemMeta();
                meta.setDisplayName(ChatColor.LIGHT_PURPLE + "Iron Chestplate");
                meta.setUnbreakable(true);
                item.setItemMeta(meta);
                return item;
            case 8:
                item = new ItemStack(Material.LEATHER_HELMET);
                LeatherArmorMeta lemeta = (LeatherArmorMeta) item.getItemMeta();
                lemeta.setColor(getPlayerColor(player));
                lemeta.setDisplayName(ChatColor.LIGHT_PURPLE + "Leather Helmet");
                lemeta.setUnbreakable(true);
                item.setItemMeta(lemeta);
                return item;
        }
        return new ItemStack(Material.AIR);
    }

    private Color getPlayerColor(Player player) {
        UUID uuid = player.getUniqueId();
        PlayerGameInfo playerGI =   queueManager.getGameManager(queueManager.getGameID.get(uuid)).getPlayerGameInfo().get(uuid);
        switch (playerGI.getTeam()) {
            case RED:
                return Color.RED;
            case BLUE:
                return Color.BLUE;
            case GREEN:
                return Color.GREEN;
            case YELLOW:
                return Color.YELLOW;
        }
        return Color.BLACK;
    }
}
