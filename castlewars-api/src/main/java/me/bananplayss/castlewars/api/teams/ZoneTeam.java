package me.bananplayss.castlewars.api.teams;

import lombok.Getter;
import me.bananplayss.castlewars.api.utils.BoundingBox;
import me.bananplayss.castlewars.api.utils.vectors.Vector3i;
import me.bananplayss.castlewars.api.utils.vectors.VectorLocation;

@Getter
public class ZoneTeam extends AbstractTeam {

    private final Vector3i zone;
    private final int radius; // mint négyzet

    public ZoneTeam(String key, String prefix, String displayName, String color, VectorLocation spawn, Vector3i corner1, Vector3i corner2, Vector3i zone, int radius) {
        super(key, prefix, displayName, color, spawn, corner1, corner2);
        this.zone = zone;
        this.radius = radius;
    }
}
