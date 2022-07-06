package me.splerix.bowbattle.Objects.Player;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class PlayerList implements Listener {

    public static Map<UUID, PlayerInfo> playerInfo;

    @EventHandler(priority = EventPriority.LOWEST)
    public void onJoin(PlayerJoinEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        playerInfo.put(player.getUniqueId(), new PlayerInfo(uuid));
        if (!(playerInfo.containsKey(uuid))) {
            playerInfo.put(uuid, new PlayerInfo(uuid));
        }
        playerInfo.get(uuid).setOnline(true);
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        if (!(playerInfo.containsKey(uuid))) {
            playerInfo.put(uuid, new PlayerInfo(uuid));
        }
        playerInfo.get(uuid).setOnline(false);
    }

    public void onEnable() {
        playerInfo = new HashMap<UUID, PlayerInfo>();
        loadPlayerInfo();
        savePlayerInfo();
    }

    public void onDisable() {
        savePlayerInfo();
    }

    private void loadPlayerInfo() {
        try {
            ObjectInputStream in = new ObjectInputStream(new FileInputStream(
                    "C:\\Users\\kiant\\OneDrive\\Desktop\\StuffIDontUse\\Server\\server\\O\\BowBattle\\1.19 Bow Battle\\plugins\\BowBattle\\PlayerData.yml"));
            playerInfo = (Map) in.readObject();
        } catch (Exception e) {
            System.out.println("FAILED LOAD");
            System.out.println(e.getMessage() + " :: " + e.getCause());
        }
    }
    public void savePlayerInfo() {
        try{
            ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(
                    "C:\\Users\\kiant\\OneDrive\\Desktop\\StuffIDontUse\\Server\\server\\O\\BowBattle\\1.19 Bow Battle\\plugins\\BowBattle\\PlayerData.yml"));
            out.writeObject(playerInfo);
        } catch(Exception e) {
            System.out.println("FAILED SAVE");
        }
    }
}
