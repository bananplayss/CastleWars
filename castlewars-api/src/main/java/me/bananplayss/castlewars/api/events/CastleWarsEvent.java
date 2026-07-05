package me.bananplayss.castlewars.api.events;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.jetbrains.annotations.NotNull;

@Getter
@AllArgsConstructor
public class CastleWarsEvent extends Event {
    private static final HandlerList HANDLERS_LIST = new HandlerList();

    private final Game game;

    @Override
    public @NotNull HandlerList getHandlers() {
        return HANDLERS_LIST;
    }
}
