package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.effects.BleedEffect;
import me.bananplayss.castlewars.core.game.FlagGameImpl;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.utils.LocationUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class PlayerMoveListener implements Listener {

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (e.getFrom().getBlockX() == e.getTo().getBlockX() && e.getFrom().getBlockZ() == e.getTo().getBlockZ() && e.getFrom().getBlockY() == e.getTo().getBlockY()) return;
        //BleedEffect a = new BleedEffect(e.getPlayer().getLocation(), 1000);
        //a.apply();
        Profile prof =  ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if(prof.getCurrentGame() == null) return;

        if(prof.getCurrentGame() instanceof FlagGameImpl fg) {
            if (fg.getFlagManager().getCarriedFlags().containsKey(e.getPlayer())) {
                GameFlagTeam ft = fg.getTeam(e.getPlayer());

                //if(LocationUtils.isInside(e.getPlayer().getLocation(), ft.getBoundingBox())) {
                if(ft.getBoundingBox().contains(e.getPlayer().getLocation().toVector())) {
                    GameFlagTeam bevittBannerTeam = fg.getFlagManager().getCarriedFlags().get(e.getPlayer());
                    fg.getFlagManager().resetFlag(e.getPlayer(), bevittBannerTeam);

                    ft.addScore();

                    e.getPlayer().setGlowing(false);
                    fg.getKit().giveHelmet(e.getPlayer());


                    System.out.println("Bevitte " + e.getPlayer().getName() + " team: " + bevittBannerTeam.getTeam().getKey() + " bannerjét");
                }
            }
        }
    }
}
