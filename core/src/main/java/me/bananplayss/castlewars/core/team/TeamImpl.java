package me.bananplayss.castlewars.core.team;

import lombok.AllArgsConstructor;
import lombok.Getter;
import me.bananplayss.castlewars.api.team.Team;

@Getter
@AllArgsConstructor
public class TeamImpl implements Team {

    private String name;
    private String color;
    private int maxPlayers;
}
