package me.bananplayss.castlewars.api.teams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bananplayss.castlewars.api.utils.BoundingBox;
import me.bananplayss.castlewars.api.utils.vectors.Vector3i;
import me.bananplayss.castlewars.api.utils.vectors.VectorLocation;

@Getter
@AllArgsConstructor
public abstract class AbstractTeam {

    protected String key;
    protected String prefix;
    protected String displayName;
    protected String color;
//    protected int maxPlayerCount;
    protected VectorLocation spawn;
    protected Vector3i corner1;
    protected Vector3i corner2;
//    private BoundingBox boundingBox;
}
