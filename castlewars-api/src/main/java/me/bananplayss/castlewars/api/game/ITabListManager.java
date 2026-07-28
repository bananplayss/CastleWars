package me.bananplayss.castlewars.api.game;

import org.bukkit.entity.Player;

public interface ITabListManager {

    Game getGame();
    void setFormat(Player player);
    void resetFormat(Player player);
}
