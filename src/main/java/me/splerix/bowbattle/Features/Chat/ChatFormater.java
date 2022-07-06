package me.splerix.bowbattle.Features.Chat;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.plugin.Plugin;

public class ChatFormater implements Listener {
    ChatPrefixReader prefixReader;
    String g = "group.";
    private Plugin plugin;

    public ChatFormater(Plugin plugin) {
        this.plugin = plugin;
        prefixReader = new ChatPrefixReader(plugin);
    }

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent e) {
        e.setFormat(prefixReader.getMessageFormat(e.getPlayer(), e.getMessage()));
    }


}
