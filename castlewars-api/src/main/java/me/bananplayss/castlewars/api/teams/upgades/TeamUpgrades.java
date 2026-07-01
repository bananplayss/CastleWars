package me.bananplayss.castlewars.api.teams.upgades;

import me.bananplayss.castlewars.api.teams.traps.TrapType;

import java.util.List;
import java.util.Map;

public class TeamUpgrades {

    private final Map<TeamUpgrades, Integer> upgrades;
    private List<TrapType> traps;

    public TeamUpgrades() {
        this.upgrades = Map.of();
        this.traps = List.of();
    }
}
