package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.api.events.flags.CastleWarsFlagPickupEvent;
import me.bananplayss.castlewars.api.events.flags.CastleWarsFlagResetEvent;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.game.FlagGameImpl;
import me.bananplayss.castlewars.core.messages.Message;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;

public class BlockBreakListener implements Listener {


    @EventHandler
    public void onBreak(BlockBreakEvent e) {
        Profile prof = ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if (prof.getCurrentGame() == null) return;
        if (prof.getTeam() == null) return;

        if (prof.getCurrentGame() instanceof FlagGameImpl fg) {
            GameFlagTeam t = fg.getFlagManager().getFlagByBlock(e.getBlock()); // fg.getFlagManager().canPickupAndGetTeam(prof, e.getClickedBlock());
            if (t != null) {
                e.setCancelled(true);
                if (!prof.getTeam().getTeam().getKey().equals(t.getTeam().getKey())) {
                    e.getPlayer().sendMessage("Ez nem a te saját zászlód te fasz.");
                    return;
                }

                CastleWarsFlagResetEvent event = new CastleWarsFlagResetEvent(fg, e.getPlayer(), t, e.getBlock());
                if (!event.callEvent()) return;

                //fg.broadcast(Message.RESE.builder().setPlayer(e.getPlayer()).getComponent());

                e.getBlock().setType(Material.AIR);
//                fg.getFlagManager().resetFlag(e.getPlayer(), t);
                fg.getFlagManager().resetFlag(e.getBlock(), t);

                System.out.println("REsetted flag : " + e.getPlayer().getName() + " csapatét: " + t.getTeam().getKey());

            }
        }
    }
}
