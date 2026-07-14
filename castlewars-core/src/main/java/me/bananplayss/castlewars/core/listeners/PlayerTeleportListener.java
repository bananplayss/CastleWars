package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

public class PlayerTeleportListener implements Listener {

    @EventHandler
    public void onPlayerTeleport(PlayerTeleportEvent e) {
        ProfileImpl prof = ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if(prof == null) return;
        if(prof.getCurrentGame() == null) return;
        if(prof.getTeam() == null) return;

        if(!prof.getCurrentGame().isInside(e.getTo())) {
            prof.getCurrentGame().leave(e.getPlayer());
            e.getPlayer().sendMessage("Ki tpződtél köcsög");
        }
    }
}
