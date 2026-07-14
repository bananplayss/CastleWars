package me.bananplayss.castlewars.core.utils;

import com.cryptomorin.xseries.XItemStack;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.game.phases.CelebrationPhase;
import me.bananplayss.castlewars.api.game.phases.StartingPhase;
import me.bananplayss.castlewars.api.game.phases.WaitingPhase;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class Utils {
    public static ItemStack getItemFromConfig(ConfigurationSection section) {
        XItemStack.Deserializer des = XItemStack.deserializer().fromConfig(section).withMiniMessage(str -> str.stream().map(ColorParser::parse).toList());

        return des.deserialize();
    }

    public static Material getBannerByBaseColor(DyeColor color) {
        return switch (color) {
            case WHITE -> Material.WHITE_BANNER;
            case ORANGE -> Material.ORANGE_BANNER;
            case MAGENTA -> Material.MAGENTA_BANNER;
            case LIGHT_BLUE -> Material.LIGHT_BLUE_BANNER;
            case YELLOW -> Material.YELLOW_BANNER;
            case LIME -> Material.LIME_BANNER;
            case PINK -> Material.PINK_BANNER;
            case GRAY -> Material.GRAY_BANNER;
            case LIGHT_GRAY -> Material.LIGHT_GRAY_BANNER;
            case CYAN -> Material.CYAN_BANNER;
            case PURPLE -> Material.PURPLE_BANNER;
            case BLUE -> Material.BLUE_BANNER;
            case BROWN -> Material.BROWN_BANNER;
            case GREEN -> Material.GREEN_BANNER;
            case RED -> Material.RED_BANNER;
            case BLACK -> Material.BLACK_BANNER;
        };
    }

    public static String replacePlaceholders(String string, Game game, Player player) {
        if(game.getPhaseManager().getCurrentPhase() instanceof WaitingPhase) {
            string = string.replace("%waiting%", TimeUtils.formatMMSS(game.getPhaseManager().getCurrentPhase().getElapsed()));
        }

        if(game.getPhaseManager().getCurrentPhase() instanceof StartingPhase) {
            string = string.replace("%starting%", (game.getPhaseManager().getCurrentPhase().getRemaining() / 1000L) +"");
        }

        string = string.replace("%elapsed_time%", TimeUtils.formatMMSS(game.getPhaseManager().getCurrentPhase().getElapsed()))
//                .replace("%phase_name%", game.getPhaseManager().getCurrentPhase().getClass().getSimpleName())
                .replace("%players%", game.getAllPlayers().size() +"")
                .replace("%max_players%", game.getBaseArena().getMaxPlayerCount() + "")
                .replace("%gamemode%", game.getBaseArena().getGameMode().name())
                .replace("%map%", game.getBaseArena().getDisplayName())
        ;

        if(game.getActionManager().getNext() != null) {
            string = string.replace("%next_action_name%", game.getActionManager().getNext().getDisplayName())
                    .replace("%next_action_time%", TimeUtils.formatMMSS(game.getActionManager().getRemainingTime()));
        }

        return string;
    }
}
