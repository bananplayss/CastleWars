package me.bananplayss.castlewars.api.kits;

import com.cryptomorin.xseries.XMaterial;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@AllArgsConstructor
public class Kit {
    private String name;
    private List<ItemStack> items;

    public void give(Player player) {
        //player.getInventory().clear();
        ItemStack helmet = player.getInventory().getHelmet();
        ItemStack[] contents = this.items.toArray(new ItemStack[0]);
        if (helmet != null && helmet.getType().name().endsWith("_BANNER")) {
            contents[39] = helmet;
        }

        player.getInventory().setContents(contents);
    }

    public void giveHelmet(Player player) {
        try {
            player.getInventory().setHelmet(this.items.get(39));
        } catch (IndexOutOfBoundsException ignored) {
            player.getInventory().setHelmet(null);
        }
    }
}
