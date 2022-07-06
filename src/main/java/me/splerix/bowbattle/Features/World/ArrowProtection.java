package me.splerix.bowbattle.Features.World;


import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ArrowProtection implements Listener {
    @EventHandler
    public void onArrowLand(ProjectileHitEvent e) {
        e.getEntity().remove();
    }

}
