package me.bananplayss.castlewars.api.game.action;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;

@Getter
@AllArgsConstructor
public abstract class GameAction {

    protected String displayName;
    protected long delay;

    public abstract void apply(Game game);
}
