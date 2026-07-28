package me.bananplayss.castlewars.core.effects;

import lombok.Getter;
import me.bananplayss.castlewars.api.effects.CustomEffect;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Particle;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.util.BoundingBox;
import org.jetbrains.annotations.Nullable;

@Getter
public class BoundingBoxEffect extends CustomEffect {

    private final double width;
    private final double height;
    private final double depth;
    private final double spacing;
    private final Entity attach;

    /**
     * Entity bounding boxhoz.
     */
    public BoundingBoxEffect(Color baseColor, long duration, double spacing, @Nullable Entity attach) {
        this(baseColor, duration, 1.0, 2.0, 1.0, spacing, attach);
    }

    /**
     * Custom boxhoz, ha nincs attach.
     */
    public BoundingBoxEffect(
            Color baseColor,
            long duration,
            double width,
            double height,
            double depth,
            double spacing,
            @Nullable Entity attach
    ) {
        super(baseColor, duration);

        this.width = width;
        this.height = height;
        this.depth = depth;
        this.spacing = Math.max(0.05, spacing);
        this.attach = attach;
    }

    @Override
    public void spawn(Location spawnLocation) {
        Particle.DustOptions dust = new Particle.DustOptions(this.getBaseColor(), 0.66f);

        if (attach != null && attach.isValid()) {
            drawBoundingBox(attach.getWorld(), attach.getBoundingBox(), dust);
            return;
        }

        World world = spawnLocation.getWorld();

        if (world == null) {
            return;
        }

        double halfWidth = width / 2.0;
        double halfDepth = depth / 2.0;

        BoundingBox box = new BoundingBox(
                spawnLocation.getX() - halfWidth,
                spawnLocation.getY(),
                spawnLocation.getZ() - halfDepth,

                spawnLocation.getX() + halfWidth,
                spawnLocation.getY() + height,
                spawnLocation.getZ() + halfDepth
        );

        drawBoundingBox(world, box, dust);
    }

    private void drawBoundingBox(World world, BoundingBox box, Particle.DustOptions dust) {
        double minX = box.getMinX();
        double minY = box.getMinY();
        double minZ = box.getMinZ();

        double maxX = box.getMaxX();
        double maxY = box.getMaxY();
        double maxZ = box.getMaxZ();

        // Bottom rectangle
        drawLine(world, minX, minY, minZ, maxX, minY, minZ, dust);
        drawLine(world, maxX, minY, minZ, maxX, minY, maxZ, dust);
        drawLine(world, maxX, minY, maxZ, minX, minY, maxZ, dust);
        drawLine(world, minX, minY, maxZ, minX, minY, minZ, dust);

        // Top rectangle
        drawLine(world, minX, maxY, minZ, maxX, maxY, minZ, dust);
        drawLine(world, maxX, maxY, minZ, maxX, maxY, maxZ, dust);
        drawLine(world, maxX, maxY, maxZ, minX, maxY, maxZ, dust);
        drawLine(world, minX, maxY, maxZ, minX, maxY, minZ, dust);

        // Vertical edges
        drawLine(world, minX, minY, minZ, minX, maxY, minZ, dust);
        drawLine(world, maxX, minY, minZ, maxX, maxY, minZ, dust);
        drawLine(world, maxX, minY, maxZ, maxX, maxY, maxZ, dust);
        drawLine(world, minX, minY, maxZ, minX, maxY, maxZ, dust);
    }

    private void drawLine(
            World world,
            double x1,
            double y1,
            double z1,
            double x2,
            double y2,
            double z2,
            Particle.DustOptions dust
    ) {
        double dx = x2 - x1;
        double dy = y2 - y1;
        double dz = z2 - z1;

        double distance = Math.sqrt(dx * dx + dy * dy + dz * dz);
        int points = Math.max(1, (int) Math.ceil(distance / spacing));

        for (int i = 0; i <= points; i++) {
            double progress = i / (double) points;

            double x = x1 + dx * progress;
            double y = y1 + dy * progress;
            double z = z1 + dz * progress;

            world.spawnParticle(
                    Particle.REDSTONE,
                    x,
                    y,
                    z,
                    1,
                    0,
                    0,
                    0,
                    0,
                    dust
            );
        }
    }

}