package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.core.game.GameSpectateManagerImpl;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.visibility.FakeNameTagManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class SpectateMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        Profile prof = ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if (prof == null) return;
        if (prof.getCurrentGame() == null) return;
        //if (!prof.getCurrentGame().getSpectateManager().isSpectating(e.getPlayer())) return;

//        FakeNameTagManager.FakeNameTag tag = ((GameSpectateManagerImpl) prof.getCurrentGame().getSpectateManager()).getNameTagManager().getTag(e.getPlayer());
//        if(tag == null) return;

//        ((GameSpectateManagerImpl) prof.getCurrentGame().getSpectateManager()).getNameTagManager().move(e.getPlayer());
//        tag.setPreviousLocation(e.getFrom());
    }
}
