package me.splerix.bowbattle.Features.Chat;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class ChatPrefixReader {
    private Plugin plugin;
    private String[] rankList;

    ChatPrefixReader(Plugin plugin) {
        this.plugin = plugin;

       rankList = new String[plugin.getConfig().getStringList("chatformat.ranks").size()];

        int i = 0;
        for (String rank : plugin.getConfig().getStringList("chatformat.ranks")) {
            rankList[i] = rank;
            i++;
        }
    }

    public String getMessageFormat(Player player, String message) {
        return translateColorCode("" + getPrefix(player) + " &5[&7" + player.getDisplayName() + "&5]:&7 " + message);
    }

    private String getPrefix(Player player) {
        int i = 0;
        for (String rank : rankList) {
            if (player.hasPermission("group." + rank)) {
                return plugin.getConfig().getStringList("chatformat.prefixes").get(i);
            }
            i++;
        }
            return plugin.getConfig().getStringList("chatformat.prefixes").get(rankList.length-1);
    }

    private String translateColorCode(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }
}
