package me.splerix.bowbattle.Features.World;

import me.splerix.bowbattle.Objects.Area.Area;
import org.bukkit.Material;
import org.bukkit.entity.ItemFrame;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.hanging.HangingBreakByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import static me.splerix.bowbattle.Objects.Player.PlayerList.playerInfo;

public class BlockProtection implements Listener {

    Area area  = new Area();

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        Player player = e.getPlayer();

        if (!(playerInfo.get(player.getUniqueId()).isDevMode())) e.setCancelled(true);
        if (!(player.hasPermission("bowbattle.admin.perms.placeblocks"))) e.setCancelled(true);
        if (area.inSpawn(player.getLocation()))
            if (!(player.hasPermission("bowbattle.admin.perms.placeblocks.inspawn"))) e.setCancelled(true);

    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        Player player = e.getPlayer();

        if (!(playerInfo.get(player.getUniqueId()).isDevMode())) e.setCancelled(true);
        if (!(player.hasPermission("bowbattle.admin.perms.breakblocks"))) e.setCancelled(true);
        if (area.inSpawn(player.getLocation()))
            if (!(player.hasPermission("bowbattle.admin.perms.breakblocks.inspawn"))) e.setCancelled(true);
    }
    //FRAME THING PROTECTION
    @EventHandler
    public void onItemFrameRotation(PlayerInteractEntityEvent e) {
        if (!(e.getRightClicked() instanceof ItemFrame)) return;
        if (!(area.inSpawn(e.getPlayer()))) return;
        ItemFrame itemFrame = (ItemFrame) e.getRightClicked();
        itemFrame.setInvulnerable(true);
        itemFrame.setFixed(true);
        e.setCancelled(true);
    }

    @EventHandler
    public void onHangingBreakEvent(HangingBreakByEntityEvent e) {
        if(area.inSpawn(e.getRemover().getLocation())) e.setCancelled(true);
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent e) {
        if (playerInfo.get(e.getPlayer().getUniqueId()).isDevMode()) return;
        if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            if(e.getClickedBlock().getType() == Material.SPRUCE_TRAPDOOR)
                e.setCancelled(true);
        }
    }
}
