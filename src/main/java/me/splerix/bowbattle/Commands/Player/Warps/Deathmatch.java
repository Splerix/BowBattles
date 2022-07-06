package me.splerix.bowbattle.Commands.Player.Warps;

import me.splerix.bowbattle.Features.Deathmatch.DeathmatchKit;
import me.splerix.bowbattle.Game.Queue.QueueInventory;
import me.splerix.bowbattle.Objects.Area.Area;
import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class Deathmatch implements CommandExecutor {

    QueueInventory queueInventory = new QueueInventory();
    DeathmatchKit deathmatchKit = new DeathmatchKit();
    Location loc;

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(label.equalsIgnoreCase("deathmatch"))) return false;
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        loc = new Area().getDeathmatchLocation();
        if (!(player.hasPermission("bowbattle.player.command.deathmatch"))) {
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
        if (!(playerInfo.get(player.getUniqueId()).isDevMode())) {
            deathmatchKit.giveKit(player);
            player.setHealth(20);
            player.setFoodLevel(20);
            if (playerInfo.get(player.getUniqueId()).getPlayerState() == PlayerState.QUEUED ) player.getInventory().setItem(8, queueInventory.getLeaveQueueItem());
        }
        return false;
    }
}