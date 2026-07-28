package me.bananplayss.castlewars.core.kits;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.kits.Kit;
import me.bananplayss.castlewars.api.kits.KitManager;
import me.bananplayss.castlewars.core.Main;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.*;

@Getter
public class KitManagerImpl implements KitManager {
    private final Map<String, Kit> kits;

    public KitManagerImpl(){
        this.kits = new HashMap<>();

        load();
    }

    public void load() {
        for (String key : Main.getInstance().getFileManager().getKits().getConfig().getKeys(false)) {
            ItemStack[] contents = Main.getInstance().getFileManager().getKits().getConfig()
                    .getList(key, new ArrayList<>())
                    .stream()
                    .map(item -> (ItemStack) item)
                    .toArray(ItemStack[]::new);

            Kit kit = new Kit(key, new ArrayList<>(Arrays.asList(contents)));
            this.kits.put(key, kit);
        }
    }

    public boolean isExists(String name) {
        return this.kits.containsKey(name);
    }

    public Kit createKit(Player player, String name) {
        Kit kit = new Kit(name, Arrays.stream(player.getInventory().getContents()).toList());
        this.kits.put(name, kit);

        return kit;
    }

    public void deleteKit(String name) {
        this.kits.remove(name);
    }

    public Kit getKit(String name) {
        return this.kits.get(name);
    }
}
