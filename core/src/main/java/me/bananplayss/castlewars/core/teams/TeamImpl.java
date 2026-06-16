package me.bananplayss.castlewars.core.teams;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bananplayss.castlewars.api.utils.vectors.VectorLocation;

@Getter
@AllArgsConstructor
public class TeamImpl implements Team {

    private String name;
    private String color;
    private int maxPlayers;
    private VectorLocation location;
    private VectorLocation flagLocation;
}
