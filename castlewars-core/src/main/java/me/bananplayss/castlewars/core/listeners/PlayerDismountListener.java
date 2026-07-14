package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;
import org.bukkit.entity.EnderDragon;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.spigotmc.event.entity.EntityDismountEvent;

public class PlayerDismountListener implements Listener {
    @EventHandler
    public void onDismount(EntityDismountEvent e) {
        if(!(e.getEntity() instanceof Player p)) return;
        ProfileImpl prof = ProfileCacheImpl.getProfileImpl(p);
        if(prof == null) return;
        if(prof.getCurrentGame() == null) return;
        if(!p.getWorld().equals(prof.getCurrentGame().getBaseArena().getWorld())) return;
        if(e.getDismounted() instanceof EnderDragon) {
            e.setCancelled(true);
        }
    }
}
