package me.splerix.bowbattle.Objects.Player;

import org.bukkit.entity.Player;

import java.io.Serializable;
import java.util.UUID;

public class PlayerInfo implements Serializable {
    //Stats
    private int killstreak = 0;
    private int highestKillStreak = 0;
    private int kills = 0;
    private int deaths = 0;
    private int gamesPlayed = 0;
    private int gamesWon = 0;

    //Game
    private PlayerState playerState = PlayerState.OUTGAME;
    //Admin
    private boolean devMode;

    //Other
    private UUID playerUUID;
    private boolean online;

    public PlayerInfo(UUID playerUUID) {
        this.playerUUID = playerUUID;
        setDevMode(false);
    }

    //Game
    public PlayerState getPlayerState() {
        return playerState;
    }
    public void setPlayerState(PlayerState playerState) {
        this.playerState = playerState;
    }

    //Admin
    public boolean isDevMode() {
        return devMode;
    }
    public boolean toggleDevMode() {
        devMode = (!(devMode));
        return devMode;
    }
    public void setDevMode(boolean devMode) {
        this.devMode = devMode;
    }

    //Other
    public boolean isOnline() {
        return online;
    }
    public void setOnline(boolean online) {
        this.online = online;
    }
}