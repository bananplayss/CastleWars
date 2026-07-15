package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.api.game.phases.RunningPhase;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

public class EntityDamageListener implements Listener {

    @EventHandler
    public void onDamage(EntityDamageEvent e) {
        if (!(e.getEntity() instanceof Player p)) return;

        Profile prof = ProfileCacheImpl.getProfileImpl(p);
        if (prof == null) return;
        if (prof.getCurrentGame() == null) return;

        if (!(prof.getCurrentGame().getPhaseManager().getCurrentPhase() instanceof RunningPhase)
                || prof.getCurrentGame().getSpectateManager().isSpectating(p)) {
            e.setCancelled(true);
        }
    }
}
