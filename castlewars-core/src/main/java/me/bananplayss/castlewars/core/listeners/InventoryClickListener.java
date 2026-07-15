package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.core.commands.subcmds.kits.KitPreviewCommand;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if(!e.getView().getTopInventory().equals(e.getClickedInventory())) return;
        if(e.getView().getTopInventory().getHolder() instanceof KitPreviewCommand.KitPreviewInventoryHolder) {
            e.setCancelled(true);
        }
    }
}
