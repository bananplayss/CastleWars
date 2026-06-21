package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.api.events.flags.CastleWarsFlagPickupEvent;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.game.FlagGameImpl;
import me.bananplayss.castlewars.core.messages.Message;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.EquipmentSlot;

public class PlayerInteractListener implements Listener {

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if(e.getHand() != EquipmentSlot.HAND) return;
        if (e.getAction() != Action.LEFT_CLICK_BLOCK && e.getAction() != Action.RIGHT_CLICK_BLOCK) return;

        if(e.getClickedBlock() == null) return;

        Profile prof = ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if(prof.getCurrentGame() == null) return;
        if(prof.getTeam() == null) return;

        if(prof.getCurrentGame() instanceof FlagGameImpl fg) {
            GameFlagTeam t = fg.getFlagManager().getFlagByBlock(e.getClickedBlock()); // fg.getFlagManager().canPickupAndGetTeam(prof, e.getClickedBlock());
            if(t != null) {
                CastleWarsFlagPickupEvent event = new CastleWarsFlagPickupEvent(fg, e.getPlayer(), t, e.getClickedBlock());
                if(!event.callEvent()) return;

                if (prof.getTeam().getTeam().getKey().equals(t.getTeam().getKey())) {
                    e.getPlayer().sendMessage("Ez a sajat zaszlod te fASZ.");
                    return;
                }

                fg.getFlagManager().pickup(e.getPlayer(), e.getClickedBlock());
                e.getPlayer().setGlowing(true);
                e.getPlayer().getInventory().setHelmet(t.getTeam().getBannerItem());
                fg.broadcast(Message.CAPTURED_FLAG.builder().setPlayer(e.getPlayer()).getComponent());
                System.out.println("Felvette ez a fasz : " + e.getPlayer().getName() + " csapatét: " + t.getTeam().getKey());

                // ToDo: sound effects
            }
        }
    }
}
