package me.bananplayss.castlewars.core.effects;

import me.bananplayss.castlewars.api.effects.CustomEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;

public class BoundingBoxVolumeEffect extends CustomEffect {

    private final BoundingBox box;

    private Particle particle;

    private int numIterations;
    /**
     * Custom boxhoz, ha nincs attach.
     */
    public BoundingBoxVolumeEffect(Particle particle, long duration, BoundingBox box, int numIterations) {
        super(null, duration);
        this.box = box;
        this.particle = particle;
        this.numIterations = numIterations;
    }

    @Override
    public void apply(Location spawnLocation) {

        World world = spawnLocation.getWorld();

        if (world == null) {
            return;
        }

        double minX = box.getMinX();
        double minY = box.getMinY();
        double minZ = box.getMinZ();

        double maxX = box.getMaxX();
        double maxY = box.getMaxY();
        double maxZ = box.getMaxZ();
        for (int i = 0; i < this.numIterations; i++) {
            double randomX = minX + (maxX - minX) * Math.random();
            double randomY = minY + (maxY - minY) * Math.random();
            double randomZ = minZ + (maxZ - minZ) * Math.random();
            world.spawnParticle(particle, randomX, randomY, randomZ, 1);
        }
    }



    @Override
    public void end() {

    }
}