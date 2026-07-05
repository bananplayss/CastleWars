package me.bananplayss.castlewars.core.profiles;

import lombok.Getter;
import lombok.Setter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.profiles.Profile;
import me.bananplayss.castlewars.api.profiles.ProfileStatistics;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import org.bukkit.Bukkit;
import org.bukkit.boss.BossBar;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

@Getter
public class ProfileImpl implements Profile {

    private final UUID uniqueId;
    private final ProfileSpectatorImpl spectator;
    private final ProfileStatistics statistics;

    private Game currentGame;
    private AbstractGameTeam team;

    private ItemStack[] inventory;

    public ProfileImpl(UUID uniqueId) {
        this.uniqueId = uniqueId;

        this.spectator = new ProfileSpectatorImpl(this);
        this.statistics = new ProfileStatistics(this);
    }

    public void join(Player player) {
        this.spectator.setPlayer(player);
    }

    @Override
    public void setCurrentGame(Game game) {
        this.currentGame = game;
    }

    @Override
    public void setTeam(AbstractGameTeam team) {
        this.team = team;
    }

    public void saveInventory() {
        Player p = Bukkit.getPlayer(this.uniqueId);
        if(p == null) return;
        this.inventory = p.getInventory().getContents();
    }

    public void restoreInventory() {
        this.spectator.getPlayer().getInventory().setContents(this.inventory);
    }
}
