package me.bananplayss.castlewars.core.listeners;

import com.destroystokyo.paper.event.player.PlayerArmorChangeEvent;
import com.jeff_media.armorequipevent.ArmorEquipEvent;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ArmorEquipListener implements Listener {

    @EventHandler
    public void onArmor(ArmorEquipEvent e) {
        System.out.println("1111");
        Profile prof = ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if(prof.getCurrentGame() == null) return;
        if(prof.getTeam() == null) return;

        e.setCancelled(true);
    }
}
