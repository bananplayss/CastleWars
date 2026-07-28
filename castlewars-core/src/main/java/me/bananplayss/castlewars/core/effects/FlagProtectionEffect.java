package me.bananplayss.castlewars.core.effects;

import com.cryptomorin.xseries.particles.XParticle;
import com.destroystokyo.paper.ParticleBuilder;
import me.bananplayss.castlewars.api.effects.CustomEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;

public class FlagProtectionEffect extends CustomEffect {
    private final float radius = 0.75f;
    public FlagProtectionEffect(long duration,long delay) {
        super(null, duration, delay);
    }

    @Override
    public void spawn(Location spawnLocation) {
        ParticleBuilder b = XParticle.END_ROD.get().builder().color(null, 0.1f).extra(0).count(0);
        for (double y = 0.25; y < 2; y += 0.25) {
            for (double angle = 0; angle < Math.PI * 2; angle += Math.PI / 8) {
                double x = (radius * Math.cos(angle));
                double z = (radius * Math.sin(angle));

                b.location(spawnLocation.clone().add(x, y, z)).spawn();
            }
        }

    }

}
