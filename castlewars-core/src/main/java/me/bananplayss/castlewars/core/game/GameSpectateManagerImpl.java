package me.bananplayss.castlewars.core.game;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.game.GameSpectateManager;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
import me.bananplayss.castlewars.core.visibility.FakeNameTagManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Getter
public class GameSpectateManagerImpl implements GameSpectateManager {

    private static final NamespacedKey key = new NamespacedKey(Main.getInstance(), "spectate_nametag");

    private final Game game;
    private final List<UUID> spectators;

    private final FakeNameTagManager nameTagManager;

    public GameSpectateManagerImpl(Game game) {
        this.game = game;
        this.spectators = new ArrayList<>();
        this.nameTagManager = new FakeNameTagManager(game);
    }

    @Override
    public void addSpectate(Player player) {
        this.spectators.add(player.getUniqueId());

        this.nameTagManager.setSpectate(player);

        for (Player target : this.game.getAllPlayers()) {
            if (player == target) continue;
            if (isSpectating(target)) {
                player.hidePlayer(Main.getInstance(), target);
            }

            target.hidePlayer(Main.getInstance(), player);
        }

//        ArmorStand as = getNameTag(player);
//        if (as != null) {
//            as.remove();
//        }
//
//        as = spawnNameTag(player);
//
//        player.hideEntity(Main.getInstance(), as);
//        player.addPassenger(as);
//
//        for (Player target : this.game.getAllPlayers()) {
//            if (player == target) continue;
//            if (isSpectating(target)) {
//                target.hidePlayer(Main.getInstance(), player);
//                target.showEntity(Main.getInstance(), as);
//
//                player.hidePlayer(Main.getInstance(), target);
//                ArmorStand tAs = getNameTag(target);
//                if (tAs != null) {
//                    player.showEntity(Main.getInstance(), tAs);
//                }
//                continue;
//            }
//
//            target.hidePlayer(Main.getInstance(), player);
//            target.hideEntity(Main.getInstance(), as);
//        }
        //Todo: ellenőrizni /\
    }

    @Override
    public void removeSpectate(UUID uniqueId) {
        this.spectators.remove(uniqueId);

        Player p = Bukkit.getPlayer(uniqueId);
        if (p != null) {
            this.nameTagManager.removeSpectate(p);

            for (Player allPlayer : this.game.getAllPlayers()) {
                if (allPlayer == p) continue;
                allPlayer.showPlayer(Main.getInstance(), p);

                if (isSpectating(allPlayer)) {
                    p.hidePlayer(Main.getInstance(), allPlayer);
                } else {
                    p.showPlayer(Main.getInstance(), allPlayer);
                }
            }
        }
    }

    @Override
    public boolean isSpectating(Player player) {
        return this.spectators.contains(player.getUniqueId());
    }
}

