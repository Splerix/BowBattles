package me.splerix.bowbattle.Game;

import me.splerix.bowbattle.Game.Queue.QueueManager;
import org.bukkit.plugin.Plugin;

public class Manager {

    private Plugin plugin;
    public static QueueManager queueManager;

    public Manager(Plugin plugin) {
        this.plugin = plugin;
        queueManager = new QueueManager(plugin);

    }

    public void onDisable() {
        queueManager.onDisable();
    }
}
