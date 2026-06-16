package me.bananplayss.castlewars.core.kits;

import lombok.Getter;
import me.bananplayss.castlewars.api.kits.Kit;
import me.bananplayss.castlewars.api.kits.KitManager;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

@Getter
public class KitManagerImpl implements KitManager {
    private final Map<String, Kit> kits;

    public KitManagerImpl(){
        this.kits = new HashMap<>();
    }

    public Kit createKit(Player player, String name){
        Kit kit = new Kit(name, Arrays.stream(player.getInventory().getContents()).toList());
        this.kits.put(name, kit);

        return kit;
    }
}
