package me.bananplayss.castlewars.core.effects;

import com.cryptomorin.xseries.base.XRegistry;
import com.cryptomorin.xseries.particles.XParticle;
import com.destroystokyo.paper.ParticleBuilder;
import me.bananplayss.castlewars.api.effects.CustomEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.Nullable;

public class RingFillEffect extends CustomEffect {

    private int radius;
    private Entity attach;
    private float progress;


    public RingFillEffect(Color baseColor, long duration, int radius, @Nullable Entity attach) {
        super(baseColor,duration);
        this.radius = radius;
        this.attach = attach;
        this.progress = 0;
    }

    @Override
    public void spawn(Location spawnLocation) {
        if (attach != null) spawnLocation = attach.getLocation();
        if (spawnLocation.getWorld() == null) return;

        double progress = (System.currentTimeMillis() - getStartTime()) / (double) getDuration();
        progress = Math.max(0, Math.min(1, progress));

        //double currentRadius = radius * Math.sin(progress * Math.PI / 2);

        ParticleBuilder builder = XParticle.DUST.get().builder().location(spawnLocation).count(1).color(getBaseColor(), 0.66f).allPlayers().spawn();
        double currentRadius = radius * (Math.sin(Math.toRadians(10 + 80 * progress)) - Math.sin(Math.toRadians(10))) / (1 - Math.sin(Math.toRadians(10)));

//        spawnLocation.getWorld().spawnParticle(
//                Particle.REDSTONE, spawnLocation, 1,
//                0, 0, 0, 0, dust
//        );

        double spacing = 0.25;
        for (double ringRadius = spacing; ringRadius <= currentRadius; ringRadius += spacing) {
            int particleCount = Math.max(8, (int) Math.ceil(2 * Math.PI * ringRadius / spacing));

            for (int i = 0; i < particleCount; i++) {
                double angle = 2 * Math.PI * i / particleCount;
                double x = ringRadius * Math.cos(angle);
                double z = ringRadius * Math.sin(angle);

                builder.location(spawnLocation.clone().add(x, 0, z)).spawn();

//                spawnLocation.getWorld().spawnParticle(
//                        Particle.REDSTONE,
//                        spawnLocation.clone().add(x, 0, z),
//                        1, 0, 0, 0, 0, dust
//                );
            }
        }
    }
}
