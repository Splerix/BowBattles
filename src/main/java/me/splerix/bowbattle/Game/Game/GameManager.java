package me.splerix.bowbattle.Game.Game;

import me.splerix.bowbattle.Features.Spawn.SpawnInventory;
import me.splerix.bowbattle.Game.Game.Kits.Kit;
import me.splerix.bowbattle.Game.Game.Maps.MapManager;
import me.splerix.bowbattle.Game.Game.PlayerGameInfo.PlayerGameInfo;
import me.splerix.bowbattle.Game.Game.PlayerGameInfo.PlayerTeam;
import me.splerix.bowbattle.Game.Game.Scoreboard.ScoreboardManager;
import me.splerix.bowbattle.Objects.Area.Area;
import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import javax.annotation.Nullable;
import java.util.*;

import static me.splerix.bowbattle.Game.Manager.queueManager;
import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class GameManager {

    private final int GAMESECONDS = 420;
    private final int OVERTIMESECONDS = 300;

    private final Plugin plugin;
    private GameState gameState;
    public MapManager mapManager;
    private ScoreboardManager scoreboardManager;
    private final int gameId;
    private int loopId;

    public Map<PlayerTeam, UUID> teamToUUID;
    private final Map<UUID, PlayerGameInfo> playerGameInfo;
    private Collection<UUID> currentPlayerList;
    int amountOfPlayers;
    private int seconds = GAMESECONDS;
    private boolean overtime = false;


    public GameManager(Plugin plugin, int gameId) {
        this.plugin = plugin;
        gameState = GameState.READY;
        this.gameId = gameId;
        playerGameInfo = new HashMap<>();
        teamToUUID = new HashMap<>();
    }

    public void startGame(@Nullable String forceMapName, ArrayList<UUID> playerList) {
        if (playerList.size() > 4) return;
        Collections.shuffle(playerList);
        for (UUID uuid : playerList) {
            playerInfo.get(uuid).setPlayerState(PlayerState.INGAME);
            PlayerTeam team = teamNumToTeam(playerList.indexOf(uuid));
            playerGameInfo.put(uuid,new PlayerGameInfo(uuid, team));
            teamToUUID.put(team, uuid);
        }
        amountOfPlayers = playerList.size();
        gameState = GameState.STARTING;
        currentPlayerList = playerGameInfo.keySet();

        mapManager = new MapManager(plugin);
        mapManager.onGameStart(forceMapName);

        scoreboardManager = new ScoreboardManager(gameId, playerGameInfo.keySet(), mapManager.getMapName());

        queueManager.clearQueueLine();
        queueManager.addCurrentMap(mapManager.getMapName());

        initiatePlayers();
        gameLoop();
    }

    private void gameLoop() {
        seconds = GAMESECONDS;
        overtime = false;
        loopId = Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, () -> {
            seconds--;
            if (!(overtime) && seconds <= 0) {
                initiateOvertime();
            }
            if (overtime && seconds <= 0) {
                for (UUID uuid : currentPlayerList) {
                    Bukkit.getPlayer(uuid).getPlayer().sendMessage(
                            ChatColor.DARK_PURPLE + "The game ran out of time");
                }
                gameEnd(true);
            }

            for (UUID uuid : playerGameInfo.keySet()) {
                if (playerGameInfo.get(uuid).getAlive()){
                    Player player = Bukkit.getPlayer(uuid);
                    if (mapManager.getGameMap().inOwnArea(player, playerGameInfo.get(uuid))) {
                        player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 21, 1));
                    }

                    if (playerGameInfo.get(uuid).getLifes() <= 0 || (!(playerGameInfo.get(uuid).getAlive()))) {
                        player.teleport(mapManager.getGameMap().getSpectatorSpawn());
                        player.setGameMode(GameMode.SPECTATOR);
                        playerEliminated(uuid);
                    }
                }
            }
            scoreboardManager.updateScoreboard();
        }, 1, 20);
    }
    private void playerEliminated(UUID uuid) {
        playerInfo.get(uuid).setPlayerState(PlayerState.SPECTATOR);
        playerGameInfo.get(uuid).setAlive(false);
        queueManager.removePlayerFromGames(uuid);
    }
    public void gameEnd(Boolean announced) {
        if (!(announced)) {
            for (UUID uuid : currentPlayerList) {
                Player player = Bukkit.getPlayer(uuid);
                player.sendMessage(ChatColor.LIGHT_PURPLE + "Your game has ended!");
            }
        }
        restartGame();
        mapManager.onGameEnd();
        scoreboardManager.onGameEnd();
        gameState = GameState.READY;
        Bukkit.getScheduler().cancelTask(loopId);
    }
    public void onDisable() {
        for(UUID uuid : currentPlayerList) {
            Player player = Bukkit.getPlayer(uuid);
            player.teleport(new Area().getSpawnLocation());
            new SpawnInventory().setSpawnInventory(player);
            player.setHealth(20);
            player.setFoodLevel(20);
            playerInfo.get(uuid).setPlayerState(PlayerState.OUTGAME);
        }
        teamToUUID.clear();
        playerGameInfo.clear();
        scoreboardManager.onDisable();
        mapManager.onDisable();
        Bukkit.getScheduler().cancelTask(loopId);
    }

    public void playerDeath(PlayerDeathEvent e) {
        Player player = e.getEntity();
        UUID uuid = player.getUniqueId();
        PlayerGameInfo playerGI;
        try {
            Player killer = player.getKiller();
            UUID killerUUID = killer.getUniqueId();
            playerGI = playerGameInfo.get(killerUUID);
            playerGI.incKills();
            playerGameInfo.put(killerUUID, playerGI);
            killer.getInventory().addItem(new ItemStack(Material.GOLDEN_APPLE));
        } catch (Exception ex) {}
        playerGI = playerGameInfo.get(uuid);
        playerGI.decLifes();
        playerGI.incDeaths();
        if (playerGI.getLifes() <= 0) {
            playerGI.setAlive(false);
        } else {
            player.sendMessage(ChatColor.GOLD + "You have " + playerGI.getLifes() + " left before you are eliminated");
        }
        playerGameInfo.put(uuid, playerGI);
    }
    public void playerSpawn(PlayerRespawnEvent e) {
        Player player = e.getPlayer();
        UUID uuid = player.getUniqueId();
        player.getInventory().clear();
        player.playSound(e.getRespawnLocation(), Sound.BLOCK_ANVIL_LAND,1 ,1 );
        if (playerGameInfo.get(uuid).getAlive()){
            Kit kit = new Kit();
            kit.giveKit(player);
            invulnerableTimer(player);
        } else {
            player.setGameMode(GameMode.SPECTATOR);
        }
    }
    public MapManager getMapManager() {
        return mapManager;
    }
    public void removePlayerFromGame(UUID uuid) {
        queueManager.getGameID.remove(uuid);
        currentPlayerList.remove(uuid);
        playerInfo.get(uuid).setPlayerState(PlayerState.OUTGAME);
        scoreboardManager.removeBoardTo(uuid);
    }
    public Map<UUID, PlayerGameInfo> getPlayerGameInfo() {
        return playerGameInfo;
    }
    public int getTimeLeft() {
        return seconds;
    }
    public boolean isOvertime() {
        return overtime;
    }
    private void restartGame() {
        for(UUID uuid : currentPlayerList) {
            Player player = Bukkit.getPlayer(uuid);
            playerInfo.get(uuid).setPlayerState(PlayerState.OUTGAME);
            player.setGameMode(GameMode.ADVENTURE);
            player.performCommand("spawn");
            player.playSound(player.getLocation(), Sound.BLOCK_END_GATEWAY_SPAWN, 1000 ,1 );
            scoreboardManager.removeBoardTo(uuid);
        }
        teamToUUID.clear();
        playerGameInfo.clear();

    }
    private PlayerTeam teamNumToTeam(int teamNum) {
        switch (teamNum) {
            case 0: return PlayerTeam.RED;
            case 1: return PlayerTeam.GREEN;
            case 2: return PlayerTeam.BLUE;
            case 3: return PlayerTeam.YELLOW;
        }
        return PlayerTeam.NONE;
    }
    private void initiatePlayers() {
        Kit kit = new Kit();
        for (UUID uuid : currentPlayerList) {
            Player player = Bukkit.getPlayer(uuid);
            PlayerGameInfo playerGI = playerGameInfo.get(uuid);
            switch (playerGI.getTeam()) {
                case RED:
                    playerGI.setPlayerSpawn(mapManager.getGameMap().getRedPlayerSpawn());
                    break;
                case BLUE:
                    playerGI.setPlayerSpawn(mapManager.getGameMap().getBluePlayerSpawn());
                    break;
                case GREEN:
                    playerGI.setPlayerSpawn(mapManager.getGameMap().getGreenPlayerSpawn());
                    break;
                case YELLOW:
                    playerGI.setPlayerSpawn(mapManager.getGameMap().getYellowPlayerSpawn());
                    break;
            }
            playerGameInfo.put(uuid, playerGI);
            player.sendMessage(ChatColor.AQUA + "The game has started!");
            Location loc = playerGI.getPlayerSpawn();
            loc.setYaw(player.getLocation().getYaw());
            loc.setPitch(player.getLocation().getPitch());
            player.teleport(loc);
            player.playSound(loc, Sound.ENTITY_ILLUSIONER_PREPARE_BLINDNESS,1,1 );
            player.playSound(loc, Sound.ENTITY_ENDERMAN_TELEPORT, 0.5F,1 );
            player.setGameMode(GameMode.SURVIVAL);
            kit.giveKit(player);
        }
    }
    private void initiateOvertime() {
        overtime = true;
        mapManager.initiateOvertime();
        seconds = OVERTIMESECONDS;
        gameState = GameState.OVERTIME;
        for (UUID uuid : currentPlayerList) {
            Player player = Bukkit.getPlayer(uuid);
            player.sendTitle(ChatColor.RED + "Overtime!", null, 10, 30, 10);
            player.playSound(player.getLocation(), Sound.ENTITY_RAVAGER_ROAR, 1, 1);
        }
    }
    private void invulnerableTimer(Player player) {
        player.setInvulnerable(true);
        Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
            player.sendMessage(ChatColor.GOLD + "Your spawn invulnerability has worn off");
            player.setInvulnerable(false);
        }, 100);
    }
}
