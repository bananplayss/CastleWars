package me.bananplayss.castlewars.api.kits;


import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public interface Kit {
    List<ItemStack> getItems();
    String getName();
    void give(Player player);
}
