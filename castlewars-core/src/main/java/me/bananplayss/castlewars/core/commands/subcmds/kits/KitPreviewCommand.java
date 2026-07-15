package me.bananplayss.castlewars.core.commands.subcmds.kits;

import me.bananplayss.castlewars.api.kits.Kit;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.commands.SubCommand;
import me.bananplayss.castlewars.core.messages.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class KitPreviewCommand implements SubCommand {

    public static class KitPreviewInventoryHolder implements InventoryHolder {
        @Override
        public @NotNull Inventory getInventory() {
            return null;
        }
    }

    @Override
    public String getName() {
        return "previewkit";
    }

    @Override
    public String getDescription() {
        return "Preview kit";
    }

    @Override
    public String getSyntax() {
        return "/castlewars previewkit <kit>";
    }

    @Override
    public String getPermission() {
        return "castlewars.commands.previewkit";
    }

    @Override
    public List<String> getTabCompletion(int index, String[] args) {
        return List.of();
    }

    @Override
    public void perform(CommandSender sender, String[] args) {
        // castlewars deletekit <kit>
        if (!(sender instanceof Player p)) {
            Message.PLAYER_ONLY.builder().send(sender);
            return;
        }

        if(!Main.getInstance().getKitManager().isExists(args[0])) {
            // ToDo: Kit exists message xd
            sender.sendMessage("Kit nem létezik lol");
            return;
        }

        Kit kit = Main.getInstance().getKitManager().getKit(args[0]);
        Inventory inv = Bukkit.createInventory(new KitPreviewInventoryHolder(), 54, "Kit " + kit.getName() + " Preview");

        int c = 0;
        for (ItemStack item : kit.getItems()) {
            if(c == 36) break;
            if(item == null) {
                c++;
                continue;
            }
            inv.setItem(c, item);
            c++;
        }

        inv.setItem(36, kit.getItems().get(39));
        inv.setItem(37, kit.getItems().get(38));
        inv.setItem(38, kit.getItems().get(37));
        inv.setItem(39, kit.getItems().get(36));
        inv.setItem(40, kit.getItems().get(40));

        p.openInventory(inv);
    }
}