package me.bananplayss.castlewars.core.listeners;

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
        if(!e.isCritical()) return;
        Profile prof = ProfileCacheImpl.getProfileImpl(victim);
        if (prof.getCurrentGame() == null) return;
        if (prof.getTeam() == null) return;

        Main.getInstance().getEffectManager().oneShotEffect(EffectManagerImpl.HIT_BLEED,victim.getLocation().add(0,1,0));
    }
}
