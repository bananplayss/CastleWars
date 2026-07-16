package me.bananplayss.castlewars.core.listeners;

import io.papermc.paper.event.player.AsyncChatEvent;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.core.messages.Message;
import me.bananplayss.castlewars.core.profiles.ProfileCacheImpl;
import me.bananplayss.castlewars.core.profiles.ProfileImpl;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class PlayerChatListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent e) {
        ProfileImpl prof = ProfileCacheImpl.getProfileImpl(e.getPlayer());
        if(prof == null) return;

        e.getRecipients().removeIf(p -> {
            ProfileImpl target = ProfileCacheImpl.getProfileImpl(p);
            return target.getCurrentGame() != null;
        });

        if (prof.getCurrentGame() == null) return;
        if (prof.getTeam() == null) return;

        e.setCancelled(true);
        Game g = prof.getCurrentGame();

        // Global chat prefix !
        if (e.getMessage().startsWith("!")) {
            String msg = e.getMessage().replaceFirst("!", "");
            for (Player allPlayer : g.getAllPlayers()) {
                allPlayer.sendMessage(
                        Message.CHAT_GLOBAL.builder()
                                .setTeam(prof.getTeam())
                                .setMsg(msg.strip())
                                .setPlayer(e.getPlayer())
                                .getComponent(e.getPlayer())
                );
            }
        } else {
            //Team chat:
            for (Player player : prof.getTeam().getPlayers()) {
                player.sendMessage(
                        Message.CHAT_TEAM.builder()
                                .setTeam(prof.getTeam())
                                .setMsg(e.getMessage())
                                .setPlayer(e.getPlayer())
                                .getComponent(e.getPlayer())
                );
            }
        }
    }
}
