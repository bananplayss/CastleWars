package me.bananplayss.castlewars.core.listeners;

import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;
import me.bananplayss.castlewars.core.utils.Async;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

public class PlayerJoinListener implements Listener {

    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Async.execute(() -> Main.getInstance().getProfileCache().loadProfile(e.getPlayer().getUniqueId()), future -> {
            future.thenAccept(p -> {
                ProfileImpl prof = (ProfileImpl) p;
                prof.join(e.getPlayer());

                if(prof.getInventory() != null) {
                    prof.restoreInventory();
                }
            });
        });
    }
}
