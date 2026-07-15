package me.bananplayss.castlewars.api.game;

import org.bukkit.entity.Player;

import java.util.List;
import java.util.Map;
import java.util.UUID;

public interface GameSpectateManager {

    List<UUID> getSpectators();
    void addSpectate(Player player);
    void removeSpectate(UUID uniqueId);
    boolean isSpectating(Player player);
}
