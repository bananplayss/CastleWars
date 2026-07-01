package me.bananplayss.castlewars.api.effects;

import org.bukkit.Location;

public interface EffectManager {

    void oneShotEffect(CustomEffect effect, Location spawn);
    void removeLoopEffect(CustomEffect effect);
    void addLoopEffect(CustomEffect effect, Location spawn);
}
