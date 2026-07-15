package me.bananplayss.castlewars.api.kits;

import org.bukkit.entity.Player;

import java.util.Map;

public interface KitManager {
    Map<String, Kit> getKits();

    Kit createKit(Player player, String name);

    void deleteKit(String name);

    Kit getKit(String name);
    boolean isExists(String name);
}


