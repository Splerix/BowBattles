package me.splerix.bowbattle.Commands.Player.Warps;

import me.splerix.bowbattle.Game.Queue.QueueInventory;
import me.splerix.bowbattle.Features.Spawn.SpawnInventory;
import me.splerix.bowbattle.Objects.Area.Area;
import me.splerix.bowbattle.Objects.Player.PlayerList;
import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class Spawn implements CommandExecutor {
    SpawnInventory spawnInventory = new SpawnInventory();
    QueueInventory queueInventory = new QueueInventory();
    Location loc;
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(label.equalsIgnoreCase("spawn") || label.equalsIgnoreCase("hub"))) return false;
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        loc = new Area().getSpawnLocation();
        if (!(player.hasPermission("bowbattle.player.command.spawn"))) {
            player.sendMessage(ChatColor.RED + "You do not have permission to do this command");
            return false;
        }
        if (playerInfo.get(player.getUniqueId()).getPlayerState() == PlayerState.INGAME) {
            player.sendMessage(ChatColor.RED + "You cant use this command if you are in a game");
            return false;
        }
        loc.setPitch(player.getLocation().getPitch());
        loc.setYaw(player.getLocation().getYaw());
        player.teleport(loc);
        player.playSound(player.getLocation(), Sound.ENTITY_ENDERMAN_TELEPORT, 1,1 );

        if (!(PlayerList.playerInfo.get(player.getUniqueId()).isDevMode())) {
            spawnInventory.setSpawnInventory(player);
            player.setHealth(20);
            player.setFoodLevel(20);
            if (playerInfo.get(player.getUniqueId()).getPlayerState() == PlayerState.QUEUED ) player.getInventory().setItem(8, queueInventory.getLeaveQueueItem());
        }
        return true;
    }
}
