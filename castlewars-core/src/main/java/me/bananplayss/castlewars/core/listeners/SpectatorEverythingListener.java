package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDropItemEvent;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;

public class SpectatorEverythingListener implements Listener {

    public ProfileImpl isInGame(Player p) {
        ProfileImpl prof = ProfileCacheImpl.getProfileImpl(p);
        if(prof == null) return null;
        if(prof.getCurrentGame() == null) return null;

        return prof;
    }

    public ProfileImpl getSpectateProfile(Player p) {
        ProfileImpl prof = ProfileCacheImpl.getProfileImpl(p);
        if(prof == null) return null;
        if(prof.getCurrentGame() == null) return null;

        return prof.getCurrentGame().getSpectateManager().isSpectating(p) ? prof : null;
    }


    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemPickup(EntityPickupItemEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;

        ProfileImpl prof = getSpectateProfile(p);
        if(prof == null) return;

        e.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onItemDrop(EntityDropItemEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;
        ProfileImpl prof = getSpectateProfile(p);
        if(prof == null) return;

        e.setCancelled(true);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockInteract(PlayerInteractEvent e) {
        ProfileImpl prof = getSpectateProfile(e.getPlayer());
        if (prof == null) return;

        e.setCancelled(true);
        e.setUseInteractedBlock(Event.Result.DENY);
        e.setUseItemInHand(Event.Result.DENY);
    }
    @EventHandler(priority = EventPriority.LOWEST)
    public void onInteractEntity(PlayerInteractEntityEvent e) {
        ProfileImpl prof = getSpectateProfile(e.getPlayer());
        if (prof == null) return;
        e.setCancelled(true);
    }
}
