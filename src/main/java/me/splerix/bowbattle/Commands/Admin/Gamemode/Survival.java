package me.splerix.bowbattle.Commands.Admin.Gamemode;

import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class Survival  implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("gms")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Console cannot use this command");
                return false;
            }
            Player player = (Player) sender;
            if (args.length == 0) {
                if (player.hasPermission("bowbattle.admin.command.gms")) {
                    if (!(playerInfo.get(player.getUniqueId()).getPlayerState() == PlayerState.OUTGAME)) {
                        player.sendMessage(ChatColor.RED + "You can't use this command if you are in a game or a queue");
                        return false;
                    }
                    player.setGameMode(GameMode.SURVIVAL);
                    player.sendMessage(ChatColor.DARK_PURPLE + "Your gamemode has been changed to " + ChatColor.LIGHT_PURPLE + "survival");
                    return true;
                }
                player.sendMessage(ChatColor.RED + "You can't use this command if you are in a game or a queue");
                return false;
            } else if (args.length == 1) {
                if (player.hasPermission("bowbattle.admin.command.gms.everyone")) {
                    if (!(playerInfo.get(player.getUniqueId()).getPlayerState() == PlayerState.OUTGAME)) {
                        player.sendMessage(ChatColor.RED + "You can only use this command if you are in a game or a queue");
                        return false;
                    }
                    try {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        target.setGameMode(GameMode.SURVIVAL);
                        target.sendMessage(ChatColor.LIGHT_PURPLE + player.getName() + ChatColor.DARK_PURPLE + " has set your gamemode to "  + ChatColor.LIGHT_PURPLE +"survival");
                        player.sendMessage(ChatColor.DARK_PURPLE + "You have set " + ChatColor.LIGHT_PURPLE + target.getName() + ChatColor.DARK_PURPLE+ "'s gamemode to " + ChatColor.LIGHT_PURPLE +"survival");
                    } catch (Exception e) {
                        player.sendMessage(ChatColor.RED + "Can't find that player!");
                    }
                    return true;
                }
                player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
                return false;
            }
        }
        return false;
    }
}
