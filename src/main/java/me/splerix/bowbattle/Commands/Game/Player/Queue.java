package me.splerix.bowbattle.Commands.Game.Player;

import me.splerix.bowbattle.Objects.Area.Area;
import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.splerix.bowbattle.Game.Manager.queueManager;
import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class Queue implements CommandExecutor {

    Area area = new Area();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(label.equalsIgnoreCase("queue"))) return false;
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!(player.hasPermission("bowbattle.player.command.queue"))) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return  false;
        }
        UUID uuid = player.getUniqueId();
        if (queueManager.addPlayerToQueue(uuid)) {
            player.sendMessage(ChatColor.DARK_PURPLE + "You have joined the queue for Bow Battles");
            player.playSound(player.getLocation(), Sound.BLOCK_SCULK_CATALYST_HIT, 1,1);
            player.closeInventory();
        } else {
            if (!(playerInfo.get(uuid).getPlayerState() == PlayerState.OUTGAME)) player.sendMessage(ChatColor.RED + "You must be out of queue and not in spectator to join queue");
            else if (!(area.inSpawn(player))) player.sendMessage(ChatColor.RED + "You must be in spawn to join the queue");
            else if (queueManager.getQueueLine().size() == 4) player.sendMessage(ChatColor.RED + "There are to many people in queue right now");
            else if (playerInfo.get(uuid).isDevMode()) player.sendMessage(ChatColor.RED + "Dev mode must be set to false to join the queue");
            else player.sendMessage(ChatColor.RED + "There was an error while trying to join the queue please try again later");
            player.playSound(player.getLocation(), Sound.ENTITY_ENDER_EYE_DEATH, 1,1);
        }

        return true;
    }
}
