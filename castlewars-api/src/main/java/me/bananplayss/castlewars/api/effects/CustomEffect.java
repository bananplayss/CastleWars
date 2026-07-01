package me.bananplayss.castlewars.api.effects;

import lombok.Getter;
import lombok.Setter;
import org.bukkit.Color;
import org.bukkit.Location;

@Getter
public abstract class CustomEffect {

    @Setter
    private Location spawnLocation;
    private final long duration;
    @Setter
    private long startTime;
    private Color baseColor;
    @Setter
    private boolean looping = false;
    //Self cancel in Effect manager
    @Setter
    private boolean cancel = false;

    public CustomEffect(Color baseColor, long duration) {
        this.duration = duration;
        this.baseColor = baseColor;
    }


    public abstract void apply(Location spawnLocation);

    public abstract void end();
}
