package me.splerix.bowbattle.Commands.Game.Admin;

import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.splerix.bowbattle.Game.Manager.queueManager;
import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class ForceStart implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(label.equalsIgnoreCase("forcestart"))) return false;
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!(player.hasPermission("bowbattle.admin.command.forcestart"))) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return false;
        }
        if (!(playerInfo.get(player.getUniqueId()).getPlayerState() == PlayerState.QUEUED)) {
            player.sendMessage(ChatColor.RED + "You must be in a queue to use this command");
            return false;
        }

        if (args.length == 0) {
            player.sendMessage(ChatColor.DARK_PURPLE + "You have force started the game");
            queueManager.startGame(null);
            return true;
        }
        if (args.length == 1) {
            queueManager.startGame(args[0]);
            player.sendMessage(ChatColor.DARK_PURPLE + "You have force started the game on map " + ChatColor.LIGHT_PURPLE + args[0]);
            return true;
        }

        return true;
    }
}
