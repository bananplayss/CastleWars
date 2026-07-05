package me.bananplayss.castlewars.api.game.action;

import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;
import org.bukkit.Bukkit;
import org.bukkit.boss.BarColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class GameActionManager {

    private final Game game;
    private final List<GameAction> actions;
    private long startTime;

    public GameActionManager(Game game) {
        this.game = game;
        this.actions = new CopyOnWriteArrayList<>();
    }

    public void start() {
        this.startTime = System.currentTimeMillis();

        actions.sort(Comparator.comparingLong(GameAction::getDelay));
    }

    public void stop() {
        this.startTime = 0;
    }

    public void tick() {
        if(this.actions.isEmpty()) return;
        if(startTime == 0) return;
        long unixTime = System.currentTimeMillis();
        long diff = unixTime - startTime;
        for (GameAction action : actions) {
            if(diff >= action.getDelay()) {
                action.apply(this.game);
                this.actions.remove(action);
            }
        }
    }

    public GameAction getNext() {
        return actions.isEmpty() ? null : actions.get(0);
    }

    // next action
    public long getRemainingTime() {
        return actions.isEmpty() ? 0 : actions.get(0).getDelay() - (System.currentTimeMillis() - startTime);
    }
}
