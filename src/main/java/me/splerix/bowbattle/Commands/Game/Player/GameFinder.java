package me.splerix.bowbattle.Commands.Game.Player;

import me.splerix.bowbattle.Game.Queue.GameFinderGUI;
import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class GameFinder implements CommandExecutor {

    GameFinderGUI gameFinderGUI = new GameFinderGUI();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(label.equalsIgnoreCase("gamefinder"))) return false;
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        if (!(player.hasPermission("bowbattle.player.command.gamefinder"))) {
            player.sendMessage(ChatColor.RED + "You do not have permission to do this command");
            return false;
        }
        UUID uuid = player.getUniqueId();
        PlayerState playerState = playerInfo.get(uuid).getPlayerState();
        if (playerState == PlayerState.INGAME) {
            player.sendMessage(ChatColor.RED + "You can't use this command while in a game");
            return false;
        }

        player.openInventory(gameFinderGUI.getGameFinderGUI());

        return false;
    }
}
