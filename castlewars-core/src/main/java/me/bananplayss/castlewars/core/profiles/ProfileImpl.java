package me.bananplayss.castlewars.core.profiles;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.profiles.ProfileStatistics;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;

@Getter
public class ProfileImpl implements Profile {

    private final UUID uniqueId;
    private final ProfileSpectatorImpl spectator;
    private final ProfileStatistics statistics;

    @Setter private Game currentGame;
    @Setter private AbstractGameTeam team;

    private ItemStack[] inventory;

    public ProfileImpl(UUID uniqueId) {
        this.uniqueId = uniqueId;

        this.spectator = new ProfileSpectatorImpl(this);
        this.statistics = new ProfileStatistics(this);

        Main.getInstance().getProfileCache().getProfiles().put(uniqueId, this);

        load();
    }

    public void join(Player player) {
        this.spectator.setPlayer(player);
    }

    public void saveInventory() {
        Player p = Bukkit.getPlayer(this.uniqueId);
        if (p == null) return;
        this.inventory = p.getInventory().getContents().clone();

        Main.getInstance().getFileManager().getPlayerCache().getConfig().set("inventories." + this.uniqueId, this.inventory);
        Main.getInstance().getFileManager().getPlayerCache().save();
    }

    public void restoreInventory() {
        Player p = Bukkit.getPlayer(this.uniqueId);
        if (p == null) return;
        if (this.inventory == null) return;
        p.getInventory().setContents(this.inventory);
        p.updateInventory();

        Main.getInstance().getFileManager().getPlayerCache().getConfig().set("inventories." + this.uniqueId, new ArrayList<>());
        Main.getInstance().getFileManager().getPlayerCache().save();
    }

    public void save() {

    }

    public void load() {
        if (Main.getInstance().getFileManager().getPlayerCache().getConfig().getList("inventories." + this.uniqueId, new ArrayList<>()).isEmpty()) {
            this.inventory = null;
            return;
        }

        ItemStack[] items = Main.getInstance().getFileManager().getPlayerCache().getConfig()
                .getList("inventories." + this.uniqueId, new ArrayList<>())
                .stream()
                .map(item -> (ItemStack) item)
                .toArray(ItemStack[]::new);
        this.inventory = items;
    }
}
