package me.bananplayss.castlewars.api.teams;

import lombok.Getter;
import me.bananplayss.castlewars.api.utils.BoundingBox;
import me.bananplayss.castlewars.api.utils.vectors.Vector3i;
import me.bananplayss.castlewars.api.utils.vectors.VectorLocation;

@Getter
public class NexusTeam extends AbstractTeam {

    private final Vector3i block;

    public NexusTeam(String key, String prefix, String displayName, String color, Vector3i corner1, Vector3i corner2, VectorLocation spawn, Vector3i block) {
        super(key, prefix, displayName, color, spawn, corner1, corner2);
        this.block = block;
    }
}
