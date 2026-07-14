package me.bananplayss.castlewars.core.listeners;

import com.destroystokyo.paper.event.player.PlayerPostRespawnEvent;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class PlayerPostRespawnListener implements Listener {

    @EventHandler
    public void onPostRespawn(PlayerPostRespawnEvent e) {
        Profile prof = ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if(prof == null) return;
        if(prof.getCurrentGame() == null) return;
        if(prof.getTeam() == null) return;

//        e.getPlayer().teleport(prof.getCurrentGame().getTeam(e.getPlayer()).getSpawn());
    }
}
