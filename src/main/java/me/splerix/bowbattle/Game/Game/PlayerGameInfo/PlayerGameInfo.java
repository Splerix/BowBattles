package me.splerix.bowbattle.Game.Game.PlayerGameInfo;

import me.splerix.bowbattle.Objects.Player.PlayerState;
import org.bukkit.Location;

import java.util.UUID;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class PlayerGameInfo {
    private int kills;
    private int deaths;
    private int lifes;
    private boolean alive;

    final PlayerTeam team;
    final UUID uuid;

    private Location playerSpawn;


    public PlayerGameInfo(UUID uuid, PlayerTeam team) {
        this.uuid = uuid;
        this.team = team;


        alive = true;
        lifes = 3;
        kills = 0;
        deaths = 0;

        playerInfo.get(uuid).setPlayerState(PlayerState.INGAME);
    }



    //Getters and setters
    public boolean getAlive() {
        return alive;
    }
    public int getKills() {
        return kills;
    }
    public int getLifes() {
        return lifes;
    }
    public Location getPlayerSpawn() {
        return playerSpawn;
    }
    public PlayerTeam getTeam() {
        return team;
    }

    public void incDeaths(){
        deaths++;
    }
    public void incKills(){
        kills++;
    }
    public void decLifes() {
        lifes--;
    }
    public void setAlive(boolean alive) {
        this.alive = alive;
    }
    public void setPlayerSpawn(Location loc) {
        this.playerSpawn = loc;
    }
    //Privates

}
