package me.bananplayss.castlewars.api.effects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.util.Consumer;

@Getter
public abstract class CustomEffect {

    @Setter
    private Location spawnLocation;
    private final long duration;
    @Setter private long startTime;
    private Color baseColor;
    @Setter private boolean looping = false;
    //Self cancel in Effect manager
    @Setter
    private boolean cancel = false;
    private long delay;
    @Setter
    private long nextRun;

    private Consumer<CustomEffect> endConsumer;

    public CustomEffect(Color baseColor, long duration, long delay) {
        this.duration = duration;
        this.baseColor = baseColor;
        this.delay = delay;
    }
    public CustomEffect(Color baseColor, long duration) {
        this(baseColor, duration, 0);
    }

    public void setEnd(Consumer<CustomEffect> consumer) {
        this.endConsumer = consumer;
    }

    public CustomEffect end(Consumer<CustomEffect> consumer) {
        this.setEnd(consumer);
        return this;
    }

    public CustomEffect delay(long delay) {
        this.delay = delay;
        return this;
    }

    public abstract void spawn(Location spawnLocation);

    public void onEnd() {
        if(this.endConsumer != null)
            this.endConsumer.accept(this);
    }

}
