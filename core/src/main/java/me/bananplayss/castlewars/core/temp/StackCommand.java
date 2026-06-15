package me.bananplayss.castlewars.core.temp;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;
import org.jspecify.annotations.NonNull;

public class StackCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command stackcommand, @NotNull String label, @NonNull @NotNull String[] args) {
        Player player = (Player) sender;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        if (itemStack.getType() != Material.AIR) {
            itemStack.setAmount(itemStack.getMaxStackSize());
            player.sendMessage("Buzi vagy, ezért max stacket kaptál " + itemStack.displayName() + " itemből. Te kis buziii");
        }

        
        return false;
    }
}
