package me.bananplayss.castlewars.core.effects;

import me.bananplayss.castlewars.api.effects.CustomEffect;
import me.bananplayss.castlewars.api.teams.game.GameFlagTeam;
import me.bananplayss.castlewars.core.utils.TeamColorConverter;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;


public class RingOuterEffect extends CustomEffect {

    private final int radius;
    private float progress;

    public RingOuterEffect(Color baseColor, long duration, int radius) {
        super(baseColor, duration);
        this.radius = radius;
        this.progress = 0;
    }

    @Override
    public void spawn(Location spawnLocation) {
        progress = (float) (System.currentTimeMillis() - getStartTime()) / getDuration();

        Particle.DustOptions dust = new Particle.DustOptions(getBaseColor(), 0.66f);
        for (double angle = 0; angle < Math.PI * 2; angle += Math.PI / 32) {
            double x = (radius * Math.cos(angle)) * Math.sin(Math.toRadians(10 + (80 * progress)));
            double z = (radius * Math.sin(angle)) * Math.sin(Math.toRadians(10 + (80 * progress)));
            spawnLocation.getWorld().spawnParticle(Particle.REDSTONE, spawnLocation.clone().add(x, 0, z), 0,0,0,0,0.08, dust);
        }
    }


    public static RingOuterEffect create(GameFlagTeam team) {
        return new RingOuterEffect(TeamColorConverter.parseColorOrThrow(team.getTeam().getColor()), 500, 2);
    }
}