package me.bananplayss.castlewars.api.utils;

import org.bukkit.entity.Player;

import java.util.Map;

public interface RespawnManager {

    Map<Player, Long> getRespawns();

    boolean isDead(Player player);
    void addRespawn(Player player);
    void removeRespawn(Player player);
}
