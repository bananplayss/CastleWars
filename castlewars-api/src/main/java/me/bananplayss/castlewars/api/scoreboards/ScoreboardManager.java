package me.bananplayss.castlewars.api.scoreboards;

import me.bananplayss.castlewars.api.game.Game;
import org.bukkit.entity.Player;

public interface ScoreboardManager {

    void show(Player player, Game game);
    void delete(Player player);
}
