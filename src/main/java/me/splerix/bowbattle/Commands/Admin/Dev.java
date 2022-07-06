package me.splerix.bowbattle.Commands.Admin;

import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class Dev implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        Player player;

        //Initial Checks
        if (!(label.equalsIgnoreCase("dev"))) return false;
        if (!(sender instanceof Player)) return false;
        player = (Player) sender;
        if (!player.hasPermission("BowBattle.Admin.Command.dev")) {
            player.sendMessage(ChatColor.RED + "You do not have permission to do this command");
            return false;
        }
        PlayerState playerState = playerInfo.get(player.getUniqueId()).getPlayerState();
        if (!(playerState == PlayerState.OUTGAME)) {
            player.sendMessage(ChatColor.RED + "You can't use this command if you are in a game or a queue");
            return false;
        }

        player.sendMessage(ChatColor.DARK_PURPLE + "Dev mode has been set to: " + ChatColor.LIGHT_PURPLE
                + playerInfo.get(player.getUniqueId()).toggleDevMode());
        return true;
    }
}
