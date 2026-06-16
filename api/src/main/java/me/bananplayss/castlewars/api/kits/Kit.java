package me.bananplayss.castlewars.api.kits;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

@Getter
@AllArgsConstructor
public class Kit {
    private String name;
    private List<ItemStack> items;

    public void give(Player player){
        //player.getInventory().clear();
        player.getInventory().setContents(this.items.toArray(new ItemStack[0]));
    }

}
