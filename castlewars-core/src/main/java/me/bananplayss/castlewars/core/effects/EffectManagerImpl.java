package me.bananplayss.castlewars.core.effects;


import lombok.Getter;
import me.bananplayss.castlewars.api.effects.CustomEffect;
import me.bananplayss.castlewars.api.effects.EffectManager;
import me.bananplayss.castlewars.core.Main;
import org.bukkit.Bukkit;
import org.bukkit.Location;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@Getter
public class EffectManagerImpl implements EffectManager {

    public static final CustomEffect HIT_BLEED = new BleedEffect(0);

    private final List<CustomEffect> loopEffects;

    public EffectManagerImpl() {
        this.loopEffects = new CopyOnWriteArrayList<>();
        effectLoop();
    }
    public void oneShotEffect(CustomEffect effect, Location spawn) {
        effect.setStartTime(System.currentTimeMillis());
        effect.spawn(spawn);
    }

    public void addLoopEffect(CustomEffect effect,Location spawn) {
        effect.setSpawnLocation(spawn);
        this.loopEffects.add(effect);
        effect.setStartTime(System.currentTimeMillis());
       // System.out.println("add loop effect");
    }

    public void removeLoopEffect(CustomEffect effect) {
        this.loopEffects.remove(effect);
    }

    public void effectLoop() {
        Bukkit.getScheduler().runTaskTimer(Main.getInstance(), () -> {
            long unixTime = System.currentTimeMillis();
            for (CustomEffect value : this.loopEffects) {

                if (value.getStartTime() + value.getDuration() < unixTime) {
                    if(value.isLooping()) {
                        value.setStartTime(System.currentTimeMillis());
                        continue;
                    }

                    value.onEnd();
                    removeLoopEffect(value);
                    continue;
                }

                if(value.getNextRun() < unixTime) {
                    value.setNextRun(System.currentTimeMillis() + value.getDelay());
                    value.spawn(value.getSpawnLocation());
                }
            }
        }, 1L, 1L);
    }
}
