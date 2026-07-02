package me.bananplayss.castlewars.api.events.flags;

import lombok.Getter;
import me.bananplayss.castlewars.api.events.CastleWarsEvent;
import me.bananplayss.castlewars.api.game.Game;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;

@Getter
public class CastleWarsFlagDropEvent extends CastleWarsEvent {

    /**
     * Who dropped the flag
     */
    private final Player player;

    /**
     * Which team's flag was dropped
     */
    private final GameFlagTeam team;

    /**
     * The location where the flag got dropped (exact banner location)
     */
    private final Location location;

//    private boolean cancelled;

    public CastleWarsFlagDropEvent(Game game, Player player, GameFlagTeam team, Location location) {
        super(game);
        this.player = player;
        this.team = team;
        this.location = location;
    }

//    @Override
//    public boolean isCancelled() {
//        return cancelled;
//    }
//
//    @Override
//    public void setCancelled(boolean b) {
//        cancelled = b;
//    }
}