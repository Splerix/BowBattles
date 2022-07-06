package me.splerix.bowbattle.Commands.Admin;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

public class BowBattle implements CommandExecutor {
    Plugin plugin;

    public BowBattle(Plugin plugin) {
        this.plugin = plugin;
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("bowbattle")) {
            if (!sender.hasPermission("bowbattle.admin.command.bowbattle")) {
                sender.sendMessage(ChatColor.RED + "You cannot use this command");
                return false;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.RED + "Ussage: /BowBattle <args>");
                return false;
            }
            if (args.length > 0) {
                if (args[0].equalsIgnoreCase("reload")) {
                    reload(sender);
                    return true;
                }
            }
        }
        return false;
    }

    private void reload(CommandSender sender) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', plugin.getConfig().getString("reload.message")));
        plugin.reloadConfig();
    }
}
