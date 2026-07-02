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
        player.getInventory().setContents(this.items.toArray(new ItemStack[0]));
        if (helmet != null && helmet.getType() != Material.AIR) {
            player.getInventory().setHelmet(helmet);
        }
    }

    public void giveHelmet(Player player) {
        try {
            player.getInventory().setHelmet(this.items.get(40));
        } catch (IndexOutOfBoundsException ignored) {
            player.getInventory().setHelmet(null);
        }
    }
}
