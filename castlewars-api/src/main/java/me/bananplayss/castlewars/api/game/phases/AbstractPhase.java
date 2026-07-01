package me.bananplayss.castlewars.api.game.phases;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bananplayss.castlewars.api.game.Game;

@Getter
@AllArgsConstructor
public abstract class AbstractPhase {

    protected Game game;

    protected long started;
    protected long duration;
    protected long end;

    public AbstractPhase(Game game, long duration) {
        this.game = game;
        this.started = 0;
        this.duration = duration;
        this.end = 0;
    }

    public void start() {
        this.started = System.currentTimeMillis();
        this.end = this.started + this.duration;
    }

    public long getElapsed() {
        if(this.started == 0) return 0;
        return System.currentTimeMillis() - this.started;
    }

    public long getRemaining() {
        if (this.end == 0) return 0;
        return this.end - System.currentTimeMillis();
    }

    public boolean isFinished() {
        if (this.end == 0) return false;
        return System.currentTimeMillis() >= this.end;
    }

    public abstract void onStart();
    public abstract void onUpdate();
    public abstract void onEnd();
}