package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        Profile prof = ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if(prof == null) return;
        if(prof.getCurrentGame() == null) return;

        prof.getCurrentGame().leave(e.getPlayer());
    }
}
