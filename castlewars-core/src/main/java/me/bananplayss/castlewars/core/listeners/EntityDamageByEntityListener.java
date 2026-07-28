package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.api.game.phases.RunningPhase;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.effects.EffectManagerImpl;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class EntityDamageByEntityListener implements Listener {

    @EventHandler
    public void onHit(EntityDamageByEntityEvent e) {
        if (!(e.getDamager() instanceof Player damager)) return;
        if (!(e.getEntity() instanceof Player victim)) return;

        Profile prof = ProfileCacheImpl.getProfileImpl(victim);
        if(prof == null) return;
        if (prof.getCurrentGame() == null) return;

        if(prof.getCurrentGame().getSpectateManager().isSpectating(damager)) {
            e.setCancelled(true);
            return;
        }

//        if(!(prof.getCurrentGame().getPhaseManager().getCurrentPhase() instanceof RunningPhase)) {
//            e.setCancelled(true);
//            return;
//        }

        Profile damagerProf = ProfileCacheImpl.getProfileImpl(damager);
        if(damagerProf == null) return;
        if(damagerProf.getTeam().equals(prof.getTeam())) {
            e.setCancelled(true);
            return;
        }



        if(!e.isCritical()) return;
//        if (prof.getTeam() == null) return;

        Main.getInstance().getEffectManager().oneShotEffect(EffectManagerImpl.HIT_BLEED,victim.getLocation().add(0,1,0));
    }
}
