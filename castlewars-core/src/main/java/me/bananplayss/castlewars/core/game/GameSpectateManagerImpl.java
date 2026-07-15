package me.bananplayss.castlewars.core.game;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.game.GameSpectateManager;
import me.bananplayss.castlewars.core.Main;
import me.bananplayss.castlewars.core.kobalib.colors.ColorParser;
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

    public GameSpectateManagerImpl(Game game) {
        this.game = game;
        this.spectators = new ArrayList<>();
    }

    @Override
    public void addSpectate(Player player) {
        this.spectators.add(player.getUniqueId());

        ArmorStand as = getNameTag(player);
        if (as != null) {
            as.remove();
        }

        as = spawnNameTag(player);

        player.hideEntity(Main.getInstance(), as);
        player.addPassenger(as);

        for (Player target : this.game.getAllPlayers()) {
            if (player == target) continue;
            if (isSpectating(target)) {
                target.hidePlayer(Main.getInstance(), player);
                target.showEntity(Main.getInstance(), as);

                player.hidePlayer(Main.getInstance(), target);
                ArmorStand tAs = getNameTag(target);
                if (tAs != null) {
                    player.showEntity(Main.getInstance(), tAs);
                }
                continue;
            }

            target.hidePlayer(Main.getInstance(), player);
            target.hideEntity(Main.getInstance(), as);
        }
        //Todo: ellenőrizni /\
    }

    @Override
    public void removeSpectate(UUID uniqueId) {
        this.spectators.remove(uniqueId);

        Player p = Bukkit.getPlayer(uniqueId);
        if (p != null) {
            ArmorStand as = getNameTag(p);
            if (as != null) {
                as.remove();
            }

            for (Player allPlayer : this.game.getAllPlayers()) {
                if (allPlayer == p) continue;
                allPlayer.showPlayer(Main.getInstance(), p);

                if (!isSpectating(allPlayer))
                    p.showPlayer(Main.getInstance(), allPlayer);
            }
        }
    }

    @Override
    public boolean isSpectating(Player player) {
        return this.spectators.contains(player.getUniqueId());
    }

    private ArmorStand getNameTag(Player player) {
        return player.getPassengers()
                .stream()
                .filter(as -> as instanceof ArmorStand)
                .filter(a -> a.getPersistentDataContainer().has(key, PersistentDataType.BYTE))
                .map(as -> (ArmorStand) as)
                .findFirst().orElse(null);
    }

    private ArmorStand spawnNameTag(Player player) {
        ArmorStand as = player.getWorld().spawn(player.getLocation(), ArmorStand.class);
        as.customName(ColorParser.parse("&7" + player.getName()));
        as.setCustomNameVisible(true);
        as.setGravity(false);
        as.setInvulnerable(true);
        as.setVisible(false);

        as.getPersistentDataContainer().set(key, PersistentDataType.BYTE, (byte) 1);


        return as;
    }
}

