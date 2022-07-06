package me.splerix.bowbattle;

import me.splerix.bowbattle.Commands.Admin.Dev;
import me.splerix.bowbattle.Commands.Admin.Gamemode.Adventure;
import me.splerix.bowbattle.Commands.Admin.Gamemode.Creative;
import me.splerix.bowbattle.Commands.Admin.Gamemode.Spectator;
import me.splerix.bowbattle.Commands.Admin.Gamemode.Survival;
import me.splerix.bowbattle.Commands.Admin.Heal;
import me.splerix.bowbattle.Commands.Game.Admin.ForceEnd;
import me.splerix.bowbattle.Commands.Game.Admin.ForceStart;
import me.splerix.bowbattle.Commands.Game.Player.GameFinder;
import me.splerix.bowbattle.Commands.Game.Player.LeaveQueue;
import me.splerix.bowbattle.Commands.Game.Player.Queue;
import me.splerix.bowbattle.Commands.Player.Warps.Deathmatch;
import me.splerix.bowbattle.Commands.Player.Warps.Spawn;
import me.splerix.bowbattle.Features.Chat.ChatFormater;
import me.splerix.bowbattle.Features.World.ArrowProtection;
import me.splerix.bowbattle.Features.World.BlockProtection;
import me.splerix.bowbattle.Features.Deathmatch.DeathmatchListeners;
import me.splerix.bowbattle.Game.Game.GameListeners;
import me.splerix.bowbattle.Game.Manager;
import me.splerix.bowbattle.Game.Queue.QueueListeners;
import me.splerix.bowbattle.Features.Spawn.SpawnListeners;
import me.splerix.bowbattle.Game.Queue.GameFinderGUIManager;
import me.splerix.bowbattle.Game.Spectator.SpectatorGUIManager;
import me.splerix.bowbattle.Objects.Player.PlayerList;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

public final class BowBattle extends JavaPlugin {

    PlayerList playerList;
    Manager manager;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        registerCommands();
        registerEvents();
        playerList = new PlayerList();
        playerList.onEnable();
        manager = new Manager(this);
    }

    @Override
    public void onDisable() {
        manager.onDisable();
        playerList.onDisable();
    }

    private void registerCommands() {
        //Admin
        this.getCommand("dev").setExecutor(new Dev());
        this.getCommand("heal").setExecutor(new Heal());
        this.getCommand("bowbattle").setExecutor(new me.splerix.bowbattle.Commands.Admin.BowBattle(this));
        //Gamemodes
        this.getCommand("gma").setExecutor(new Adventure());
        this.getCommand("gmsp").setExecutor(new Spectator());
        this.getCommand("gmc").setExecutor(new Creative());
        this.getCommand("gms").setExecutor(new Survival());
        //Game
        this.getCommand("forcestart").setExecutor(new ForceStart());
        this.getCommand("forceend").setExecutor(new ForceEnd());
        //Pre Game/Post Game
        this.getCommand("gamefinder").setExecutor(new GameFinder());
        this.getCommand("queue").setExecutor(new Queue());
        this.getCommand("leavequeue").setExecutor(new LeaveQueue());
        this.getCommand("spectator").setExecutor(new me.splerix.bowbattle.Commands.Player.Spectator());
        //Warps
        this.getCommand("spawn").setExecutor(new Spawn());
        this.getCommand("hub").setExecutor(new Spawn());
        this.getCommand("deathmatch").setExecutor(new Deathmatch());
    }

    private void registerEvents() {
        //world
        Bukkit.getPluginManager().registerEvents(new BlockProtection(), this);
        Bukkit.getPluginManager().registerEvents(new ArrowProtection(), this);
        //CHat
        Bukkit.getPluginManager().registerEvents(new ChatFormater(this), this);

        //Objects
        Bukkit.getPluginManager().registerEvents(new PlayerList(), this);
        //Game
        Bukkit.getPluginManager().registerEvents(new GameListeners(), this);
        //Queue
        Bukkit.getPluginManager().registerEvents(new QueueListeners(), this);
        Bukkit.getPluginManager().registerEvents(new GameFinderGUIManager(), this);
        //Spawn
        Bukkit.getPluginManager().registerEvents(new SpawnListeners(), this);
        //Deathmatch
        Bukkit.getPluginManager().registerEvents(new DeathmatchListeners(), this);
        //Spectator
        Bukkit.getPluginManager().registerEvents(new SpectatorGUIManager(), this);
    }
}