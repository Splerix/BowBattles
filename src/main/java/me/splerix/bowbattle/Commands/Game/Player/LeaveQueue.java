package me.splerix.bowbattle.Commands.Game.Player;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.splerix.bowbattle.Game.Manager.queueManager;

public class LeaveQueue implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(label.equalsIgnoreCase("leavequeue"))) return false;
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (queueManager.removePlayerFromQueue(player)){
            player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY,1 , 1);
            player.sendMessage(ChatColor.DARK_PURPLE + "You have been removed from the queue!");
        }

        return true;
    }
}
