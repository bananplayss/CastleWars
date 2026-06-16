package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.core.Main;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockZ() == e.getTo().getBlockZ() && e.getFrom().getBlockY() == e.getTo().getBlockY()) return;

        Profile prof =  Main.getInstance().getProfileCache().getProfile(e.getPlayer());
    }
}
