package me.bananplayss.castlewars.core.hooks.tab;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.game.ITabListManager;
import me.bananplayss.castlewars.api.teams.AbstractTeam;
import me.bananplayss.castlewars.api.teams.game.AbstractGameTeam;
import me.bananplayss.castlewars.core.Main;
import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.TabPlayer;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.Nullable;

@Getter
public class TABTabListManager implements ITabListManager {

    private final Game game;

    public TABTabListManager(Game game) {
        this.game = game;
    }

    public void setFormat(Player player) {
        AbstractGameTeam t = this.game.getTeam(player);
        if(t == null) {
            resetFormat(player);
            return;
        }
        setPrefix(player, t.getTeam());
    }

    private void setPrefix(Player player, @Nullable AbstractTeam t) {
        if(Main.getInstance().getHookManager().isTab()) {
            TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());
            if(tabPlayer == null) return;
            tabPlayer.setTemporaryGroup(t.getKey());
        }
    }

    public void resetFormat(Player player) {
        if(Main.getInstance().getHookManager().isTab()) {
            TabPlayer tabPlayer = TabAPI.getInstance().getPlayer(player.getUniqueId());
            if(tabPlayer == null) return;
            tabPlayer.setTemporaryGroup(null);
        }
    }
}
