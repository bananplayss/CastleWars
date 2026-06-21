package me.bananplayss.castlewars.api.events.flags;

import lombok.Getter;
import me.bananplayss.castlewars.api.events.CastleWarsEvent;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Getter
public class CastleWarsFlagPickupEvent extends CastleWarsEvent implements Cancellable {

    /**
     * Who picked up the flag
     */
    private final Player player;

    /**
     * Which team's flag was picked up
     */
    private final GameFlagTeam team;

    /**
     * The block where the flag was picked up (exact banner location)
     */
    private final Block block;

    private boolean cancelled;

    public CastleWarsFlagPickupEvent(Game game, Player player, GameFlagTeam team, Block block) {
        super(game);
        this.player = player;
        this.team = team;
        this.block = block;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean b) {
        cancelled = b;
    }
}
