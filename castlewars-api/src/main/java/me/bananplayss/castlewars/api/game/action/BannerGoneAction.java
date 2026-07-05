package me.bananplayss.castlewars.api.game.action;

import me.bananplayss.castlewars.api.CastleWarsAPI;
import me.bananplayss.castlewars.api.game.FlagGame;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;

import java.util.Map;

public class BannerGoneAction extends GameAction {

    public BannerGoneAction(String displayName, long delay) {
        super(displayName, delay);
    }

    @Override
    public void apply(Game game) {
        if(game instanceof FlagGame fg) {
            fg.setRespawnEnabled(false);
            for (Map.Entry<Location, GameFlagTeam> entry : fg.getFlagManager().getBlockFlags().entrySet()) {
                entry.getKey().getBlock().setType(Material.AIR);
                entry.getValue().setFlagState(GameFlagTeam.FlagState.BANNER_GONE);
                CastleWarsAPI.EFFECT_MANAGER.get().removeLoopEffect(entry.getValue().getRingEffect());
            }
            fg.getFlagManager().getBlockFlags().clear();

            for (Map.Entry<Player, GameFlagTeam> entry : fg.getFlagManager().getCarriedFlags().entrySet()) {
                game.getKit().giveHelmet(entry.getKey());
                entry.getValue().setFlagState(GameFlagTeam.FlagState.BANNER_GONE);
//                CastleWarsAPI.EFFECT_MANAGER.get().removeLoopEffect(entry.getValue().getRingEffect());
            }
            fg.getFlagManager().getCarriedFlags().clear();

            System.out.println("OMG KIÜTÖDTEK A FLAGEK!!!!");
        }
    }
}