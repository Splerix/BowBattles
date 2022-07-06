package me.splerix.bowbattle.Commands.Player;

import me.splerix.bowbattle.Game.Spectator.SpectatorGUI;
import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class Spectator implements CommandExecutor {
    SpectatorGUI spectatorGUI = new  SpectatorGUI();
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(label.equalsIgnoreCase("spectator"))) return false;
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        PlayerState playerState = playerInfo.get(uuid).getPlayerState();
        if (!(player.hasPermission("bowbattle.player.command.spectator"))) {
            player.sendMessage(ChatColor.RED + "You don't have permission to use this command");
            return false;
        }
        if (playerState == PlayerState.INGAME || playerState == PlayerState.QUEUED) {
            player.sendMessage(ChatColor.RED + "You can't use this command if you are in a game or queued");
            return false;
        }
        if (playerInfo.get(uuid).isDevMode()) {
            player.sendMessage(ChatColor.RED + "You can't use this command while in dev mode");
            return false;
        }

        player.openInventory(spectatorGUI.getInv());


        return true;
    }
}
