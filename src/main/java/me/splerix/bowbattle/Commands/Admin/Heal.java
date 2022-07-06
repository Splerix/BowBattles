package me.splerix.bowbattle.Commands.Admin;

import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class Heal implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (label.equalsIgnoreCase("heal")) {
            if (!(sender instanceof Player)) {
                sender.sendMessage(ChatColor.RED + "Console cannot use this command.");
                return false;
            }
            Player player = (Player) sender;
            try {
                if (args.length == 0) {
                    if (player.hasPermission("bowbattle.admin.command.heal")) {
                        if (playerInfo.get(player.getUniqueId()).getPlayerState() == PlayerState.INGAME) {
                            player.sendMessage(ChatColor.RED + "You can't use this command if you are in a game");
                            return false;
                        }
                        heal(player);
                        player.sendMessage(ChatColor.GOLD + "You have been healed");
                        return true;
                    }
                    player.sendMessage(ChatColor.RED + "You do not have permission for this command");
                    return false;
                } else if (args.length == 1) {
                    if (player.hasPermission("bowbattle.admin.command.heal.everyone")) {
                        Player target = Bukkit.getPlayerExact(args[0]);
                        if (playerInfo.get(target.getUniqueId()).getPlayerState() == PlayerState.INGAME) {
                            player.sendMessage(ChatColor.RED + "You can only heal people that aren't currently in a game");
                            return false;
                        }
                        player.sendMessage(ChatColor.GOLD + "You have healed " + ChatColor.DARK_PURPLE + target.getName());

                        heal(target);
                        target.sendMessage(ChatColor.GOLD + "You have been healed by " + ChatColor.DARK_PURPLE + player.getName());
                        return true;
                    }
                    player.sendMessage(ChatColor.RED + "You do not have permission for this command");
                    return false;
                } else if (args.length > 1){
                    player.sendMessage(ChatColor.RED + "Usage /heal <player (choice)>");
                    return false;
                }

            } catch (Exception e) {
                player.sendMessage(ChatColor.RED + "Usage /heal <player (choice)>");
                return false;
            }
        }
        return false;
    }

    void heal(Player player) {
        try {
            player.setHealth(20.0);
            player.setFoodLevel(20);
        } catch (Exception e) {
        }
    }
}
