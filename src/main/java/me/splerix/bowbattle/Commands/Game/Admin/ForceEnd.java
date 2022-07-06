package me.splerix.bowbattle.Commands.Game.Admin;

import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.UUID;

import static me.splerix.bowbattle.Game.Manager.queueManager;
import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class ForceEnd implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(label.equalsIgnoreCase("forceend"))) return false;
        if (!(sender instanceof Player)) return false;
        Player player = (Player) sender;
        UUID uuid = player.getUniqueId();
        if (!(player.hasPermission("bowbattle.admin.command.forceend"))) {
            player.sendMessage(ChatColor.RED + "You do not have permission to use this command");
            return false;
        }
        if (args.length == 0) {
            if (!(playerInfo.get(player.getUniqueId()).getPlayerState() == PlayerState.INGAME)) {
                player.sendMessage(ChatColor.RED + "You have to clarify a game ID or be in a game to use this command");
                return false;
            }
            int gameId = queueManager.getGameID.get(uuid);
            queueManager.getGameManager(gameId).gameEnd(false);
            queueManager.gameEnd(gameId);
            player.sendMessage(ChatColor.DARK_PURPLE + "You have force ended the game");
            return true;
        }
        if (args.length == 1) {
            String num = args[0];
            int gameId;
            if (!(isNum(num))) {
                player.sendMessage(ChatColor.RED + "Must type a valid game id");
                return false;
            }
            gameId = parseInt(num);
            if (!(queueManager.checkGameId(gameId))) {
                player.sendMessage(ChatColor.RED + "Must type a valid game id");
                return false;
            }
            queueManager.getGameManager(gameId).gameEnd(false);
            queueManager.gameEnd(gameId);
            player.sendMessage(ChatColor.DARK_PURPLE + "You have force ended the game with the ID: " + ChatColor.LIGHT_PURPLE + gameId );
            return true;
        }


        return true;
    }

    private int parseInt(String numString) {
        try {
            return Integer.parseInt(numString);
        } catch (Exception e) {
            System.out.println("ERROR PARSING FORCE END ::" + e.getCause() + "  :  "  + e.getMessage());
            return -1;
        }
    }

    private boolean isNum(String numString) {
        try {
            Integer.parseInt(numString);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
